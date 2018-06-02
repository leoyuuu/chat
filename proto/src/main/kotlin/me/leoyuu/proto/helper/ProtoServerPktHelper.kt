package me.leoyuu.proto.helper

import me.leoyuu.proto.BasePackets
import me.leoyuu.proto.SystemPackets
import me.leoyuu.proto.BasePackets.PacketType.*

object ProtoServerPktHelper {

    fun bindOkRspPkt(seq: Int, heartBeatTime:Int) : BasePackets.Packet{
        val bindRsp = SystemPackets.SysBindRsp.newBuilder().setHeartBeatInterval(heartBeatTime).build()
        val content = sysContentBuilder.setBindRsp(bindRsp).build()
        val sysMsg = SystemPackets.SysMsg.newBuilder().setContent(content).setType(SystemPackets.SysMsgType.SysBindRspMsg)
        return basePkt(AppSystemMsg, pktContentBuilder.setSysMsg(sysMsg).build(), seq, CODE_OK)
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
