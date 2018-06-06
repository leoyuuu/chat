package me.leoyuu.chat.apk.util.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import java.util.ArrayList


/**
 * date 2018/5/9
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class BaseRvAdapter<T, V : BaseRvItemView<T>>(
        private val c: Class<V>) : RecyclerView.Adapter<BaseRvHolder<T>>() {

    private val dataList = ArrayList<T>()

    fun refresh(list: List<T>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun addFirst(item: T) {
        dataList.add(0, item)
        notifyItemInserted(0)
    }

    fun addTail(list: List<T>) {
        val size = dataList.size
        dataList.addAll(list)
        notifyItemRangeInserted(size, list.size)
    }

    fun addTail(item: T) {
        dataList.add(item)
        notifyItemInserted(dataList.size - 1)
    }

    fun setFirst(item: T) {
        val a = dataList.remove(item)
        dataList.add(0, item)
        if (a) {
            notifyDataSetChanged()
        } else {
            notifyItemChanged(0)
        }
    }

    fun remove(item: T): Int {
        val itr = dataList.iterator()
        var index = 0
        while (itr.hasNext()) {
            val e = itr.next()
            if (item!! == e) {
                itr.remove()
                notifyItemRemoved(index)
                return index
            }
            index++
        }
        return -1
    }

    override fun onBindViewHolder(holder: BaseRvHolder<T>, position: Int) {
        holder.view.onBindData(dataList[position])
    }

    override fun getItemViewType(position: Int) = c.hashCode()

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvHolder<T> {
        val context = parent.context
        val v = c.getConstructor(Context::class.java).newInstance(context)
        return BaseRvHolder(v)
    }
}
