// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.command;

import wtf.cattyn.moneymod.api.managment.CommandManager;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.util.Globals;

public abstract class Command implements Globals
{
    private final String name;
    private final String syntax;
    private final String[] alias;
    
    public Command(final String syntax, final String... alias) {
        this.alias = alias;
        this.syntax = syntax;
        this.name = alias[0];
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getAlias() {
        return this.alias;
    }
    
    public String getSyntax() {
        return this.syntax;
    }
    
    public abstract void exec(final String[] p0);
    
    protected void say(final String text) {
        ChatUtil.sendMessage(ChatFormatting.YELLOW + text);
    }
    
    protected void printUsage() {
        ChatUtil.sendMessage(String.format("Usage: %s%s", CommandManager.prefix, this.syntax));
    }
}
