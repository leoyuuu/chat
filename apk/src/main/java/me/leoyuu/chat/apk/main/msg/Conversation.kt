package me.leoyuu.chat.apk.main.msg

import me.leoyuu.chat.apk.client.Message
import me.leoyuu.chat.apk.client.MsgManager

/**
 * date 2018/6/6
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class Conversation(val cid: MsgManager.Cid, var msg: Message) {
    override fun equals(other: Any?): Boolean {
        return other != null && other.javaClass == javaClass && other is Conversation && other.cid.id == cid.id && other.cid.type == cid.type
    }

    override fun hashCode(): Int {
        return cid.hashCode()
    }
}
