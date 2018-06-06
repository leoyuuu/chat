package me.leoyuu.chat.apk.client

import me.leoyuu.chat.base.callback.SendCallback
import me.leoyuu.proto.BasePackets

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object UserManager {
    private val mUsers = mutableListOf<User>()
    private val userMap = HashMap<Int, User>()

    fun init(callback: Callback) {
        PacketSender.sendGetUsersPacket(object : SendCallback {
            override fun onFailed(msg: String, packet: BasePackets.Packet?) {
                callback.onFailed(msg)
            }

            override fun onSuccess(packet: BasePackets.Packet) {
                val users = packet.content.userMsg.rspContent.getUserRsp.usersList
                mUsers.addAll(users.map { User(it.uid, it.name) })
                userMap.clear()
                mUsers.forEach { userMap[it.id] = it }
                callback.onSuccess(mUsers)
            }
        })
    }

    fun update(callback: Callback) {
        PacketSender.sendGetUsersPacket(object : SendCallback {
            override fun onFailed(msg: String, packet: BasePackets.Packet?) {
                callback.onFailed(msg)
            }

            override fun onSuccess(packet: BasePackets.Packet) {
                val users = packet.content.userMsg.rspContent.getUserRsp.usersList
                mUsers.clear()
                mUsers.addAll(users.map { User(it.uid, it.name) })
                userMap.clear()
                mUsers.forEach { userMap[it.id] = it }
                callback.onSuccess(mUsers)
            }
        })
    }

    fun getUserById(uid: Int) = userMap[uid]

    interface Callback {
        fun onSuccess(users: List<User>)
        fun onFailed(msg: String)
    }
}
