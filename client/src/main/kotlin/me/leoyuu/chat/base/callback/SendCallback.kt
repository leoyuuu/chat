package me.leoyuu.chat.base.callback

import me.leoyuu.proto.BasePackets

interface SendCallback {
    fun onSuccess(packet: BasePackets.Packet)
    fun onFailed(msg:String, packet: BasePackets.Packet? = null)
}