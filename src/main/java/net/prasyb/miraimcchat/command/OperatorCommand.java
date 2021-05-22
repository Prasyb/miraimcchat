package net.prasyb.miraimcchat.command;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import net.prasyb.miraimcchat.JavaPluginMain;
import net.prasyb.miraimcchat.service.CommandService;

public class OperatorCommand extends JCompositeCommand {
    public static final OperatorCommand INSTANCE = new OperatorCommand();
    private OperatorCommand() {
        super(JavaPluginMain.INSTANCE, "mcchatop");
        setPrefixOptional(false);
    }

    @SubCommand(value = "register")
    public void registerClient(CommandSender commandSender, String name) {
        CommandService.registerClient(commandSender, name);
    }
    @SubCommand(value = "remove")
    public void removeClient(CommandSender commandSender, String key) {
        CommandService.removeClient(commandSender, key);
    }

    @SubCommand(value = "clientlist")
    public void listClient(CommandSender commandSender) {
        CommandService.listClient(commandSender);
    }

    @SubCommand(value = "userlist")
    public void listUser(CommandSender commandSender) {
        CommandService.listUser(commandSender);
    }

    @SubCommand(value = "searchclientkey")
    public void searchClientKey(CommandSender commandSender, String name) {
        CommandService.searchClientKey(commandSender, name);
    }

    @SubCommand(value = "searchclientname")
    public void searchClientName(CommandSender commandSender, String name) {
        CommandService.searchClientName(commandSender, name);
    }
    @SubCommand(value = "setport")
    public void setPort(CommandSender commandSender, int port) {
        CommandService.setPort(commandSender, port);
    }
    @SubCommand(value = "bindgroup")
    public void bindGroup(CommandSender commandSender, long groupID) {
        CommandService.bindGroup(commandSender, groupID);
    }
    @SubCommand(value = "enable")
    public void enableListening(CommandSender commandSender) {
        CommandService.enableListening(commandSender);
    }
    @SubCommand(value = "disable")
    public void disableListening(CommandSender commandSender) {
        CommandService.disableListening(commandSender);
    }
}
