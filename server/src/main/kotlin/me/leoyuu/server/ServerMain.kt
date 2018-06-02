package me.leoyuu.server

import me.leoyuu.server.chat.ChatServer

const val PORT = 9909

fun main(args: Array<String>) {
    var port = PORT
    if (args.isNotEmpty()) {
        try {
            port = args[0].toInt()
        } catch (e:Exception) {
             println("error param, init default port $port")
        }
    }
    val server = ChatServer(port)
    server.start()
}