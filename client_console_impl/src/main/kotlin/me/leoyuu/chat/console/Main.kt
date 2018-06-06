package me.leoyuu.chat.console

import me.leoyuu.chat.base.ChatClient
import me.leoyuu.chat.base.ChatClientConfig
import me.leoyuu.chat.base.callback.Callback
import me.leoyuu.chat.base.callback.SendCallback
import me.leoyuu.chat.base.net.PacketReceiver
import me.leoyuu.proto.BasePackets
import me.leoyuu.proto.helper.ProtoClientPktHelper
import java.util.*

fun showMsg(msg:Any) {
    println(msg)
}

val receiver = object : PacketReceiver{
    override fun onReceive(packet: BasePackets.Packet) {
        showMsg(packet)
    }
}

val sendCallback = object :SendCallback {
    override fun onFailed(msg: String, packet: BasePackets.Packet?) {
        showMsg(msg)
    }

    override fun onSuccess(packet: BasePackets.Packet) {
        showMsg("write success: $packet" )
    }
}

lateinit var config: ChatClientConfig
val client = ChatClient()
var uid:Int = 0
var sid = "123456"
var name = ""
var toUid:Int = 0

fun reqUsers() {
    client.sendPacket(ProtoClientPktHelper.buildReqUsersPkt(), object : SendCallback {
        override fun onFailed(msg: String, packet: BasePackets.Packet?) {
            showMsg(msg)
        }

        override fun onSuccess(packet: BasePackets.Packet) {
            showMsg(packet)
        }
    })
}

fun bindSelf() {
    client.sendPacket(ProtoClientPktHelper.buildBindPacket(uid, sid, name), object : SendCallback {
        override fun onFailed(msg: String, packet: BasePackets.Packet?) {
            showMsg("bind failed $msg")
        }

        override fun onSuccess(packet: BasePackets.Packet) {
            showMsg("bind success")
            reqUsers()
        }
    })
}

fun startChat() {
    val scanner = Scanner(System.`in`)
    while (true) {
        val line = scanner.nextLine()
        if (line != null) {
            if (toUid < 1) {
                try {
                    toUid = line.toInt()
                } catch (e:Exception){
                    println("please input uid first")
                }
            } else {
                send(line)
            }
        } else {
            break
        }
    }
}

fun send(msg:String) {
    client.sendPacket(ProtoClientPktHelper.buildChatPkt(toUid, uid, msg), sendCallback)
}

fun main(args:Array<String>) {
    if (args.size != 4) {
        showMsg("use ip port id name as the arguments")
        return
    }
    config = ChatClientConfig(args[0], args[1].toInt(), receiver)
    uid = args[2].toInt()
    name = args[3]

    client.config = config
    client.connect(object : Callback {
        override fun onFailed(msg: String) {
            showMsg(msg)
        }

        override fun onSuccess() {
            showMsg("connect success")
            bindSelf()
        }
    })
    startChat()
}
