//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.Main;
import net.minecraft.network.play.server.SPacketChat;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import wtf.cattyn.moneymod.util.impl.Timer;
import wtf.cattyn.moneymod.api.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AutoEz", desc = "Types ez in the chat after kill", cat = Module.Category.MISC)
public class AutoEz extends Module
{
    private static EntityPlayer target;
    String[] message;
    String ezMessage;
    int kilStreak;
    private final Setting<Boolean> prikol;
    private final Setting<Boolean> killStreak;
    private final Setting<Boolean> randomHack;
    private final Setting<Number> pDelay;
    private Timer timer;
    String[] randomHackMessage;
    
    public AutoEz() {
        this.message = new String[] { "Wow he died so fast lmfao", "rekt", "LOL AHAHHA NICE IQ", "THE STATE", "MONEYMODLESS AHAHAHAHAH", "Iq issue retard" };
        this.ezMessage = "Good Game! My Dick is Stuck In Car Exhaust Pipe It Hurts. Thanks To MoneyMod+3";
        this.prikol = this.register("Helping!!!", true);
        this.killStreak = this.register("KillStreak", true);
        this.randomHack = this.register("RandomHack", false);
        this.pDelay = this.register("Helping Delay", 1.0, 0.1, 10.0, 0.1);
        this.timer = new Timer();
        this.randomHackMessage = new String[] { "is a noob hahaha fobus on tope", "Good fight! Konas owns me and all", "I guess konas ca is too fast for you", "you just got nae nae'd by konas", "I was AFK!", "you just got nae nae'd by wurst+" };
    }
    
    public static void setTarget(final EntityPlayer player) {
        AutoEz.target = player;
    }
    
    @Override
    protected void onEnable() {
        this.timer.reset();
        this.kilStreak = 0;
    }
    
    @Override
    protected void onDisable() {
        this.timer.reset();
        this.kilStreak = 0;
    }
    
    @Override
    public void onTick() {
        if (AutoEz.target != null && AutoEz.mc.player.getDistanceSq((Entity)AutoEz.target) < 150.0) {
            if (AutoEz.target.isDead || AutoEz.target.getHealth() <= 0.0f) {
                AutoEz.mc.player.sendChatMessage(((boolean)this.randomHack.getValue()) ? this.randomHackMessage[AutoEz.random.nextInt(this.randomHackMessage.length)] : ("> " + AutoEz.target.getName() + " " + this.ezMessage + " [$]"));
                ++this.kilStreak;
                if (this.killStreak.getValue()) {
                    ChatUtil.sendMessage(String.format("%s[%sKillStreak%s] %s kills!", ChatFormatting.YELLOW, ChatFormatting.GOLD, ChatFormatting.YELLOW, this.kilStreak));
                }
            }
            AutoEz.target = null;
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = event.getPacket();
            final String text = packet.getChatComponent().getFormattedText();
            if (this.prikol.getValue()) {
                for (final String name : Main.getFriendManager().get(0)) {
                    if (text.contains(String.format("<%s>", name)) && text.toLowerCase().contains("[$]") && this.timer.passed((long)(this.pDelay.getValue().doubleValue() * 1000.0))) {
                        AutoEz.mc.player.sendChatMessage("> " + this.message[AutoEz.random.nextInt(this.message.length)]);
                        this.timer.reset();
                        break;
                    }
                }
            }
        }
    }
}
