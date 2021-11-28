//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.core;

import wtf.cattyn.moneymod.api.managment.CommandManager;
import net.minecraft.network.play.client.CPacketChatMessage;
import wtf.cattyn.moneymod.api.event.UpdateWalkingPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import wtf.cattyn.moneymod.api.event.TotemPopEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import wtf.cattyn.moneymod.util.Globals;

public class Handler implements Globals
{
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Handler.mc.player != null && Handler.mc.world != null && event.phase == TickEvent.Phase.START) {
            Main.getModuleManager().get().stream().filter(Module::isToggled).forEach(Module::onTick);
        }
    }
    
    @SubscribeEvent
    public void onKey(final InputEvent.KeyInputEvent event) {
        Main.getModuleManager().get().stream().filter(module -> Keyboard.getEventKey() != 0 && Keyboard.getEventKeyState() && Keyboard.getEventKey() == module.getKey()).forEach(Module::toggle);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        Main.getFpsManager().update();
        Main.getPulseManager().update();
        (new Thread(() -> {
            Main.getSettingManager().get().forEach((s) -> {
                if (s.getType().equals(Setting.Type.C) && s.isRainbow()) {
                    float[] hsb = Color.RGBtoHSB(((Color)s.getValue()).getRed(), ((Color)s.getValue()).getGreen(), ((Color)s.getValue()).getBlue(), (float[])null);
                    //s.setValue(ColorUtil.injectAlpha(ColorUtil.rainbowColor(20, hsb[1], hsb[2]), ((Color)s.getValue()).getAlpha()));
                }

            });
        })).start();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = event.getPacket();
            if (packet.getEntity((World)Handler.mc.world) instanceof EntityPlayer && packet.getOpCode() == 35) {
                MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent((EntityPlayer)packet.getEntity((World)Handler.mc.world)));
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (Handler.mc.world == null || Handler.mc.player == null) {
            return;
        }
        if (event.getStage() == 0) {
            Main.getRotationManager().update();
        }
        else {
            Main.getRotationManager().reset();
        }
    }
    
    @SubscribeEvent
    public void onPacketSent(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            final CPacketChatMessage packet = event.getPacket();
            if (packet.getMessage().startsWith(CommandManager.prefix)) {
                Main.getCommandManager().parseCommand(packet.getMessage().substring(1));
                event.setCanceled(true);
            }
            else if (packet.getMessage().length() > 2 && packet.getMessage().charAt(0) == '!' && packet.getMessage().charAt(1) == '!') {
                Main.getCapeThread().sendChatMessage(packet.getMessage().substring(2));
                event.setCanceled(true);
            }
        }
    }
}
