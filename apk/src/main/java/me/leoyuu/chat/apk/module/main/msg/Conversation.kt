package me.leoyuu.chat.apk.module.main.msg

import me.leoyuu.chat.apk.module.data.msg.Message
import me.leoyuu.chat.apk.module.data.msg.MsgManager

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
