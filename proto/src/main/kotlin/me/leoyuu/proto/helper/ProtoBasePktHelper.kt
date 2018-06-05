package me.leoyuu.proto.helper

import me.leoyuu.proto.UserPackets

internal object ProtoBasePktHelper {
    fun user(uid:Int, name:String, sid:String):UserPackets.UserInfo =
            UserPackets.UserInfo.newBuilder().setName(name).setUid(uid).setSid(sid).build()
}
