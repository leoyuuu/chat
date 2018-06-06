package me.leoyuu.chat.apk.module.main.msg

import android.content.Context
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.conversation_item.view.*

import me.leoyuu.chat.apk.R
import me.leoyuu.chat.apk.module.chat.ChatActivity
import me.leoyuu.chat.apk.module.data.user.UserManager
import me.leoyuu.chat.apk.util.rv.BaseRvItemView

/**
 * date 2018/6/6
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class ConversationItemView(context: Context) : BaseRvItemView<Conversation>(context) {
    private lateinit var data: Conversation

    init {
        LayoutInflater.from(context).inflate(R.layout.conversation_item, this)
        setOnClickListener { ChatActivity.start(context, data.cid.id) }
    }

    override fun onBindData(data: Conversation) {
        this.data = data
        val user = UserManager.getUserById(data.cid.id)
        con_name_tv.text = user?.name ?: data.msg.fromId.toString()
        con_text_tv.text = data.msg.text
    }
}
