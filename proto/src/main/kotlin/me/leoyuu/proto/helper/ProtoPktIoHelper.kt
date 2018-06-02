package me.leoyuu.proto.helper

import me.leoyuu.proto.BasePackets
import java.io.*
import java.nio.ByteBuffer
import java.nio.ByteOrder

object ProtoPktIoHelper {

    private const val MAX_BUFFER_SIZE = 1024 * 1024 * 16

    fun write(packet: BasePackets.Packet, outputStream: OutputStream) {
        val bytes = packet.toByteArray()
        val len = bytes.size
        val stream = DataOutputStream(outputStream)
        val headBuf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(len)
        stream.write(headBuf.array())
        stream.write(bytes)
    }

    fun read(inputStream: InputStream): BasePackets.Packet {
        val stream = DataInputStream(inputStream)
        val lenBytes = ByteArray(4)
        stream.readFully(lenBytes)
        val len = ByteBuffer.wrap(lenBytes).order(ByteOrder.LITTLE_ENDIAN).int
        if (len in (0 .. MAX_BUFFER_SIZE)) {
            val bytes = ByteArray(len)
            stream.readFully(bytes)
            return BasePackets.Packet.parseFrom(bytes)
        }
        throw IOException("server packet error")
    }
}
