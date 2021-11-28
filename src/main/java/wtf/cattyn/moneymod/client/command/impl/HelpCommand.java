// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.command.impl;

import wtf.cattyn.moneymod.util.impl.ChatUtil;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.command.Command;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("help", new String[] { "help" });
    }
    
    @Override
    public void exec(final String[] args) {
    }
}
