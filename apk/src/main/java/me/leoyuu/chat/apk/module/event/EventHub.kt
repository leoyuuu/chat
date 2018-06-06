package me.leoyuu.chat.apk.module.event

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * date 2018/6/6
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object EventHub {
    private val registers = mutableListOf<EventContractor<*>>()
    private val handler = Handler(Looper.getMainLooper())
    private val e = Executor { handler.post(it) }

    fun post(o: Any) {
        registers.forEach {
            it.consume(o, e)
        }
    }

    fun <T> register(clazz: Class<T>, observer: EventObserver<T>) {
        registers.add(EventContractor(clazz, observer))
    }

    fun <T> unregister(observer: EventObserver<T>) {
        val itr = registers.iterator()
        while (itr.hasNext()) {
            val r = itr.next()
            if (r.observer == observer) {
                itr.remove()
            }
        }
    }
}
