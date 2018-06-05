package me.leoyuu.proto.helper

import me.leoyuu.proto.BasePackets
import me.leoyuu.proto.BasePackets.PacketType.AppSystemMsg
import me.leoyuu.proto.BasePackets.PacketType.AppUserMsg
import me.leoyuu.proto.SystemPackets
import me.leoyuu.proto.UserPackets
import me.leoyuu.proto.helper.ProtoBasePktHelper.user

object ProtoServerPktHelper {

    fun bindOkRspPkt(seq: Int, heartBeatTime:Int) : BasePackets.Packet{
        val bindRsp = SystemPackets.SysBindRsp.newBuilder().setHeartBeatInterval(heartBeatTime).build()
        val content = sysContentBuilder.setBindRsp(bindRsp).build()
        val sysMsg = SystemPackets.SysMsg.newBuilder().setContent(content).setType(SystemPackets.SysMsgType.SysBindRspMsg)
        return basePkt(AppSystemMsg, pktContentBuilder.setSysMsg(sysMsg).build(), seq, CODE_OK)
    }

    fun getUsersReqPkt(uidList: List<Int>, nameList: List<String>, seq: Int): BasePackets.Packet {
        val userRspBuilder = UserPackets.UserGetRsp.newBuilder()
        (0 until uidList.size).forEach {
            userRspBuilder.addUsers(user(uidList[it], nameList[it]))
        }
        val userRsp = userRspBuilder.build()
        val rspContent = UserPackets.UserRspContent.newBuilder().setGetUserRsp(userRsp).build()
        return basePkt(AppUserMsg, pktContentBuilder.setUserMsg(UserPackets.UserMsg.newBuilder()
                .setReqType(UserPackets.UserReqType.getUsersReq)
                .setRspContent(rspContent).build()).build(), seq, CODE_OK)
    }

    fun baseOkRspPkt(seq:Int) :BasePackets.Packet =
            BasePackets.Packet.newBuilder().setCode(CODE_OK).setMsg("").setSeq(seq).build()

    fun baseErrRspPkt(seq: Int, msg:String):BasePackets.Packet =
            BasePackets.Packet.newBuilder().setCode(CODE_ERR).setMsg(msg).setSeq(seq).build()

    private fun basePkt(type:BasePackets.PacketType,
                        content:BasePackets.PacketContent,
                        seq: Int,
                        code:Int) : BasePackets.Packet =
            BasePackets.Packet.newBuilder()
                    .setCode(code)
                    .setSeq(seq)
                    .setType(type)
                    .setContent(content)
                    .build()

    private val sysContentBuilder = SystemPackets.SysMsgContent.newBuilder()
    private val pktContentBuilder = BasePackets.PacketContent.newBuilder()

    const val CODE_OK = 200
    const val CODE_ERR = 500
}
