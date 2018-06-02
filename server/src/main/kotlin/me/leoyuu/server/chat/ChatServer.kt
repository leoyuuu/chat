package me.leoyuu.server.chat

class ChatServer(private val port:Int) {
    private var initialized = false
    private lateinit var listener: ClientListener

    fun start() {
        if (!initialized || listener.listening) {
            listener = ClientListener(port)
            listener.listen()
        }
    }
}