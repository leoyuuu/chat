package me.leoyuu.chat.base.net

import me.leoyuu.chat.base.callback.SendCallback
import me.leoyuu.proto.BasePackets

interface PacketSender {
    fun sendPacket(packet: BasePackets.Packet, callback: SendCallback)
}
