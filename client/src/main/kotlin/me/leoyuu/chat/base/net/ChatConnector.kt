package me.leoyuu.chat.base.net

import me.leoyuu.chat.base.ChatClientConfig
import me.leoyuu.util.Logger
import me.leoyuu.util.ThreadUtil
import java.io.IOException
import java.net.Socket

class ChatConnector {
    var connected = false
    private set

    fun connect(config: ChatClientConfig, callback: ConnectCallback) {
        if (connected) {
            callback.onFailed("already connected")
            return
        }

        ThreadUtil.postCacheTask {
            try {
                val handler = PacketHandler(Socket(config.ip, config.port), config.packetReceiver)
                connected = true
                callback.onSuccess(handler)
                handler.listen()
            } catch (e: IOException) {
                connected = false
                Logger.e(e = e)
                callback.onFailed(e.localizedMessage)
            }
        }
    }

    interface ConnectCallback {
        fun onSuccess(sender: PacketSender)
        fun onFailed(msg:String)
    }
}