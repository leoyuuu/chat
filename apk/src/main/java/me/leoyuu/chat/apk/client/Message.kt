package me.leoyuu.chat.apk.client

import me.leoyuu.proto.ChatPackets

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
data class Message(val mid: Int,
                   var fromId: Int,
                   var toId: Int,
                   var text: String,
                   var gid: Int = 0,
                   var toMid: Int = 0,
                   var json: String = "",
                   var type: Int = 0,
                   var extra: Any = Object()) {
    companion object {
        fun fromChatMsg(msg: ChatPackets.ChatMsg): Message {
            return Message(msg.mid, msg.fromUid, msg.toUid, msg.text, msg.toMid, msg.gid, msg.json, msg.typeValue, msg.extra)
        }
    }
}
