package me.leoyuu.chat.apk.util

import android.content.Context
import android.support.annotation.StringRes
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import me.leoyuu.chat.apk.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */

private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
private const val TIME_DAY = 1000L * 60 * 60 * 24
private lateinit var appContext: Context

private var screenWidth = 0
private var screenHeight = 0
private var statusBarHeight = 0

fun utilInit(context: Context) {
    appContext = context
}

fun dp2px(dp: Int): Int = (appContext.resources.displayMetrics.density * dp).toInt()

fun loge(msg: String, e: Throwable? = null) {
    if (!BuildConfig.DEBUG) {
        return
    }

    val ele = Thread.currentThread().stackTrace[4]
    Log.e("Logger", String.format(Locale.CHINA, "msg: $msg \t ${ele.className}#${ele.methodName}"), e)
}

fun toast(msg: String?) {
    Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show()
}

fun toast(@StringRes msgId: Int) {
    Toast.makeText(appContext, msgId, Toast.LENGTH_SHORT).show()
}

fun getFriendlyTimeString(time: Long): String = dateFormatter.format(time)


/**
 * 判断前天，昨天，今天，明天，后天
 * @param time 输入的时间
 * @param now 现在
 * @return -2: 前天 -1：昨天： 0 ： 今天， 1： 明天， 2：后天，
 */
fun getDayTime(time: Long, now: Long): Int {
    val offSet = Calendar.getInstance().timeZone.rawOffset
    val today = (now + offSet) / TIME_DAY
    val start = (time + offSet) / TIME_DAY
    return (start - today).toInt()
}

fun getScreenWidth(): Int {
    if (screenWidth == 0) {
        initScreenDisplay()
    }
    return screenWidth
}

fun getScreenHeight(): Int {
    if (screenHeight == 0) {
        initScreenDisplay()
    }
    return screenHeight
}

private fun initScreenDisplay() {
    val dm = DisplayMetrics()
    (appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)
    screenWidth = dm.widthPixels
    screenHeight = dm.heightPixels
}

fun getStatusBarHeight(): Int {
    if (statusBarHeight > 0) else {
        val resourceId = appContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = if (resourceId > 0) appContext.resources.getDimensionPixelSize(resourceId) else 0
    }
    return statusBarHeight
}

fun getDist(x0: Float, x1: Float, y0: Float, y1: Float): Float {
    val dx = x1 - x0
    val dy = y1 - y0
    return Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
}