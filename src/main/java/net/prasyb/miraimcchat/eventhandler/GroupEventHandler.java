package net.prasyb.miraimcchat.eventhandler;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.prasyb.miraimcchat.JavaPluginMain;
import net.prasyb.miraimcchat.service.MessageService;
import org.jetbrains.annotations.NotNull;

public class GroupEventHandler extends SimpleListenerHost {
    private long groupID;
    private boolean isEnabled;

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public GroupEventHandler(long groupID, boolean isEnabled) {
        this.groupID = groupID;
        this.isEnabled = isEnabled;
        if (isEnabled) {
            JavaPluginMain.INSTANCE.getLogger().info("MiraiMcChat 群监听器已启用:" + groupID);
        } else {
            JavaPluginMain.INSTANCE.getLogger().info("MiraiMcChat 群监听器未启用:" + groupID);
        }
    }
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception){
        super.handleException(context, exception);
        JavaPluginMain.INSTANCE.getLogger().error(exception);
    }

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) {
        if (event.getSubject().getId() == groupID && isEnabled) {
            MessageService.broadcastMessage(event);
        }
    }
}
