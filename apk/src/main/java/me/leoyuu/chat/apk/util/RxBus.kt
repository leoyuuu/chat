package me.leoyuu.chat.apk.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * date 2018/6/6
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object RxBus {
    private val registers = mutableListOf<RxRegister<*>>()
    private val handler = Handler(Looper.getMainLooper())
    private val e = Executor { handler.post(it) }

    fun post(o: Any) {
        registers.forEach {
            it.consume(o, e)
        }
    }

    fun <T> register(clazz: Class<T>, observer: RxObserver<T>) {
        registers.add(RxRegister(clazz, observer))
    }

    fun <T> unregister(observer: RxObserver<T>) {
        val itr = registers.iterator()
        while (itr.hasNext()) {
            val r = itr.next()
            if (r.observer == observer) {
                itr.remove()
            }
        }
    }
}
