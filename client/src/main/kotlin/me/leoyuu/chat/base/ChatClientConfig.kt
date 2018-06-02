package me.leoyuu.chat.base

import me.leoyuu.chat.base.net.PacketReceiver

data class ChatClientConfig(
        val ip:String,
        val port:Int,
        val packetReceiver: PacketReceiver
)