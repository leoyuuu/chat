package me.leoyuu.util

import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*

object Logger {
    private val executor = ThreadUtil.getSingleExecutor()

    var printLevel = Level.Debug
    var showTime = true
    var showThread = true
    var showMethod = false

    var err:PrintStream = System.err
    var warn:PrintStream = System.out
    var info:PrintStream = System.out
    var debug:PrintStream = System.out

    fun d(msg: String, e: Throwable? = null) {
        printMsg(Level.Debug, debug, msg, e)
    }

    fun i(msg:String, e: Throwable? = null) {
        printMsg(Level.Info, info, msg, e)
    }

    fun w(msg:String, e: Throwable? = null) {
        printMsg(Level.Warn, warn, msg, e)
    }

    fun e(msg:String = "", e: Throwable? = null) {
        printMsg(Level.Err,err, msg, e)
    }

    private fun printMsg(level: Level, printStream: PrintStream, msg:String, e:Throwable?) {
        if (level.value >= printLevel.value) {
            val info =  genLogMsgByConfig(level, msg, e)
            executor.execute {
                printStream.println(info)
            }
        }
    }

    private fun genLogMsgByConfig(level: Level, msg: String, e: Throwable?) : String{
        return "Log($level): ${getTimeStr()}${getThreadStr()} $msg ${getErrStr(e)} ${getMethodString()}"
    }

    private fun getTimeStr() = if (showTime) timeFormatter.format(System.currentTimeMillis()) else ""

    private fun getThreadStr() = if (showThread) " ${Thread.currentThread().id}" else ""

    private fun getErrStr(e:Throwable?) = if (e == null) "" else e.localizedMessage

    private fun getMethodString():String {
        return if (showMethod) {
            val ele = Thread.currentThread().stackTrace[5]
            "${ele.className}#${ele.methodName}"
        } else {
            ""
        }
    }

    enum class Level constructor(val value: Int) {
        Debug(0), Info(1), Warn(2), Err(3)
    }

    private val timeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA)
}
