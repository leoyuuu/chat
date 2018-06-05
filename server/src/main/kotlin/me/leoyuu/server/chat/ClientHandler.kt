package me.leoyuu.server.chat

import me.leoyuu.proto.BasePackets
import me.leoyuu.proto.SystemPackets
import me.leoyuu.proto.UserPackets
import me.leoyuu.proto.helper.ProtoServerPktHelper
import me.leoyuu.proto.helper.ProtoPktIoHelper
import me.leoyuu.server.Printer
import me.leoyuu.server.chat.clients.ClientsManager
import me.leoyuu.server.entity.User
import me.leoyuu.util.Logger
import me.leoyuu.util.ThreadUtil
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class ClientHandler(private val skt: Socket) : Runnable {
    private lateinit var inputStream:InputStream
    private lateinit var outputStream:OutputStream
    private var initSuccess = false
    private var running = false
    private var bound = false
    lateinit var user: User
    private set

    init {
        try {
            inputStream = skt.getInputStream()
            outputStream = skt.getOutputStream()
            initSuccess = true
            running = true
            Printer.println("a new client connect success")
        } catch (e:IOException) {
            initSuccess = false
            Logger.e(e = e)
        }
    }

    override fun run() {
        if (!initSuccess) {
            try {
                skt.close()
            } catch (e:IOException) {
                Logger.e(e = e)
            }
        } else {
            loopClientMsg()
        }
    }

    fun sendPacket(packet:BasePackets.Packet) {
        ThreadUtil.postNetIoTask { sendPacketInternal(packet) }
    }

    private fun loopClientMsg() {
        try {
            while (running) {
                handlePacket(ProtoPktIoHelper.read(inputStream))
            }
        } catch (e:IOException) {
            e.printStackTrace()
            removeSelf()
        }
    }

    private fun sendPacketInternal(packet: BasePackets.Packet) {
        var notSent = true
        var retryTimes = 0
        while (notSent) {
            retryTimes++
            notSent = try {
                ProtoPktIoHelper.write(packet, outputStream)
                false
            } catch (e:IOException) {
                e.printStackTrace()
                true
            }
            if (retryTimes > 2) {
                removeSelf()
            }
        }
    }

    private fun handlePacket(packet: BasePackets.Packet) {
        Printer.println(packet)
        val res = if (bound) {
            handleReq(packet)
        } else {
            checkBind(packet)
        }
        Printer.println(res)
        sendPacket(res)
    }

    private fun checkBind(packet: BasePackets.Packet) :BasePackets.Packet{
        return if (packet.type == BasePackets.PacketType.AppSystemMsg &&
                packet.content.sysMsg.type == SystemPackets.SysMsgType.SysBindMsg) {
            checkBindInfo(packet.content.sysMsg.content.bind, packet.seq)
        } else {
            ProtoServerPktHelper.baseErrRspPkt(packet.seq, "please bind first")
        }
    }

    private fun handleReq(packet: BasePackets.Packet) :BasePackets.Packet{
        return when (packet.type) {
            BasePackets.PacketType.AppChatMsg -> {
                ChatMsgHelper.handleChatMsg(packet)
            }
            else -> {
                ProtoServerPktHelper.baseErrRspPkt(packet.seq, "unsupported msg type" )
            }
        }
    }

    private fun checkBindInfo(bindMsg: SystemPackets.SysBind, seq:Int):BasePackets.Packet {
        return when {
            ClientsManager.contains(bindMsg.user.uid) -> {
                ProtoServerPktHelper.baseErrRspPkt(seq, "user online")
            }
            bindMsg.user.sid.isEmpty() -> {
                ProtoServerPktHelper.baseErrRspPkt(seq, "Authentication Failed")
            }
            else -> {
                user = transform(bindMsg.user)
                bound = true
                addSelf()
                ProtoServerPktHelper.bindOkRspPkt(seq, 3000)
            }
        }
    }


    private fun removeSelf() {
        ClientsManager.remove(user.uid)
    }

    private fun addSelf() {
        ClientsManager.put(this)
    }

    companion object {
        private fun transform(userInfo: UserPackets.UserInfo):User {
            return User(userInfo.uid, if (userInfo.sid == null) "" else userInfo.sid, if (userInfo.name == null) "无名大侠" else userInfo.name)
        }
    }
}
