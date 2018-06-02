package me.leoyuu.chat.base.callback

interface Callback {
    fun onSuccess()
    fun onFailed(msg:String)
}