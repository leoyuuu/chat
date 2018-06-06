package me.leoyuu.chat.apk.module.data.net

import android.os.Handler
import android.os.Looper
import me.leoyuu.chat.base.ChatClient
import me.leoyuu.chat.base.ChatClientConfig
import me.leoyuu.chat.base.callback.Callback
import me.leoyuu.chat.base.callback.SendCallback
import me.leoyuu.proto.BasePackets
import me.leoyuu.proto.helper.ProtoClientPktHelper

/**
 * date 2018/6/2
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object ClientManager {
    private val handler = Handler(Looper.getMainLooper())
    private val client = ChatClient()
    private var uid = 0
    private var name = ""

    fun initConfig(host: String, port: Int, uid: Int, name: String) {
        client.config = ChatClientConfig(host, port, Receiver())
        ClientManager.uid = uid
        ClientManager.name = name
    }

    fun connect(callback: Callback) {
        connectInternal(callback)
    }

    fun bind(callback: Callback) {
        sendPacket(ProtoClientPktHelper.buildBindPacket(uid, "123456", name), object : SendCallback {
            override fun onFailed(msg: String, packet: BasePackets.Packet?) {
                callback.onFailed(msg)
            }

            override fun onSuccess(packet: BasePackets.Packet) {
                callback.onSuccess()
            }
        })
    }

    private fun connectInternal(callback: Callback) {
        client.connect(object : Callback {
            override fun onFailed(msg: String) {
                handler.post { callback.onFailed(msg) }
            }

            override fun onSuccess() {
                bind(callback)
            }
        })
    }

    internal fun sendPacket(packet: BasePackets.Packet, callback: SendCallback) {
        client.sendPacket(packet, object : SendCallback {
            override fun onFailed(msg: String, packet: BasePackets.Packet?) {
                handler.post { callback.onFailed(msg, packet) }
            }

            override fun onSuccess(packet: BasePackets.Packet) {
                handler.post { callback.onSuccess(packet) }
            }
        })
    }
}
