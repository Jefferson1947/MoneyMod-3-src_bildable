// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.command.impl;

import java.util.Arrays;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.command.Command;

public class ChatCommand extends Command
{
    public ChatCommand() {
        super("chat <message|online>", new String[] { "chat", "irc", "c" });
    }
    
    @Override
    public void exec(final String[] args) {
        if (args.length < 2) {
            this.printUsage();
        }
        else if (args[1].equalsIgnoreCase("online")) {
            Main.getCapeThread().sendOnlineRequest();
            ChatUtil.sendMessage(String.format("Online players (%d): %s", Main.getCapeThread().getUserCount(), Main.getCapeThread().getOnline()));
        }
        else {
            final String msg = String.join(" ", (CharSequence[])Arrays.copyOfRange(args, 1, args.length));
            Main.getCapeThread().sendChatMessage(msg);
        }
    }
}
