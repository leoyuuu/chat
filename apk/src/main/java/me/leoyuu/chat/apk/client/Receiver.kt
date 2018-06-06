package me.leoyuu.chat.apk.client

import android.util.Log

import me.leoyuu.chat.base.net.PacketReceiver
import me.leoyuu.proto.BasePackets

/**
 * date 2018/6/2
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class Receiver : PacketReceiver {

    override fun onReceive(packet: BasePackets.Packet) {
        Log.i(TAG, packet.toString())
        when (packet.type) {
            BasePackets.PacketType.AppChatMsg -> {
                MsgManager.msgReceived(Message.fromChatMsg(packet.content.chatMsg))
            }
            BasePackets.PacketType.AppSystemMsg -> {

            }
            BasePackets.PacketType.AppUserMsg -> {

            }
            else -> {
            }
        }
    }

    companion object {
        private val TAG = Receiver::class.java.simpleName
    }
}
