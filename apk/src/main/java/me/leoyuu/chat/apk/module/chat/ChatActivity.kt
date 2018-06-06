package me.leoyuu.chat.apk.module.chat

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_chat.*
import me.leoyuu.chat.apk.module.data.msg.MsgUpdateEvent
import me.leoyuu.chat.apk.R
import me.leoyuu.chat.apk.module.data.msg.MsgManager
import me.leoyuu.chat.apk.module.data.net.PacketSender
import me.leoyuu.chat.apk.module.data.user.UserManager
import me.leoyuu.chat.apk.module.login.LoginHelper
import me.leoyuu.chat.apk.module.event.EventHub
import me.leoyuu.chat.apk.module.event.EventObserver
import me.leoyuu.chat.apk.util.rv.BaseRvAdapter
import me.leoyuu.chat.apk.util.toast
import me.leoyuu.chat.base.callback.SendCallback
import me.leoyuu.proto.BasePackets

class ChatActivity : AppCompatActivity() {

    private val adapter = BaseRvAdapter(MessageItemView::class.java)
    private var toUid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSend()
        msg_list.adapter = adapter
        msg_list.layoutManager = LinearLayoutManager(this)
        EventHub.register(MsgUpdateEvent::class.java, msgObserver)
        initData()
    }

    private fun initData() {
        toUid = intent.getIntExtra("UID", 0)
        val user = UserManager.getUserById(toUid)
        title = user?.name
        val list = MsgManager.getMsg(toUid)
        if (list != null) {
            adapter.addTail(list)
            msg_list.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun setSend() {
        chat_send_btn.setOnClickListener {
            PacketSender.sendChatPacket(LoginHelper.uid, toUid, chat_send_et.text.toString(), object : SendCallback {
                override fun onFailed(msg: String, packet: BasePackets.Packet?) {
                    toast(msg)
                }

                override fun onSuccess(packet: BasePackets.Packet) {
                    MsgManager.addSelfMsg(toUid, chat_send_et.text.toString())
                    chat_send_et.text.clear()
                }
            })
        }
    }

    override fun onDestroy() {
        EventHub.unregister(msgObserver)
        super.onDestroy()
    }

    private val msgObserver = object : EventObserver<MsgUpdateEvent> {
        override fun onEvent(t: MsgUpdateEvent) {
            if (toUid == t.cid.id && t.cid.type == MsgManager.MsgType.Person) {
                adapter.addTail(t.msg)
                msg_list.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    companion object {
        fun start(context: Context, toUid: Int) {
            context.startActivity(Intent(context, ChatActivity::class.java).putExtra("UID", toUid))
        }
    }
}
