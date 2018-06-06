package me.leoyuu.chat.apk.module.event

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object EventDispatcher {
    fun publishEvent(event: Any) {
        EventHub.post(event)
    }
}
