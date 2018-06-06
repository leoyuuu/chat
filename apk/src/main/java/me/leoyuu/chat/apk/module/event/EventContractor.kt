package me.leoyuu.chat.apk.module.event

import java.util.concurrent.Executor

/**
 * date 2018/6/6
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
internal class EventContractor<T>(private val clazz: Class<T>, val observer: EventObserver<T>) {
    fun consume(e: Any, executor: Executor) {
        if (e.javaClass == clazz) {
            executor.execute { observer.onEvent(clazz.cast(e)) }
        }
    }
}
