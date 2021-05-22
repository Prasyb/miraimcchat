package net.prasyb.miraimcchat.util;


import net.mamoe.mirai.console.command.CommandManager;
import net.prasyb.miraimcchat.command.OperatorCommand;
import net.prasyb.miraimcchat.command.UserCommand;

public class CommandRegistry {
    public static void registerCommands() {
        CommandManager.INSTANCE.registerCommand(OperatorCommand.INSTANCE, true);
        CommandManager.INSTANCE.registerCommand(UserCommand.INSTANCE, true);
    }
}
