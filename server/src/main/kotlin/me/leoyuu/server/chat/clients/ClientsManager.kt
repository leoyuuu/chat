package me.leoyuu.server.chat.clients

import me.leoyuu.server.chat.ClientHandler

object ClientsManager {
    private val clients = mutableMapOf<Int, ClientHandler>()

    fun contains(uid: Int) = clients.containsKey(uid)

    fun put(handler: ClientHandler) {
        clients[handler.user.uid] = handler
    }

    fun remove(uid:Int) {
        clients.remove(uid)
    }

    fun get(uid: Int) = clients[uid]

}