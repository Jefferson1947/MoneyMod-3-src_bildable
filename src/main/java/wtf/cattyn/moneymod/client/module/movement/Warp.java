//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraft.entity.MoverType;
import wtf.cattyn.moneymod.Main;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Arrays;
import net.minecraft.network.Packet;
import java.util.Queue;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Warp", cat = Module.Category.MOVEMENT)
public class Warp extends Module
{
    public final Setting<String> mode;
    public final Setting<Boolean> step;
    public final Setting<Number> time;
    public final Setting<Number> tick;
    int timer;
    Queue<Packet<?>> packets;
    
    public Warp() {
        this.mode = this.register("Mode", "Tick", Arrays.asList("Skip", "Tick", "LagTick", "Timer"));
        this.step = this.register("Step", false);
        this.time = this.register("Time", 8.0, 0.1, 16.0, 1.0);
        this.tick = this.register("Tick", 4.0, 0.1, 6.0, 0.1);
        this.packets = new ConcurrentLinkedQueue<Packet<?>>();
    }
    
    public void doNull() {
        this.timer = 0;
        Main.TIMER_VALUE = 1.0f;
        Warp.mc.player.stepHeight = 0.6f;
    }
    
    public void onEnable() {
        this.doNull();
    }
    
    public void onDisable() {
        while (!this.packets.isEmpty()) {
            Warp.mc.getConnection().sendPacket((Packet)this.packets.poll());
        }
        this.doNull();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            this.doNull();
            this.setToggled(false);
            return;
        }
        if (this.step.getValue()) {
            Warp.mc.player.stepHeight = 2.0f;
        }
        if (this.mode.getValue().equalsIgnoreCase("Tick") || this.mode.getValue().equalsIgnoreCase("LagTick")) {
            ++this.timer;
            Main.TIMER_VALUE = this.tick.getValue().floatValue();
            if (this.timer >= this.time.getValue().floatValue()) {
                this.doNull();
                this.setToggled(false);
                return;
            }
        }
        if (this.mode.getValue().equalsIgnoreCase("Skip")) {
            ++this.timer;
            final double y = Warp.mc.player.rotationYaw * 0.017453292;
            final double p = this.tick.getValue().floatValue();
            for (float tim = 0.0f; tim <= this.time.getValue().floatValue(); tim += (float)0.1) {
                Warp.mc.player.move(MoverType.PLAYER, -Math.sin(y) * p, 0.0, Math.cos(y) * p);
                Main.getRotationManager().set(Warp.mc.player.rotationYaw, Warp.mc.player.rotationPitch, false);
            }
            if (this.timer >= this.time.getValue().floatValue()) {
                this.doNull();
                this.setToggled(false);
            }
        }
        if (this.mode.getValue().equalsIgnoreCase("Timer")) {
            Main.TIMER_VALUE = this.tick.getValue().floatValue();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.mode.getValue().equalsIgnoreCase("LagTick")) {
            if (this.timer >= this.time.getValue().intValue() && this.mode.getValue().equalsIgnoreCase("LagTick")) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayer) {
                this.packets.add(event.getPacket());
                event.setCanceled(true);
            }
        }
    }
}
