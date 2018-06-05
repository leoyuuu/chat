package me.leoyuu.server.chat.clients

import me.leoyuu.server.chat.ClientHandler
import me.leoyuu.server.entity.User

object ClientsManager {
    private val lock = Object()
    private val clients = mutableMapOf<Int, ClientHandler>()
    private val users = mutableListOf<User>()

    fun contains(uid: Int) = clients.containsKey(uid)

    fun put(handler: ClientHandler) {
        synchronized(lock) {
            clients[handler.user.uid] = handler
            users.add(handler.user)
        }
    }

    fun remove(handler: ClientHandler) {
        synchronized(lock) {
            clients.remove(handler.user.uid)
            users.remove(handler.user)
        }
    }

    fun get(uid: Int) = clients[uid]

    fun getUsers(): List<User> {
        synchronized(lock) {
            return users
        }
    }
}