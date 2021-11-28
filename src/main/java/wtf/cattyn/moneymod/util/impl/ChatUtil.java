//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import wtf.cattyn.moneymod.client.module.client.Global;
import wtf.cattyn.moneymod.util.Globals;

public class ChatUtil implements Globals
{
    public static String rainbowName;
    public static String staticName;
    
    public static void sendMessage(final String text) {
        sendMsgEvent(((boolean)Global.getInstance().rainbowPrefix.getValue()) ? ChatUtil.rainbowName : ChatUtil.staticName, text, false, 1);
    }
    
    public static void sendMessage(final String text, final Boolean silent) {
        sendMsgEvent(((boolean)Global.getInstance().rainbowPrefix.getValue()) ? ChatUtil.rainbowName : ChatUtil.staticName, text, silent, 1);
    }
    
    public static void sendMessageId(final String text, final Boolean silent, final int id) {
        sendMsgEvent(((boolean)Global.getInstance().rainbowPrefix.getValue()) ? ChatUtil.rainbowName : ChatUtil.staticName, text, silent, id);
    }
    
    public static void sendMsgEvent(final String prefix, final String text, final boolean silent, final int id) {
        if (ChatUtil.mc.player == null) {
            return;
        }
        if (!silent) {
            ChatUtil.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(prefix + TextFormatting.GRAY + " " + text));
        }
        else {
            ChatUtil.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(prefix + TextFormatting.GRAY + " " + text), id);
        }
    }
    
    static {
        ChatUtil.rainbowName = "§$[moneymod+3]";
        ChatUtil.staticName = ChatFormatting.GREEN + "[" + "moneymod+3" + "]";
    }
}
