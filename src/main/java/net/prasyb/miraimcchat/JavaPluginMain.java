package net.prasyb.miraimcchat;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.prasyb.miraimcchat.data.PluginConfig;
import net.prasyb.miraimcchat.service.DataService;
import net.prasyb.miraimcchat.data.PluginData;
import net.prasyb.miraimcchat.eventhandler.GroupEventHandler;
import net.prasyb.miraimcchat.network.WebSocketServer;
import net.prasyb.miraimcchat.util.CommandRegistry;


public final class JavaPluginMain extends JavaPlugin {
    public static final JavaPluginMain INSTANCE = new JavaPluginMain();
    private PluginData pluginData = null;
    private PluginConfig pluginConfig = null;
    private Thread webSocketServerThread = null;
    private GroupEventHandler groupEventHandler = null;

    private JavaPluginMain() {
        super(new JvmPluginDescriptionBuilder("net.prasyb.miraimcchat", "0.0.1")
                .author("Prasyb")
                .info("a mirai-console plugin for QQ group & minecraft chat")
                .build());
    }

    @Override
    public void onEnable() {
        //加载数据
        pluginData = DataService.loadPluginData(getDataPath() + "/data/");
        if (pluginData == null) {
            pluginData = new PluginData();
        }
        pluginConfig = DataService.loadPluginConfig(getDataPath() + "/config/");
        if (pluginConfig == null) {
            pluginConfig = new PluginConfig();
        }

        //注册指令
        CommandRegistry.registerCommands();

        //启动 webSocket 服务器线程
        webSocketServerThread = new WebSocketServer(pluginConfig.getPort());
        webSocketServerThread.start();

        //开启全局监听器
        groupEventHandler = new GroupEventHandler(pluginConfig.getGroupID(), pluginConfig.getIsEnabled());
        JavaPluginMain.INSTANCE.getScheduler().delayed(3000, () ->
            Bot.getInstances().get(0).getEventChannel().registerListenerHost(groupEventHandler));

        //每 5 分钟自动保存数据
        JavaPluginMain.INSTANCE.getScheduler().repeating(1000 * 60 * 5,
                () -> DataService.savePluginData(getDataPath() + "/data/"));
    }

    public void onDisable() {
        //保存数据
        DataService.savePluginData( getDataPath() + "/data/");
        DataService.savePluginConfig(getDataPath() + "/config/");
        //结束 webSocket 服务器线程
        webSocketServerThread.interrupt();
    }

    public static String getDataPath() {
        return JavaPluginMain.INSTANCE.getDataFolderPath().toString();
    }

    public PluginData getPluginData() {
        return pluginData;
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    public GroupEventHandler getGroupEventHandler() { return groupEventHandler; }
}