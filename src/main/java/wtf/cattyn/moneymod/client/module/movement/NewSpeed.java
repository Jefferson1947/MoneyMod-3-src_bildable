//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.init.MobEffects;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemFood;
import java.util.Random;
import net.minecraft.entity.Entity;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import wtf.cattyn.moneymod.api.event.MoveEvent;
import wtf.cattyn.moneymod.api.event.UpdateWalkingPlayerEvent;
import wtf.cattyn.moneymod.mixin.mixins.AccessorCPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.Main;
import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "NewSpeed", desc = "Go faster", cat = Module.Category.MOVEMENT)
public class NewSpeed extends Module
{
    public final Setting<String> speedmode;
    public final Setting<Boolean> timer;
    public final Setting<Boolean> autosprint;
    public final Setting<Number> speed;
    public final Setting<Boolean> water;
    public final Setting<Boolean> ongroundstrict;
    public final Setting<Boolean> strict;
    private boolean flip;
    private int rhh;
    private int stage;
    private double moveSpeed;
    private double distance;
    
    public NewSpeed() {
        this.speedmode = this.register("Mode", "Strafe", Arrays.asList("Strafe", "OnGround", "GroundStrafe", "Vanilla"));
        this.timer = this.register("Timer", true);
        this.autosprint = this.register("AutoSprint", false);
        this.speed = this.register("VanillaSpeed", 5.0, 0.0, 10.0, 0.1);
        this.water = this.register("Water", false);
        this.ongroundstrict = this.register("OnGroundStrict", false);
        this.strict = this.register("StrafeStrict", false);
        this.flip = false;
        this.rhh = 0;
        this.stage = 0;
        this.moveSpeed = 0.0;
        this.distance = 0.0;
    }
    
    @Override
    public void onTick() {
        this.info = this.speedmode.getValue();
    }
    
    public void onToggle() {
        try {
            this.stage = 2;
            this.distance = 0.0;
            this.moveSpeed = this.getBaseMoveSpeed();
            Main.TIMER_VALUE = 1.0f;
            if (this.autosprint.getValue() && NewSpeed.mc.player != null) {
                NewSpeed.mc.player.setSprinting(false);
            }
        }
        catch (Exception ex) {}
    }
    
    public void onEnable() {
        this.onToggle();
    }
    
    public void onDisable() {
        this.onToggle();
    }
    
    @SubscribeEvent
    public void onReceiveEvent(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook && (this.speedmode.getValue().equals("Strafe") || this.speedmode.getValue().equals("OnGround"))) {
            this.rhh = 6;
            if (this.speedmode.getValue().equals("OnGround")) {
                this.stage = 2;
            }
            else {
                this.stage = 1;
                this.flip = false;
            }
            this.distance = 0.0;
            this.moveSpeed = this.getBaseMoveSpeed();
        }
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = event.getPacket();
            if (this.speedmode.getValue().equals("OnGround") && this.stage == 3) {
                ((AccessorCPacketPlayer)packet).setY(packet.getY(0.0) + 0.4);
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0) {
            final double d3 = NewSpeed.mc.player.posX - NewSpeed.mc.player.prevPosX;
            final double d4 = NewSpeed.mc.player.posZ - NewSpeed.mc.player.prevPosZ;
            this.distance = Math.sqrt(d3 * d3 + d4 * d4);
        }
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (NewSpeed.mc.player.isElytraFlying() || NewSpeed.mc.player.fallDistance >= 4.0f) {
            return;
        }
        if (!this.water.getValue() && (NewSpeed.mc.player.isInWater() || NewSpeed.mc.player.isInLava())) {
            return;
        }
        if (this.strict.getValue()) {
            ChatUtil.sendMessage("lol no strict strafe is broken fuck you");
        }
        if (this.rhh > 0) {
            --this.rhh;
        }
        if (this.autosprint.getValue()) {
            NewSpeed.mc.player.setSprinting(true);
        }
        final String mode = this.speedmode.getValue();
        if (mode.equals("Strafe")) {
            if (this.timer.getValue() && !this.strict.getValue()) {
                Main.TIMER_VALUE = 1.08f;
            }
            if (this.round(NewSpeed.mc.player.posY - (int)NewSpeed.mc.player.posY, 3) == this.round(0.138, 3)) {
                final EntityPlayerSP player = NewSpeed.mc.player;
                --player.motionY;
                event.motionY -= 0.09316090325960147;
            }
            if (this.stage == 2 && this.isMoving()) {
                if (NewSpeed.mc.player.collidedVertically) {
                    event.motionY = 0.4;
                    NewSpeed.mc.player.motionY = 0.3995;
                    this.flip = !this.flip;
                    if (this.strict.getValue()) {
                        Main.TIMER_VALUE = (this.flip ? 1.08f : 1.0f);
                    }
                    else {
                        Main.TIMER_VALUE = 1.0f;
                    }
                    if (!this.strict.getValue()) {
                        if (this.flip) {
                            this.moveSpeed *= 1.5499999523162842;
                        }
                        else {
                            this.moveSpeed *= 1.3949999809265137;
                        }
                    }
                    else if (this.flip) {
                        this.moveSpeed *= 1.899999976158142;
                    }
                    else {
                        this.moveSpeed *= 1.850000023841858;
                    }
                }
            }
            else if (this.stage == 3 && this.isMoving()) {
                final double var = 0.66 * (this.distance - this.getBaseMoveSpeed());
                this.moveSpeed = this.distance - var;
                if (this.timer.getValue()) {
                    if (this.flip) {
                        if (this.strict.getValue()) {
                            Main.TIMER_VALUE = 1.08f;
                        }
                        else {
                            Main.TIMER_VALUE = 1.125f;
                        }
                    }
                    else if (this.strict.getValue()) {
                        Main.TIMER_VALUE = 1.05f;
                    }
                    else {
                        Main.TIMER_VALUE = 1.0088f;
                    }
                }
            }
            else {
                if (NewSpeed.mc.world.getCollisionBoxes((Entity)NewSpeed.mc.player, NewSpeed.mc.player.getEntityBoundingBox().offset(0.0, NewSpeed.mc.player.motionY, 0.0)).size() > 0 || NewSpeed.mc.player.collidedVertically) {
                    this.stage = 1;
                }
                this.moveSpeed = this.distance - this.distance / 159.0;
            }
            float val = 1.0f;
            if (this.strict.getValue()) {
                if (this.flip) {
                    val = 0.983f;
                }
                else {
                    val = 0.975f;
                }
            }
            val *= (float)this.getBaseMoveSpeed();
            this.moveSpeed = Math.max(this.moveSpeed, val);
            final float[] dir = this.rhc(this.moveSpeed);
            event.motionX = dir[0];
            event.motionZ = dir[1];
            ++this.stage;
        }
        else if (mode.equals("OnGround")) {
            if (NewSpeed.mc.player.collidedHorizontally || !this.checkMove()) {
                Main.TIMER_VALUE = 1.0f;
            }
            else {
                if (!this.ongroundstrict.getValue()) {
                    if (!NewSpeed.mc.player.onGround) {
                        Main.TIMER_VALUE = 1.0f;
                    }
                    else if (this.stage == 2) {
                        Main.TIMER_VALUE = 1.0f;
                        if (this.rhh > 0) {
                            this.moveSpeed = this.getBaseMoveSpeed();
                        }
                        this.moveSpeed *= 2.149;
                        this.stage = 3;
                    }
                    else if (this.stage == 3) {
                        if (this.timer.getValue()) {
                            Main.TIMER_VALUE = Math.max(1.0f + new Random().nextFloat(), 1.2f);
                        }
                        else {
                            Main.TIMER_VALUE = 1.0f;
                        }
                        this.stage = 2;
                        final double var = 0.66 * (this.distance - this.getBaseMoveSpeed());
                        this.moveSpeed = this.distance - var;
                    }
                }
                this.rhQ_rhP(event, this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed()));
            }
        }
        else if (mode.equals("GroundStrafe")) {
            if (NewSpeed.mc.player.collidedHorizontally || NewSpeed.mc.player.movementInput.sneak) {
                return;
            }
            if (NewSpeed.mc.player.isHandActive() && NewSpeed.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood) {
                return;
            }
            if (!this.checkMove()) {
                return;
            }
            if (NewSpeed.mc.player.onGround) {
                if (NewSpeed.mc.player.ticksExisted % 2 == 0) {
                    Main.TIMER_VALUE = 1.0f;
                    this.stage = 2;
                }
                else {
                    if (this.timer.getValue()) {
                        Main.TIMER_VALUE = 1.2f;
                    }
                    else {
                        Main.TIMER_VALUE = 1.0f;
                    }
                    this.stage = 3;
                }
                this.moveSpeed = this.getBaseMoveSpeed();
            }
            else {
                Main.TIMER_VALUE = 1.0f;
                this.stage = 0;
            }
            this.rhQ_rhP(event, this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed()));
        }
        else if (mode.equals("Vanilla")) {
            final double speedval = (double)this.speed.getValue() / 5.0;
            this.rhQ_rhP(event, speedval);
        }
    }
    
    public boolean isMoving() {
        return NewSpeed.mc.player.movementInput.moveForward != 0.0f || NewSpeed.mc.player.movementInput.moveStrafe != 0.0f;
    }
    
    public double getBaseMoveSpeed() {
        double d = 0.2873;
        if (NewSpeed.mc.player != null && NewSpeed.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int n = NewSpeed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * (n + 1);
        }
        return d;
    }
    
    public double round(final double value, final int places) {
        final BigDecimal b = new BigDecimal(value).setScale(places, RoundingMode.HALF_UP);
        return b.doubleValue();
    }
    
    public boolean checkMove() {
        return NewSpeed.mc.player.moveForward != 0.0f || NewSpeed.mc.player.moveStrafing != 0.0f;
    }
    
    public void rhQ_rhP(final MoveEvent event, final double speed) {
        float moveForward = NewSpeed.mc.player.movementInput.moveForward;
        float moveStrafe = NewSpeed.mc.player.movementInput.moveStrafe;
        float rotationYaw = NewSpeed.mc.player.rotationYaw;
        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            event.motionX = 0.0;
            event.motionZ = 0.0;
            return;
        }
        if (moveForward != 0.0f) {
            if (moveStrafe >= 1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45.0f : 45.0f);
                moveStrafe = 0.0f;
            }
            else if (moveStrafe <= -1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45.0f : -45.0f);
                moveStrafe = 0.0f;
            }
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double motionX = Math.cos(Math.toRadians(rotationYaw + 90.0f));
        final double motionZ = Math.sin(Math.toRadians(rotationYaw + 90.0f));
        final double newX = moveForward * speed * motionX + moveStrafe * speed * motionZ;
        final double newZ = moveForward * speed * motionZ - moveStrafe * speed * motionX;
        event.motionX = newX;
        event.motionZ = newZ;
    }
    
    public float[] rhf(final float yaw, final double niggers) {
        float moveForward = NewSpeed.mc.player.movementInput.moveForward;
        float moveStrafe = NewSpeed.mc.player.movementInput.moveStrafe;
        float rotationYaw = yaw;
        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            final float[] ret = { 0.0f, 0.0f };
            return ret;
        }
        if (moveForward != 0.0f) {
            if (moveStrafe >= 1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45.0f : 45.0f);
                moveStrafe = 0.0f;
            }
            else if (moveStrafe <= -1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45.0f : -45.0f);
                moveStrafe = 0.0f;
            }
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double motionX = Math.cos(Math.toRadians(rotationYaw + 90.0f));
        final double motionZ = Math.sin(Math.toRadians(rotationYaw + 90.0f));
        final double newX = moveForward * niggers * motionX + moveStrafe * niggers * motionZ;
        final double newZ = moveForward * niggers * motionZ - moveStrafe * niggers * motionX;
        final float[] ret2 = { (float)newX, (float)newZ };
        return ret2;
    }
    
    public float[] rhc(final double niggers) {
        final float yaw = NewSpeed.mc.player.prevRotationYaw + (NewSpeed.mc.player.rotationYaw - NewSpeed.mc.player.prevRotationYaw) * NewSpeed.mc.getRenderPartialTicks();
        return this.rhf(yaw, niggers);
    }
}
