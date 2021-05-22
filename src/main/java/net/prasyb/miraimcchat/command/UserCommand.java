package net.prasyb.miraimcchat.command;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import net.prasyb.miraimcchat.JavaPluginMain;
import net.prasyb.miraimcchat.service.CommandService;

public class UserCommand extends JCompositeCommand {
    public static final UserCommand INSTANCE = new UserCommand();
    private UserCommand() {
        super(JavaPluginMain.INSTANCE, "mcchat");
        setPrefixOptional(false);
    }

    @SubCommand(value = "bind")
    public void bindUser(CommandSender commandSender, String name) {
        CommandService.bindUser(commandSender, name);
    }
    @SubCommand(value = "unbind")
    public void unbindUser(CommandSender commandSender) {
        CommandService.unbindUser(commandSender);
    }

    @SubCommand(value = "searchuserid")
    public void searchUserid(CommandSender commandSender, long uid) {
        CommandService.searchUserid(commandSender, uid);
    }
    @SubCommand(value = "searchusername")
    public void searchUsername(CommandSender commandSender, String name) {
        CommandService.searchUsername(commandSender, name);
    }
}
