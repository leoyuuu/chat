package me.leoyuu.chat.apk.module.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import me.leoyuu.chat.apk.module.main.mine.MineFragment
import me.leoyuu.chat.apk.module.main.msg.MsgFragment
import me.leoyuu.chat.apk.module.main.users.UserFragment

/**
 * date 2018/6/5
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UserFragment()
            1 -> MsgFragment()
            2 -> MineFragment()
            else -> Fragment()
        }
    }
}
