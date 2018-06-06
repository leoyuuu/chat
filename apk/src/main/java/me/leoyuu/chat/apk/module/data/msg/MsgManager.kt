package me.leoyuu.chat.apk.module.data.msg

import me.leoyuu.chat.apk.module.event.EventDispatcher
import me.leoyuu.chat.apk.module.login.LoginHelper
import me.leoyuu.chat.apk.util.loge

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object MsgManager {
    private val personMsg = HashMap<Int, MutableList<Message>>()
    private val groupMsg = HashMap<Int, MutableList<Message>>()
    private val msgList = mutableListOf<Cid>()

    fun msgReceived(msg: Message) {
        if (msg.gid == 0) {
            val id = if (msg.fromId == LoginHelper.uid) msg.toId else msg.fromId
            addMsgToPerson(id, msg)
        } else {
            addMsgToGroup(msg)
        }
    }

    fun addSelfMsg(toId: Int, text: String): Message {
        val msg = Message(1, LoginHelper.uid, toId, text)
        addMsgToPerson(toId, msg)
        return msg
    }

    private fun addMsgToPerson(id: Int, msg: Message) {
        val list = personMsg[id]
        if (list == null) {
            personMsg[id] = mutableListOf(msg)
        } else {
            list.add(msg)
        }
        changeMsgOrder(id, msg, MsgType.Person)
    }

    private fun addMsgToGroup(msg: Message) {
        val list = groupMsg[msg.gid]
        if (list == null) {
            groupMsg[msg.gid] = mutableListOf(msg)
        } else {
            list.add(msg)
        }
        changeMsgOrder(msg.gid, msg, MsgType.Group)
    }


    fun getMsg(fromId: Int): MutableList<Message>? {
        return personMsg[fromId]
    }

    private fun changeMsgOrder(id: Int, msg: Message, type: MsgType) {
        val cid = Cid(id, type)
        msgList.remove(cid)
        msgList.add(0, cid)
        notifyMsgReceived(cid, msg)
        loge("cid:$id, msg:$msg")
    }

    private fun notifyMsgReceived(cid: Cid, msg: Message) {
        EventDispatcher.publishEvent(MsgUpdateEvent(cid, msg))
    }

    data class Cid(var id: Int, var type: MsgType) {
        override fun hashCode(): Int {
            return id + 31 * type.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            return other != null && other.javaClass == javaClass && other is Cid && other.type == type
        }
    }

    enum class MsgType {
        Person, Group
    }
}
