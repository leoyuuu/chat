package me.leoyuu.server.chat

import me.leoyuu.proto.BasePackets
import me.leoyuu.proto.ChatPackets
import me.leoyuu.proto.helper.ProtoServerPktHelper
import me.leoyuu.proto.helper.ProtoServerPktHelper.CODE_ERR
import me.leoyuu.server.Printer
import me.leoyuu.server.chat.clients.ClientsManager

object ChatMsgHelper {

    fun handleChatMsg(uid: Int, packet: BasePackets.Packet): BasePackets.Packet {
        val p = packet.content.chatMsg
        if (p.fromUid != uid) {
            return ProtoServerPktHelper.baseErrRspPkt(CODE_ERR, "error uid")
        }
        val res = if (p.gid > 0) {
            sendToGroup(p, packet)
        } else {
            sendToPerson(p, packet)
        }
        return if (res.isEmpty()) {
            ProtoServerPktHelper.baseOkRspPkt(packet.seq)
        } else {
            ProtoServerPktHelper.baseErrRspPkt(packet.seq, res)
        }
    }


    private fun sendToGroup(p: ChatPackets.ChatMsg, packet:BasePackets.Packet):String {
        val res = checkSendGroup(p.gid, p.fromUid)
        if (res.isEmpty()) {
            sendToGroup(packet, p.gid)
        }
        return res
    }

    private fun sendToGroup(packet: BasePackets.Packet, gid: Int) {
        Printer.println("send msg to group, gid:$gid, msg:$packet")
    }

    @SuppressWarnings("unused")
    private fun checkSendGroup(gid:Int, fromId:Int):String {
        // todo add group
        return ""
    }


    private fun sendToPerson(p: ChatPackets.ChatMsg, packet:BasePackets.Packet):String {
        val res = checkSendPerson(p.gid, p.fromUid)
        if (res.isEmpty()) {
            sendToPerson(packet, p.toUid)
        }
        return res
    }

    private fun sendToPerson(packet: BasePackets.Packet, uid: Int) {
        Printer.println("send msg to user, to uid:$uid, msg:$packet")
        ClientsManager.get(uid)?.sendPacket(packet)
    }

    @SuppressWarnings("unused")
    private fun checkSendPerson(toUid:Int, fromUid:Int):String {
        // todo check person relation to set if do send
        return ""
    }

}
