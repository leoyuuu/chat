package me.leoyuu.proto.helper

import me.leoyuu.proto.BasePackets.*
import me.leoyuu.proto.BasePackets.PacketType.*
import me.leoyuu.proto.ChatPackets.*
import me.leoyuu.proto.ChatPackets.ChatMsgType.*
import me.leoyuu.proto.SystemPackets.*
import me.leoyuu.proto.SystemPackets.SysMsgType.SysBindMsg
import me.leoyuu.proto.UserPackets
import me.leoyuu.proto.UserPackets.UserReqContent
import me.leoyuu.proto.UserPackets.UserReqType.getUsersReq
import me.leoyuu.proto.helper.ProtoBasePktHelper.user

object ProtoClientPktHelper {
    private var seq = 0

    fun buildReqUsersPkt(): Packet {
        val getUserReq = UserPackets.UserGetReq.newBuilder().build()
        return basePkt(AppUserMsg, baseUserPktContent(getUsersReq,
                UserReqContent.newBuilder()
                        .setGetUserReq(getUserReq).build()))
    }

    fun buildChatPkt(toUid: Int, fromUid: Int, msg: String, gid: Int = 0, json: String = "") =
            buildChatPkt(chatMsgBuilder(toUid, fromUid, gid, json, ChatNormalMsg).setText(msg).build())

    fun buildChatVideoPkt(toUid: Int, fromUid: Int, videoUrl: String, gid: Int = 0, json: String = "") =
            buildChatPkt(chatMsgBuilder(toUid, fromUid, gid, json, ChatVideoMsg).setExtra(chatVideoExtra(videoUrl)).build())

    fun buildChatImgPkt(toUid: Int, fromUid: Int, videoUrl: String, gid: Int = 0, json: String = "") =
            buildChatPkt(chatMsgBuilder(toUid, fromUid, gid, json, ChatImgMsg).setExtra(chatImgExtra(videoUrl)).build())

    fun buildChatSharePkt(toUid: Int, fromUid: Int, json: String = "", title: String, iconUrl: String, desc: String, url: String, gid: Int = 0) =
            buildChatPkt(chatMsgBuilder(toUid, fromUid, gid, json, ChatShareMsg).setExtra(chatShareExtra(title, iconUrl, desc, url)).build())

    fun buildBindPacket(uid: Int, sid: String, name: String): Packet {
        val bind = SysBind.newBuilder().setUser(user(uid, name, sid)).build()
        return basePkt(AppSystemMsg, baseSysPktContent(SysBindMsg, SysMsgContent.newBuilder().setBind(bind).build()))
    }

    private fun buildChatPkt(chatMsg: ChatMsg): Packet {
        return basePkt(AppChatMsg, PacketContent.newBuilder().setChatMsg(chatMsg).build())
    }

    private fun chatVideoExtra(url: String): ChatMsgExtra =
            ChatMsgExtra.newBuilder().setVideo(ChatMsgExtraVideo.newBuilder().setPath(url).build()).build()

    private fun chatImgExtra(url: String): ChatMsgExtra =
            ChatMsgExtra.newBuilder().setImg(ChatMsgExtraImg.newBuilder().setPath(url).build()).build()

    private fun chatShareExtra(title: String, iconUrl: String, desc: String, url: String): ChatMsgExtra =
            ChatMsgExtra.newBuilder().setShare(getShareExtra(title, iconUrl, desc, url)).build()

    private fun getShareExtra(title: String, iconUrl: String, desc: String, url: String) =
            ChatMsgExtraShare.newBuilder().setTitle(title).setIconUrl(iconUrl).setDesc(desc).setUrl(url).build()

    private fun chatMsgBuilder(toUid: Int, fromUid: Int, git: Int, json: String = "", type: ChatMsgType): ChatMsg.Builder = ChatMsg.newBuilder()
            .setType(type)
            .setToUid(toUid)
            .setFromUid(fromUid)
            .setGid(git)
            .setJson(json)

    private fun baseUserPktContent(type: UserPackets.UserReqType, content: UserPackets.UserReqContent) =
            PacketContent.newBuilder().setUserMsg(UserPackets.UserMsg.newBuilder().setReqType(type).setReqContent(content).build()).build()

    private fun baseSysPktContent(type: SysMsgType, content: SysMsgContent): PacketContent =
            PacketContent.newBuilder().setSysMsg(SysMsg.newBuilder().setType(type).setContent(content).build()).build()

    private fun basePkt(type: PacketType, content: PacketContent): Packet =
            Packet.newBuilder().setCode(200).setSeq(seq++).setType(type).setContent(content).build()

    const val CODE_OK = ProtoServerPktHelper.CODE_OK
    const val CODE_ERR = ProtoServerPktHelper.CODE_ERR
}
