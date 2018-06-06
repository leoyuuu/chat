package me.leoyuu.chat.apk.util.rv

import android.support.v7.widget.RecyclerView

/**
 * date 2018/5/13
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class BaseRvHolder<in T> internal constructor(val view: BaseRvItemView<T>) : RecyclerView.ViewHolder(view)