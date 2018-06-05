package me.leoyuu.chat.base.net

import me.leoyuu.chat.base.callback.SendCallback

object CallbackManager {
    private val lock = Object()
    private val map = HashMap<Int, SendCallback>()

    fun addCallback(seq: Int, callback: SendCallback) {
        synchronized(lock) {
            map[seq] = callback
        }
    }

    fun getCallback(seq: Int) : SendCallback? {
        synchronized(lock) {
            return map.remove(seq)
        }
    }
}