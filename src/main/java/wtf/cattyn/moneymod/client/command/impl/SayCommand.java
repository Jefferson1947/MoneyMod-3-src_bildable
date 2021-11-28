//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.command.impl;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import wtf.cattyn.moneymod.client.command.Command;

public class SayCommand extends Command
{
    public SayCommand() {
        super("say <message>", new String[] { "say" });
    }
    
    @Override
    public void exec(final String[] args) {
        if (args.length < 2) {
            this.printUsage();
            return;
        }
        SayCommand.mc.player.sendChatMessage((String)Arrays.stream(args).skip(1L).collect(Collectors.joining(" ")));
    }
}
