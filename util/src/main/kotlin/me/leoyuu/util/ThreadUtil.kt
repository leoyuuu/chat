package me.leoyuu.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

object ThreadUtil {
    private val netIoExecutor = Executors.newFixedThreadPool(4)
    private val cacheExecutor = Executors.newCachedThreadPool()

    fun getSingleExecutor() : Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun postNetIoTask(runnable: () -> Unit) {
        netIoExecutor.execute(runnable)
    }

    fun postNetIoTask(runnable: Runnable) {
        netIoExecutor.execute(runnable)
    }

    fun postCacheTask(runnable: () -> Unit) {
        cacheExecutor.execute(runnable)
    }

    fun postCacheTask(runnable: Runnable) {
        cacheExecutor.execute(runnable)
    }
}
