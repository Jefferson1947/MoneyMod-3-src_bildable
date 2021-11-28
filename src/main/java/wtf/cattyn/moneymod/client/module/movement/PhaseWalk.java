//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import wtf.cattyn.moneymod.api.event.MoveEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.util.impl.EntityUtil;
import wtf.cattyn.moneymod.api.event.UpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.util.impl.Timer;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "PhaseWalk", desc = "phasing", cat = Module.Category.MOVEMENT)
public class PhaseWalk extends Module
{
    private final Setting<Number> attempts;
    private final Setting<Number> speed;
    private final Setting<Boolean> all;
    private final Setting<Boolean> edgeEnable;
    Timer timer;
    boolean cancel;
    int teleportID;
    
    public PhaseWalk() {
        this.attempts = this.register("Attempts", 10.0, 0.0, 10.0, 1.0);
        this.speed = this.register("Speed", 3.0, 0.0, 10.0, 1.0);
        this.all = this.register("All", false);
        this.edgeEnable = this.register("Walk", true);
        this.timer = new Timer();
        this.cancel = false;
        this.teleportID = 0;
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent packetEvent) {
        if (this.nullCheck()) {
            return;
        }
        if (packetEvent.getPacket() instanceof SPacketPlayerPosLook) {
            if (this.all.getValue()) {
                PhaseWalk.mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID - 1));
                PhaseWalk.mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID));
                PhaseWalk.mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID + 1));
            }
            else {
                PhaseWalk.mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(this.teleportID + 1));
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final UpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        PhaseWalk.mc.player.motionX = 0.0;
        PhaseWalk.mc.player.motionY = 0.0;
        PhaseWalk.mc.player.motionZ = 0.0;
        if (this.shouldPacket()) {
            if (this.timer.isPassed()) {
                final double[] dArray = EntityUtil.forward(this.getSpeed());
                for (int i = 0; i < this.attempts.getValue().intValue(); ++i) {
                    this.sendPackets(PhaseWalk.mc.player.posX + dArray[0], PhaseWalk.mc.player.posY + this.getUpMovement(), PhaseWalk.mc.player.posZ + dArray[1]);
                }
                this.timer.reset();
            }
        }
        else {
            this.cancel = false;
        }
    }
    
    double getUpMovement() {
        return (PhaseWalk.mc.gameSettings.keyBindJump.isKeyDown() ? 1 : (PhaseWalk.mc.gameSettings.keyBindSneak.isKeyDown() ? -1 : 0)) * this.getSpeed();
    }
    
    public void sendPackets(final double d, final double d2, final double d3) {
        this.cancel = false;
        PhaseWalk.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(d, d2, d3, PhaseWalk.mc.player.onGround));
        PhaseWalk.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(0.0, 1337.0, 0.0, PhaseWalk.mc.player.onGround));
        this.cancel = true;
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent moveEvent) {
        if (this.nullCheck()) {
            return;
        }
        if (this.shouldPacket()) {
            moveEvent.motionX = 0.0;
            moveEvent.motionY = 0.0;
            moveEvent.motionZ = 0.0;
        }
    }
    
    double getSpeed() {
        return this.speed.getValue().doubleValue() / 100.0;
    }
    
    boolean shouldPacket() {
        return !this.edgeEnable.getValue() || PhaseWalk.mc.player.collidedHorizontally;
    }
}
