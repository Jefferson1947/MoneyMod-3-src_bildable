//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import wtf.cattyn.moneymod.api.event.UpdatePlayerMoveStateEvent;
import net.minecraft.util.MovementInput;
import wtf.cattyn.moneymod.api.event.UpdateWalkingPlayerEvent;
import wtf.cattyn.moneymod.api.event.MotionUpdateEvent;
import wtf.cattyn.moneymod.util.Globals;
import org.spongepowered.asm.mixin.injection.Inject;
import wtf.cattyn.moneymod.api.event.UpdateEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.moneymod.api.event.MoveEvent;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.client.module.movement.Sprint;
import wtf.cattyn.moneymod.Main;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.entity.AbstractClientPlayer;

@Mixin(value = { EntityPlayerSP.class }, priority = 9999)
public class MixinEntityPlayerSP extends AbstractClientPlayer
{
    public MixinEntityPlayerSP(final World worldIn, final GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setSprinting(Z)V", ordinal = 2))
    public void setSprinting(final EntityPlayerSP player, boolean value) {
        if (Main.getModuleManager().get(Sprint.class).isToggled()) {
            player.setSprinting(true);
        }
        else {
            player.setSprinting(value);
        }
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(final AbstractClientPlayer player, final MoverType type, final double x, final double y, final double z) {
        final MoveEvent event = new MoveEvent(x, y, z);
        MinecraftForge.EVENT_BUS.post((Event)event);
        super.move(type, event.motionX, event.motionY, event.motionZ);
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("HEAD") })
    public void onUpdatePre(final CallbackInfo info) {
        final UpdateEvent event = new UpdateEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", shift = At.Shift.BEFORE) })
    public void onPreMotionUpdate(final CallbackInfo info) {
        final MotionUpdateEvent event = new MotionUpdateEvent(Globals.mc.player.rotationYaw, Globals.mc.player.rotationPitch, Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, Globals.mc.player.onGround, Globals.mc.player.noClip, 0);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", shift = At.Shift.AFTER) })
    public void onPostMotionUpdate(final CallbackInfo info) {
        final MotionUpdateEvent event = new MotionUpdateEvent(Globals.mc.player.rotationYaw, Globals.mc.player.rotationPitch, Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ, Globals.mc.player.onGround, Globals.mc.player.noClip, 1);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") })
    public void pre(final CallbackInfo info) {
        final UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(0);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    public void post(final CallbackInfo info) {
        final UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(1);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/MovementInput;updatePlayerMoveState()V"))
    public void updatePlayerMoveState(final MovementInput input) {
        input.updatePlayerMoveState();
        final UpdatePlayerMoveStateEvent event = new UpdatePlayerMoveStateEvent(input);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
