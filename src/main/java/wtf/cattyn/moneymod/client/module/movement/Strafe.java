//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import wtf.cattyn.moneymod.util.impl.EntityUtil;
import wtf.cattyn.moneymod.api.event.MoveEvent;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Strafe", desc = "black person mode", cat = Module.Category.MOVEMENT)
public class Strafe extends Module
{
    public final Setting<Boolean> timer;
    public final Setting<Number> timerSpeed;
    public final Setting<Number> speed;
    public final Setting<Boolean> jump;
    public final Setting<Boolean> liquid;
    private double moveSpeed;
    private double lastDist;
    private int stage;
    
    public Strafe() {
        this.timer = this.register("Timer", true);
        this.timerSpeed = this.register("TimerSpeed", 1.1, 0.0, 2.0, 0.1);
        this.speed = this.register("Speed", 2.6, 0.0, 10.0, 0.1);
        this.jump = this.register("Jump", true);
        this.liquid = this.register("Liquid", true);
        this.moveSpeed = 0.0;
        this.lastDist = 0.0;
        this.stage = 4;
    }
    
    @Override
    public void onTick() {
        if (!this.liquid.getValue() && (Strafe.mc.player.isInLava() || Strafe.mc.player.isInWater())) {
            return;
        }
        this.lastDist = Math.sqrt(Math.pow(Strafe.mc.player.posX - Strafe.mc.player.prevPosX, 2.0) + Math.pow(Strafe.mc.player.posZ - Strafe.mc.player.prevPosZ, 2.0));
    }
    
    @SubscribeEvent
    public void onPlayerMove(final MoveEvent event) {
        if (EntityUtil.isMoving()) {
            if (Strafe.mc.player.onGround) {
                this.stage = 2;
            }
            if (this.timer.getValue()) {}
        }
        if (this.stage == 1 && EntityUtil.isMoving()) {
            this.stage = 2;
            this.moveSpeed = 1.38 * (this.speed.getValue().floatValue() / 10.0f);
            if (Strafe.mc.player.isPotionActive(MobEffects.SPEED)) {
                final int amplifier = Strafe.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (amplifier + 1);
            }
        }
        else if (this.stage == 2) {
            this.stage = 3;
            if (this.jump.getValue()) {
                Strafe.mc.player.motionY = 0.3995000123977661;
                event.motionY = 0.3995000123977661;
            }
            this.moveSpeed *= 2.149;
            if (Strafe.mc.player.isPotionActive(MobEffects.SPEED)) {
                final int amplifier = Strafe.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (amplifier + 1);
            }
        }
        else if (this.stage == 3) {
            this.stage = 4;
            this.moveSpeed = this.lastDist - 0.66 * (this.lastDist - this.speed.getValue().floatValue() / 10.0f);
            if (Strafe.mc.player.isPotionActive(MobEffects.SPEED)) {
                final int amplifier = Strafe.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                this.moveSpeed *= 1.0 + 0.2 * (amplifier + 1);
            }
        }
        else {
            if (Strafe.mc.world.getCollisionBoxes((Entity)Strafe.mc.player, Strafe.mc.player.getEntityBoundingBox().offset(0.0, Strafe.mc.player.motionY, 0.0)).size() > 0 || Strafe.mc.player.collidedVertically) {
                this.stage = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        this.moveSpeed = Math.min(Math.max(this.moveSpeed, this.speed.getValue().floatValue() / 10.0f), 0.551);
        float forward = Strafe.mc.player.movementInput.moveForward;
        float strafe = Strafe.mc.player.movementInput.moveStrafe;
        float yaw = Strafe.mc.player.rotationYaw;
        if (!EntityUtil.isMoving()) {
            event.motionX = 0.0;
            event.motionZ = 0.0;
        }
        else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
                strafe = 0.0f;
            }
            else if (strafe <= -1.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        event.motionX = forward * this.moveSpeed * cos + strafe * this.moveSpeed * sin;
        event.motionZ = forward * this.moveSpeed * sin - strafe * this.moveSpeed * cos;
        if (!EntityUtil.isMoving()) {
            event.motionX = 0.0;
            event.motionZ = 0.0;
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.moveSpeed = 0.0;
            this.lastDist = 0.0;
            this.stage = 4;
        }
    }
}
