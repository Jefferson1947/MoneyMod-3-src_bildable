// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.Main;
import java.util.Arrays;
import wtf.cattyn.moneymod.client.command.Command;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend <add|del|get> <name>", new String[] { "f" });
    }
    
    @Override
    public void exec(String[] str) {
        str = Arrays.copyOfRange(str, 1, str.length);
        if (str.length < 2) {
            if (str[0].equalsIgnoreCase("get")) {
                Main.getFriendManager().get(0);
            }
            else {
                this.printUsage();
            }
        }
        else {
            final String lowerCase = str[0].toLowerCase();
            switch (lowerCase) {
                case "add": {
                    Main.getFriendManager().add(str[1]);
                    this.say(ChatFormatting.GRAY + "friend " + ChatFormatting.WHITE + "> " + str[1].toLowerCase() + " " + ChatFormatting.GREEN + "added");
                    break;
                }
                case "del": {
                    Main.getFriendManager().del(str[1]);
                    this.say(ChatFormatting.GRAY + "friend " + ChatFormatting.WHITE + "> " + str[1].toLowerCase() + " " + ChatFormatting.RED + "deleted");
                    break;
                }
            }
        }
    }
}
