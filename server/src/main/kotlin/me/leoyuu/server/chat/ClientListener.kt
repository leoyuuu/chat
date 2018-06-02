package me.leoyuu.server.chat

import me.leoyuu.server.Printer
import me.leoyuu.util.Logger
import me.leoyuu.util.ThreadUtil

import java.io.IOException
import java.net.ServerSocket

class ClientListener(private val port: Int){
    var listening = false
    private set

    fun listen() {
        if (!listening) {
            try {
                val serverSocket = ServerSocket(port)
                Printer.println("listening on $port")
                listening = true
                while (true) {
                    ThreadUtil.postCacheTask(ClientHandler(serverSocket.accept()))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            listening = true
        }
    }
}
