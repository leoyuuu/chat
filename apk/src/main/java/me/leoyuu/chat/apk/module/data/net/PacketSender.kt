package me.leoyuu.chat.apk.module.data.net

import me.leoyuu.chat.base.callback.SendCallback
import me.leoyuu.proto.helper.ProtoClientPktHelper

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object PacketSender {
    fun sendGetUsersPacket(callback: SendCallback) {
        ClientManager.sendPacket(ProtoClientPktHelper.buildReqUsersPkt(), callback)
    }

    fun sendChatPacket(from: Int, to: Int, text: String, callback: SendCallback) {
        ClientManager.sendPacket(ProtoClientPktHelper.buildChatPkt(to, from, text), callback)
    }

}
