package net.prasyb.miraimcchat.service;

import com.google.common.collect.BiMap;
import net.mamoe.mirai.console.command.CommandSender;
import net.prasyb.miraimcchat.JavaPluginMain;

import java.util.Objects;
import java.util.UUID;

import static net.prasyb.miraimcchat.JavaPluginMain.getDataPath;
import static net.prasyb.miraimcchat.service.DataService.getGroupId;


public class CommandService {

    public static void registerClient(CommandSender commandSender, String name) {
        String key = UUID.randomUUID().toString().replace("-","");
        try {
            DataService.addClient(key, name);
            if (commandSender.getSubject() != null) {
                commandSender.getSubject().sendMessage(
                        "已注册客户端并生成key:\n" + key + "\n以上key将作为客户端连接服务端的令牌");
            } else {
                JavaPluginMain.INSTANCE.getLogger().info("" +
                        "已注册客户端并生成key:\n" + key + "\n以上key将作为客户端连接服务端的令牌");
            }
        } catch (IllegalArgumentException e) {
            if (commandSender.getSubject() != null) {
                commandSender.getSubject().sendMessage("错误: 已存在相同用户名的客户端");
            } else {
                JavaPluginMain.INSTANCE.getLogger().warning("错误: 已存在相同用户名的客户端");
            }
        }
    }

    public static void removeClient(CommandSender commandSender, String key) {
        DataService.removeClient(key);
        if (commandSender.getSubject() != null) {
            commandSender.getSubject().sendMessage("已移除");
        } else {
            JavaPluginMain.INSTANCE.getLogger().info("已移除");
        }
    }


    public static void listClient(CommandSender commandSender) {
        StringBuilder stringBuilder = new StringBuilder();
        BiMap<String, String> clientMap = JavaPluginMain.INSTANCE.getPluginData().getClientBiMap();
        for (String key : clientMap.keySet()) {
            stringBuilder
                    .append("---\n")
                    .append("key:")
                    .append(key)
                    .append("\n")
                    .append("name:")
                    .append(clientMap.get(key))
                    .append("\n");
        }
        stringBuilder.append("---end---");
        if (commandSender.getUser() != null) {
            commandSender.getUser().sendMessage(stringBuilder.toString());
        } else {
            JavaPluginMain.INSTANCE.getLogger().info(stringBuilder.toString());
        }
    }


    public static void listUser(CommandSender commandSender) {
        StringBuilder stringBuilder = new StringBuilder();
        BiMap<Long, String> userMap = JavaPluginMain.INSTANCE.getPluginData().getUserBiMap();
        for (Long uid : userMap.keySet()) {
            stringBuilder
                    .append("---\n")
                    .append("QQ:")
                    .append(uid)
                    .append("\n")
                    .append("name:")
                    .append(userMap.get(uid))
                    .append("\n");
        }
        stringBuilder.append("---end---");
        if (commandSender.getUser() != null) {
            commandSender.getUser().sendMessage(stringBuilder.toString());
        } else {
            JavaPluginMain.INSTANCE.getLogger().info(stringBuilder.toString());
        }
    }

    public static void searchClientKey(CommandSender commandSender, String key) {
        String name = DataService.searchClientKey(key);
        String message;
        if (name != null) {
            message = "搜索到name:" + name;
        } else {
            message = "未搜索到结果";
        }
        if (commandSender.getUser() != null) {
            commandSender.getUser().sendMessage(message);
        } else {
            JavaPluginMain.INSTANCE.getLogger().info(message);
        }
    }


    public static void searchClientName(CommandSender commandSender, String name) {
        String key = DataService.searchClientName(name);
        String message;
        if (key != null) {
            message = "搜索到key:" + key;
        } else {
            message = "未搜索到结果";
        }
        if (commandSender.getUser() != null) {
            commandSender.getUser().sendMessage(message);
        } else {
            JavaPluginMain.INSTANCE.getLogger().info(message);
        }
    }


    public static void searchUserid(CommandSender commandSender, long uid) {
        String name = DataService.searchUserid(uid);
        String message;
        if (name != null) {
            message = "搜索到mc用户名:" + name;
        } else {
            message = "未搜索到结果";
        }
        if (commandSender.getUser() != null) {
            commandSender.getUser().sendMessage(message);
        } else {
            JavaPluginMain.INSTANCE.getLogger().info(message);
        }
    }

    public static void searchUsername(CommandSender commandSender, String name) {
        long uid = DataService.searchUsername(name);
        String message;
        if (uid != 0) {
            message = "搜索到QQ号:" + uid;
        } else {
            message = "未搜索到结果";
        }
        if (commandSender.getUser() != null) {
            commandSender.getUser().sendMessage(message);
        } else {
            JavaPluginMain.INSTANCE.getLogger().info(message);
        }
    }

    public static void bindUser(CommandSender commandSender, String name) {
        if (commandSender.getSubject() != null && commandSender.getUser() != null) {
            try {
                DataService.addUser(commandSender.getUser().getId(), name);
                commandSender.getSubject().sendMessage("绑定成功");
            } catch (IllegalArgumentException e) {
                JavaPluginMain.INSTANCE.getLogger().warning("错误: 已存在相同用户名的用户");
            }
        } else {
            JavaPluginMain.INSTANCE.getLogger().warning("非法请求");
        }
    }

    public static void unbindUser(CommandSender commandSender) {
        if (commandSender.getSubject() != null && commandSender.getUser() != null) {
            DataService.removeUser(commandSender.getUser().getId());
            commandSender.getSubject().sendMessage("已解除绑定");
        } else {
            JavaPluginMain.INSTANCE.getLogger().warning("非法请求");
        }
    }

    public static void setPort(CommandSender commandSender, int port) {
        if (port >= 0 && port < 65536) {
            JavaPluginMain.INSTANCE.getPluginConfig().setPort(port);
            DataService.savePluginConfig(getDataPath() + "/config/");
            if (commandSender.getSubject() != null) {
                commandSender.getSubject().sendMessage("端口设置成功，请重启mirai-console以应用设置");
            } else {
                JavaPluginMain.INSTANCE.getLogger().info("端口设置成功，请重启mirai-console以应用设置");
            }
        }
    }
    public static void bindGroup(CommandSender commandSender, long groupID) {
        JavaPluginMain.INSTANCE.getPluginConfig().setGroupID(groupID);
        JavaPluginMain.INSTANCE.getGroupEventHandler().setGroupID(groupID);
        DataService.savePluginConfig(getDataPath() + "/config/");
        if (commandSender.getSubject() != null) {
            commandSender.getSubject().sendMessage("绑定QQ群成功");
        } else {
            JavaPluginMain.INSTANCE.getLogger().info("绑定QQ群成功");
        }
    }

    public static void enableListening(CommandSender commandSender) {
        JavaPluginMain.INSTANCE.getPluginConfig().setIsEnabled(true);
        JavaPluginMain.INSTANCE.getGroupEventHandler().setIsEnabled(true);
        DataService.savePluginConfig(getDataPath() + "/config/");
        if (commandSender.getSubject() != null) {
            commandSender.getSubject().sendMessage("已开启插件在群" + getGroupId() + "的消息服务");
        } else {
            JavaPluginMain.INSTANCE.getLogger().info("已开启插件在群" + getGroupId() + "的消息服务");
        }
    }

    public static void disableListening(CommandSender commandSender) {
        JavaPluginMain.INSTANCE.getPluginConfig().setIsEnabled(false);
        JavaPluginMain.INSTANCE.getGroupEventHandler().setIsEnabled(false);
        DataService.savePluginConfig(getDataPath() + "/config/");
        if (commandSender.getSubject() != null) {
            commandSender.getSubject().sendMessage("已关闭插件在群" + getGroupId() + "的消息服务");
        } else {
            JavaPluginMain.INSTANCE.getLogger().info("已关闭插件在群" + getGroupId() + "的消息服务");
        }
    }
}
