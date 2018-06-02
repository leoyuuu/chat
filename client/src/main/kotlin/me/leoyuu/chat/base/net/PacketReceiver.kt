package me.leoyuu.chat.base.net

import me.leoyuu.proto.BasePackets

interface PacketReceiver{
    fun onReceive(packet: BasePackets.Packet)
}