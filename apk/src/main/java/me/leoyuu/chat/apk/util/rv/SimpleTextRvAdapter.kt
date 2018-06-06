package me.leoyuu.chat.apk.util.rv


import android.widget.TextView
import me.leoyuu.chat.apk.R


/**
 * date 2018/5/9
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class SimpleTextRvAdapter : SimpleRvAdapter<String>(R.layout.base_text_view) {

    fun init(num: Int) {
        refresh((0..Math.abs(num)).map { it.toString() })
    }

    override fun convert(holder: Holder, data: String) {
        (holder.itemView as TextView).text = data
    }
}
