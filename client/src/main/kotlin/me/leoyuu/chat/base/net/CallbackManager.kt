package me.leoyuu.chat.base.net

import me.leoyuu.chat.base.callback.SendCallback

object CallbackManager {
    private val map = HashMap<Int, SendCallback>()

    fun getCallback(seq: Int) = map.remove(seq)
}