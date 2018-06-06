package me.leoyuu.chat.apk.module.event

/**
 * date 2018/6/6
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
interface EventObserver<T> {
    fun onEvent(t: T)
}
