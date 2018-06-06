package me.leoyuu.chat.apk.util.rv

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * date 2018/5/16
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class SimpleTextList @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : RecyclerView(context, attrs) {
    init {
        adapter = SimpleTextRvAdapter().apply { init(100) }
        layoutManager = LinearLayoutManager(context)
    }
}
