package me.leoyuu.chat.apk


import me.leoyuu.chat.apk.util.RxBus

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object EventDispatcher {
    fun publishEvent(event: Any) {
        RxBus.post(event)
    }
}
