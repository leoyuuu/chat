package me.leoyuu.chat.apk.module.chat

import android.content.Context
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.msg_item.view.*
import me.leoyuu.chat.apk.R

import me.leoyuu.chat.apk.module.data.msg.Message
import me.leoyuu.chat.apk.module.data.user.UserManager
import me.leoyuu.chat.apk.util.rv.BaseRvItemView

/**
 * date 2018/6/6
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class MessageItemView(context: Context) : BaseRvItemView<Message>(context) {
    private lateinit var data: Message

    init {
        LayoutInflater.from(context).inflate(R.layout.msg_item, this)
    }

    override fun onBindData(data: Message) {
        this.data = data
        val user = UserManager.getUserById(data.fromId)
        msg_name_tv.text = user?.name ?: data.fromId.toString()
        msg_text_tv.text = data.text
    }
}
