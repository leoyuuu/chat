package me.leoyuu.chat.apk.module.login

import me.leoyuu.chat.apk.module.data.user.User

/**
 * date 2018/6/6
 * email july.oloy@qq.com
 *
 * @author leoyuu
 */
object LoginHelper {
    private lateinit var user: User
    fun init(uid: Int, name: String) {
        user = User(uid, name)
    }

    val name: String get() = user.name
    val uid: Int get() = user.id
}
