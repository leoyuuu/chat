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

val config = ChatClientConfig("118.190.98.99", 9909, receiver)
val client = ChatClient()
var uid:Int = 0
var sid = "123456"
var toUid:Int = 0

fun bindSelf() {
    client.sendPacket(ProtoClientPktHelper.buildBindPacket(uid, sid, "$uid"), sendCallback)
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
    showMsg("I am a console chat client")
    showMsg("this is just a simple implication")
    client.config = config
    if (args.isEmpty()) {
        uid = System.currentTimeMillis().toInt()
    } else {
        uid = args[0].toInt()
    }
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
