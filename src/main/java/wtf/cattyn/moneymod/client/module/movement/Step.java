//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import net.minecraft.entity.Entity;
import wtf.cattyn.moneymod.mixin.mixins.AccessorEntityPlayerSP;
import net.minecraft.network.Packet;
import wtf.cattyn.moneymod.api.event.MotionUpdateEvent;
import wtf.cattyn.moneymod.api.event.UpdateEvent;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.api.event.StepEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Step", desc = "Changes player's step height", cat = Module.Category.MOVEMENT)
public class Step extends Module
{
    public final Setting<String> stepmode;
    public final Setting<Number> height;
    public final Setting<Boolean> upwards;
    public final Setting<Boolean> reverse;
    public final Setting<Boolean> timer;
    public final Setting<Boolean> entitystep;
    private ConcurrentLinkedQueue packetqueue;
    private double[] positions1;
    private double[] positions2;
    private double[] positions3;
    private double[] positions4;
    private int wait;
    
    public Step() {
        this.stepmode = this.register("Mode", "NCP", Arrays.asList("NCP", "Vanilla"));
        this.height = this.register("Height", 2.0, 1.0, 2.5, 0.1);
        this.upwards = this.register("Upwards", true);
        this.reverse = this.register("Reverse", false);
        this.timer = this.register("Timer", false);
        this.entitystep = this.register("EntityStep", false);
        (this.positions1 = new double[2])[0] = 0.42;
        this.positions1[1] = 0.75;
        (this.positions2 = new double[6])[0] = 0.42;
        this.positions2[1] = 0.75;
        this.positions2[2] = 1.0;
        this.positions2[3] = 1.16;
        this.positions2[4] = 1.23;
        this.positions2[5] = 1.2;
        (this.positions3 = new double[8])[0] = 0.425;
        this.positions3[1] = 0.821;
        this.positions3[2] = 0.699;
        this.positions3[3] = 0.599;
        this.positions3[4] = 1.022;
        this.positions3[5] = 1.372;
        this.positions3[6] = 1.652;
        this.positions3[7] = 1.869;
        (this.positions4 = new double[10])[0] = 0.425;
        this.positions4[1] = 0.821;
        this.positions4[2] = 0.699;
        this.positions4[3] = 0.599;
        this.positions4[4] = 1.022;
        this.positions4[5] = 1.372;
        this.positions4[6] = 1.652;
        this.positions4[7] = 1.869;
        this.positions4[8] = 2.019;
        this.positions4[9] = 1.907;
        this.packetqueue = new ConcurrentLinkedQueue();
        this.wait = 0;
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer.Rotation && !this.packetqueue.isEmpty() && !this.packetqueue.contains(event.getPacket())) {
            this.packetqueue.add(event.getPacket());
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onStep(final StepEvent event) {
        if (!this.upwards.getValue()) {
            return;
        }
        if (!this.packetqueue.isEmpty()) {
            return;
        }
        if (!Step.mc.player.onGround) {
            return;
        }
        if (event.getStage() == 0) {
            Step.mc.player.stepHeight = this.height.getValue().floatValue();
            return;
        }
        if (event.getStage() == 1) {
            final double d = Step.mc.player.getEntityBoundingBox().minY - Step.mc.player.posY;
            if (Double.compare(d, 0.0) <= 0 || !this.stepmode.getValue().equals("NCP")) {
                Step.mc.player.stepHeight = 0.6f;
                return;
            }
            if (d > 1.0) {
                if (Double.compare(d, 1.5) > 0) {
                    if (Double.compare(d, 2.0) > 0) {
                        if (Double.compare(d, 2.5) <= 0) {
                            this.sendPositionPackets(this.positions4);
                            if (this.timer.getValue()) {
                                Main.TIMER_VALUE = 0.15f;
                            }
                        }
                    }
                    else {
                        this.sendPositionPackets(this.positions3);
                        if (this.timer.getValue()) {
                            Main.TIMER_VALUE = 0.25f;
                        }
                    }
                }
                else {
                    this.sendPositionPackets(this.positions2);
                    if (this.timer.getValue()) {
                        Main.TIMER_VALUE = 0.35f;
                    }
                }
            }
            else {
                this.sendPositionPackets(this.positions1);
                if (this.timer.getValue()) {
                    Main.TIMER_VALUE = 0.6f;
                }
            }
        }
        this.wait = 1;
        Step.mc.player.stepHeight = 0.6f;
    }
    
    @SubscribeEvent
    public void onUpdate(final UpdateEvent event) {
        this.info = this.stepmode.getValue();
        if (this.height.getValue().doubleValue() < this.height.getMin()) {
        }
        if (this.entitystep.getValue() && Step.mc.player.getRidingEntity() != null && Step.mc.player.getRidingEntity().stepHeight != this.height.getValue().floatValue()) {
            Step.mc.player.getRidingEntity().stepHeight = this.height.getValue().floatValue();
        }
    }
    
    @SubscribeEvent
    public void onMotionUpdate(final MotionUpdateEvent event) {
        if (event.stage != 0) {
            if (this.wait > 0) {
                --this.wait;
                return;
            }
            if (this.timer.getValue()) {
                Main.TIMER_VALUE = 1.0f;
            }
        }
        else {
            if (!this.packetqueue.isEmpty() && this.wait <= 0) {
                for (int n = 0, n2 = 0; n2 < 4; n2 = ++n) {
                    final Packet packet = (Packet) this.packetqueue.peek();
                    if (packet != null) {
                        Step.mc.player.connection.sendPacket(packet);
                        this.packetqueue.poll();
                    }
                }
            }
            if (Step.mc.world == null || Step.mc.player == null || Step.mc.player.isInWater() || Step.mc.player.isInLava() || Step.mc.player.isOnLadder() || Step.mc.gameSettings.keyBindJump.isKeyDown()) {
                return;
            }
            if (this.reverse.getValue() && ((AccessorEntityPlayerSP)Step.mc.player).wasPreviouslyOnGround() && !Step.mc.player.onGround && !Step.mc.player.isInWater() && !Step.mc.player.isOnLadder()) {
                for (double d = 0.0, d2 = 0.0; d2 < this.height.getValue().floatValue() + 0.5; d2 = d + 0.01) {
                    if (!Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(0.0, -d, 0.0)).isEmpty()) {
                        Step.mc.player.motionY = -10.0;
                        return;
                    }
                }
            }
        }
    }
    
    public void onDisable() {
        if (Step.mc.player != null) {
            Step.mc.player.stepHeight = 0.6f;
        }
        Main.TIMER_VALUE = 1.0f;
    }
    
    public void sendPositionPackets(final double[] y) {
        if (this.stepmode.getValue().equals("NCP")) {
            for (int n = 0, n2 = 0; n2 < y.length; n2 = ++n) {
                final CPacketPlayer.Position packet = new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + y[n], Step.mc.player.posZ, Step.mc.player.onGround);
                if (n > 6) {
                    this.packetqueue.add(packet);
                }
                Step.mc.player.connection.sendPacket((Packet)packet);
            }
        }
    }
}
