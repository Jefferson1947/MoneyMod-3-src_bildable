//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import wtf.cattyn.moneymod.Main;
import net.minecraft.network.play.server.SPacketChat;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "RecorderMode", cat = Module.Category.MISC)
public class RecorderMode extends Module
{
    @SubscribeEvent
    public void messageReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            boolean cancel = false;
            final SPacketChat packetChat = event.getPacket();
            String message = packetChat.getChatComponent().getUnformattedText();
            if (message.contains(RecorderMode.mc.player.getName())) {
                message = message.replaceAll(RecorderMode.mc.player.getName(), "me");
                cancel = true;
            }
            for (final String friend : Main.getFriendManager().get(0)) {
                if (message.contains(friend)) {
                    message = message.replaceAll(friend, "moneymod_user");
                    cancel = true;
                }
            }
            if (cancel) {
                RecorderMode.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(message));
                event.setCanceled(true);
            }
        }
    }
}
