package me.leoyuu.chat.base

import me.leoyuu.chat.base.callback.Callback
import me.leoyuu.chat.base.callback.SendCallback
import me.leoyuu.chat.base.net.ChatConnector
import me.leoyuu.chat.base.net.PacketSender
import me.leoyuu.proto.BasePackets

class ChatClient(private val config: ChatClientConfig) {
    private var connector = ChatConnector()
    private var packetSender:PacketSender? = null

    fun connect(callback: Callback?) {
        connector.connect(config, object : ChatConnector.ConnectCallback {
            override fun onFailed(msg: String) {
                callback?.onFailed(msg)
            }

            override fun onSuccess(sender: PacketSender) {
                packetSender = sender
                callback?.onSuccess()
            }
        })
    }

    fun sendPacket(packet: BasePackets.Packet, sendCallback: SendCallback) {
        packetSender?.sendPacket(packet, sendCallback)
    }
}
