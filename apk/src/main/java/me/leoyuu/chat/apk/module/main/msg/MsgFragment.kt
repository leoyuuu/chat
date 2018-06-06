package me.leoyuu.chat.apk.module.main.msg

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_msgs.*
import me.leoyuu.chat.apk.module.data.msg.MsgUpdateEvent

import me.leoyuu.chat.apk.R
import me.leoyuu.chat.apk.module.event.EventHub
import me.leoyuu.chat.apk.module.event.EventObserver
import me.leoyuu.chat.apk.util.rv.BaseRvAdapter

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class MsgFragment : Fragment() {

    private val adapter = BaseRvAdapter(ConversationItemView::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_msgs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conversation_list.adapter = adapter
        conversation_list.layoutManager = LinearLayoutManager(context)
        EventHub.register(MsgUpdateEvent::class.java, msgObserver)
    }


    override fun onDestroyView() {
        EventHub.unregister(msgObserver)
        super.onDestroyView()
    }


    private val msgObserver = object : EventObserver<MsgUpdateEvent> {
        override fun onEvent(t: MsgUpdateEvent) {
            adapter.setFirst(Conversation(t.cid, t.msg))
        }
    }
}
