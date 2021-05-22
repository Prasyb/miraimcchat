package net.prasyb.miraimcchat.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.prasyb.miraimcchat.JavaPluginMain;
import net.prasyb.miraimcchat.data.PluginConfig;
import net.prasyb.miraimcchat.data.PluginData;

import static net.prasyb.miraimcchat.util.FileUtils.createJsonFile;
import static net.prasyb.miraimcchat.util.FileUtils.readJsonFile;

public class DataService {
    public static void savePluginData(String filePath) {
        PluginData pluginData = JavaPluginMain.INSTANCE.getPluginData();
        createJsonFile(JSON.toJSONString(pluginData,
                SerializerFeature.PrettyFormat),
                filePath,
                "plugin_data",
                true);
    }

    public static PluginData loadPluginData(String filePath) {
        String jsonStr = readJsonFile(filePath + "plugin_data.json");
        return JSON.parseObject(jsonStr, PluginData.class);
    }
    public static void savePluginConfig(String filePath) {
        PluginConfig pluginConfig = JavaPluginMain.INSTANCE.getPluginConfig();
        createJsonFile(JSON.toJSONString(pluginConfig,
                SerializerFeature.PrettyFormat),
                filePath,
                "plugin_config",
                true);
    }

    public static PluginConfig loadPluginConfig(String filePath) {
        String jsonStr = readJsonFile(filePath + "plugin_config.json");
        return JSON.parseObject(jsonStr, PluginConfig.class);
    }
    public static String searchClientKey(String key) {
        return JavaPluginMain.INSTANCE.getPluginData().getClientMap().get(key);
    }
    public static String searchClientName(String name) {
        return JavaPluginMain.INSTANCE.getPluginData().getClientBiMap().inverse().get(name);
    }
    public static String searchUserid(long uid) {
        return JavaPluginMain.INSTANCE.getPluginData().getUserMap().get(uid);
    }
    public static long searchUsername(String name) {
        if (JavaPluginMain.INSTANCE.getPluginData().getUserBiMap().inverse().get(name) == null) {
            return -1;
        }
        return JavaPluginMain.INSTANCE.getPluginData().getUserBiMap().inverse().get(name);
    }
    public static void addClient(String key, String name) throws IllegalArgumentException {
        JavaPluginMain.INSTANCE.getPluginData().getClientMap().put(key, name);
    }
    public static void addUser(long uid, String name) throws IllegalArgumentException {
        JavaPluginMain.INSTANCE.getPluginData().getUserMap().put(uid, name);
    }
    public static void removeClient(String key) {
        JavaPluginMain.INSTANCE.getPluginData().getClientMap().remove(key);
    }
    public static void removeUser(long uid) {
        JavaPluginMain.INSTANCE.getPluginData().getUserMap().remove(uid);
    }
    public static long getGroupId() {
        return JavaPluginMain.INSTANCE.getPluginConfig().getGroupID();
    }
    public static int getPort() {
        return JavaPluginMain.INSTANCE.getPluginConfig().getPort();
    }
    public static boolean containsClientKey(String key) {
        return JavaPluginMain.INSTANCE.getPluginData().getClientMap().containsKey(key);
    }
}
