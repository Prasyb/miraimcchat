package net.prasyb.miraimcchat.service;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.events.MessageEvent;
import net.prasyb.miraimcchat.JavaPluginMain;
import net.prasyb.miraimcchat.network.*;

import java.util.Objects;

import static net.prasyb.miraimcchat.network.WebSocketChannelSupervise.sendToAll;
import static net.prasyb.miraimcchat.network.WebSocketChannelSupervise.sendToAllExcept;
import static net.prasyb.miraimcchat.service.DataService.*;

public class MessageService {
    public static void broadcastMessage(MessageEvent event) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setText(event.getMessage().contentToString());
        serverMessage.setSenderQQName(getMemberNameCardOrNick(event.getSender().getId(), getGroupId()));
        serverMessage.setSource("群");
        String mcName = DataService.searchUserid(event.getSender().getId());
        if (mcName != null) {
            serverMessage.setSenderMcName(mcName);
        } else {
            serverMessage.setSenderMcName("");
        }
        ServerPacket serverPacket = new ServerPacket();
        serverPacket.setMessage(serverMessage);
        sendToAll(new TextWebSocketFrame(JSON.toJSONString(serverPacket)));
    }

    public static void forwardMessage(ClientPacket clientPacket, String sourceId, String sourceName) {
        ServerMessage serverMessage = new ServerMessage();
        ServerPacket serverPacket = new ServerPacket();
        String QQName;
        String source = searchClientKey(clientPacket.getKey());
        String text;
        String mcName;
        Group group = Bot.getInstances().get(0).getGroup(getGroupId());
        try {
            QQName = getMemberNameCardOrNick(searchUsername(
                    Objects.requireNonNull(clientPacket.getMessage().getSender())), getGroupId());
            text = Objects.requireNonNull(clientPacket.getMessage().getText());
            mcName = Objects.requireNonNull(clientPacket.getMessage().getSender());
        } catch (Exception e) {
            JavaPluginMain.INSTANCE.getLogger().error("接收到非法包", e);
            return;
        }
        serverMessage.setSenderQQName(QQName);
        serverMessage.setSource(source);
        serverMessage.setText(text);
        serverMessage.setSenderMcName(mcName);
        serverPacket.setMessage(serverMessage);
        sendToAllExcept(new TextWebSocketFrame(JSON.toJSONString(serverPacket)), sourceId);
        if (group == null) {
            JavaPluginMain.INSTANCE.getLogger().warning("群组不存在，请检查群号");
        } else {
            String toSend = String.format("[%s]<%s/%s>:%s",sourceName,QQName,mcName,text);
            group.sendMessage(toSend);
        }
    }

    public static String getMemberNameCardOrNick(long uid, long gid) {
        String QQname;
        Group group = Bot.getInstances().get(0).getGroup(gid);
        if (group != null) {
            NormalMember member = group.get(uid);
            if (member != null) {
                String nameCard = member.getNameCard();
                if (!nameCard.equals("")) {
                    QQname = nameCard;
                } else {
                    QQname = member.getNick();
                }
            } else {
                QQname = "";
            }
        } else {
            QQname = "";
        }
        return QQname;
    }
}
