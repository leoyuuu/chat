package me.leoyuu.chat.apk.module.main.users

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_users.*

import me.leoyuu.chat.apk.R
import me.leoyuu.chat.apk.module.chat.ChatActivity
import me.leoyuu.chat.apk.module.data.user.User
import me.leoyuu.chat.apk.module.data.user.UserManager
import me.leoyuu.chat.apk.module.login.LoginHelper
import me.leoyuu.chat.apk.util.rv.SimpleRvAdapter
import me.leoyuu.chat.apk.util.toast

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class UserFragment : Fragment() {

    private val adapter = Adapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_refresher.setOnRefreshListener { update() }
        user_list.adapter = adapter
        user_list.layoutManager = LinearLayoutManager(context)
        update()
    }

    private fun update() {
        UserManager.update(object : UserManager.Callback {
            override fun onFailed(msg: String) {
                user_refresher.isRefreshing = false
                toast(msg)
            }

            override fun onSuccess(users: List<User>) {
                adapter.refresh(users.filter { it.id != LoginHelper.uid })
                user_refresher.isRefreshing = false
            }
        })
    }

    internal class Adapter : SimpleRvAdapter<User>(R.layout.base_text_view) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val holder = super.onCreateViewHolder(parent, viewType)
            holder.itemView.setPadding(0, 24, 0, 24)
            return holder
        }

        override fun convert(holder: Holder, data: User) {
            (holder.itemView as TextView).text = data.name
            holder.itemView.setOnClickListener { ChatActivity.start(holder.itemView.context, data.id) }
        }
    }
}
