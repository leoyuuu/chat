package me.leoyuu.chat.base.net

import me.leoyuu.chat.base.callback.SendCallback
import me.leoyuu.proto.BasePackets
import me.leoyuu.proto.helper.ProtoPktIoHelper
import me.leoyuu.util.Logger
import me.leoyuu.util.ThreadUtil
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class PacketHandler(private val skt:Socket, private val packetReceiver: PacketReceiver) :PacketSender{
    private lateinit var inputStream: InputStream
    private lateinit var outputStream: OutputStream
    private var running = true

    private var initSuccess = false

    init {
        try {
            inputStream = skt.getInputStream()
            outputStream = skt.getOutputStream()
            initSuccess = true
        } catch (e: IOException) {
            initSuccess = false
            Logger.e(e = e)
        }
    }

    fun listen() {
        if (!initSuccess) {
            try {
                skt.close()
            } catch (e:IOException) {
                me.leoyuu.util.Logger
                Logger.e(e = e)
                e.printStackTrace()
            }
        } else {
            listenInternal()
        }
    }

    override fun sendPacket(packet: BasePackets.Packet, callback: SendCallback){
        ThreadUtil.postNetIoTask() { sendPacketInternal(packet, callback) }
    }

    private fun listenInternal() {
        try {
            while (running) {
                onReceivePacket(ProtoPktIoHelper.read(inputStream))
            }
        } catch (e:IOException) {
            Logger.e(e = e)
        }
    }

    private fun onReceivePacket(packet: BasePackets.Packet) {
        val callback = CallbackManager.getCallback(packet.seq)
        if (callback != null) {
            if (packet.code == 200) {
                callback.onSuccess(packet)
            } else {
                callback.onFailed(packet.msg, packet)
            }
        } else {
            packetReceiver.onReceive(packet)
        }
    }

    private fun sendPacketInternal(packet: BasePackets.Packet, callback: SendCallback) {
        try {
            CallbackManager.addCallback(packet.seq, callback)
            ProtoPktIoHelper.write(packet, outputStream)
        } catch (e:Exception) {
            callback.onFailed(e.localizedMessage)
        }
    }
}