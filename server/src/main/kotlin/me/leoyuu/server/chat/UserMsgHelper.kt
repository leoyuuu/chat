package me.leoyuu.server.chat

import me.leoyuu.proto.BasePackets
import me.leoyuu.proto.UserPackets
import me.leoyuu.proto.helper.ProtoServerPktHelper
import me.leoyuu.proto.helper.ProtoServerPktHelper.CODE_ERR
import me.leoyuu.proto.helper.ProtoServerPktHelper.CODE_OK
import me.leoyuu.server.chat.clients.ClientsManager

object UserMsgHelper {

    fun handleUserMsg(uid: Int, packet: BasePackets.Packet): BasePackets.Packet {
        val p = packet.content.userMsg

        return when (p.reqType) {
            UserPackets.UserReqType.changeInfo -> {
                changeUserInfo(uid, p.reqContent.changeReq, packet)
            }
            UserPackets.UserReqType.getUsersReq -> {
                getUsersInfo(packet.seq)
            }
            else -> ProtoServerPktHelper.baseErrRspPkt(CODE_ERR, "not supported");
        }
    }

    private fun changeUserInfo(uid: Int, p: UserPackets.UserInfoChangeReq, packet: BasePackets.Packet)
            : BasePackets.Packet {
        if (p.info.uid != uid) {
            return ProtoServerPktHelper.baseErrRspPkt(CODE_ERR, "req uid not match")
        }
        val handler = ClientsManager.get(p.info.uid)
        return if (handler != null && uid == p.info.uid) {
            val user = handler.user
            user.name = p.info.name
            ProtoServerPktHelper.baseOkRspPkt(CODE_OK)
        } else {
            ProtoServerPktHelper.baseErrRspPkt(CODE_ERR, "no user in")
        }
    }

    private fun getUsersInfo(seq: Int): BasePackets.Packet {
        val users = ClientsManager.getUsers()
        val uidList = users.map { it.uid }
        val nameList = users.map { it.name }
        return ProtoServerPktHelper.getUsersReqPkt(uidList, nameList, seq)
    }
}
