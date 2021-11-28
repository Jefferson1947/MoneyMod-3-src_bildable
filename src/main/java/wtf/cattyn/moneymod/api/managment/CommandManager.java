// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.managment;

import java.util.Iterator;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import java.util.Arrays;
import wtf.cattyn.moneymod.client.command.impl.ChatCommand;
import wtf.cattyn.moneymod.client.command.impl.FriendCommand;
import wtf.cattyn.moneymod.client.command.impl.KitCommand;
import wtf.cattyn.moneymod.client.command.impl.HelpCommand;
import wtf.cattyn.moneymod.client.command.impl.SayCommand;
import wtf.cattyn.moneymod.client.command.Command;
import java.util.List;

public class CommandManager
{
    public static String prefix;
    private List<Command> commands;
    
    public CommandManager() {
        this.commands = Arrays.asList(new SayCommand(), new HelpCommand(), new KitCommand(), new FriendCommand(), new ChatCommand());
    }
    
    public List<Command> getCommands() {
        return this.commands;
    }
    
    public void parseCommand(final String string) {
        for (final Command command : this.getCommands()) {
            for (final String name : command.getAlias()) {
                if (string.startsWith(name)) {
                    try {
                        command.exec(string.trim().split(" "));
                    }
                    catch (Exception e) {
                        ChatUtil.sendMessage(String.format("Usage: %s", command.getSyntax()));
                    }
                    return;
                }
            }
        }
        ChatUtil.sendMessage("Unknown command.", true);
    }
    
    public String getPrefix() {
        return CommandManager.prefix;
    }
    
    static {
        CommandManager.prefix = "$";
    }
}
