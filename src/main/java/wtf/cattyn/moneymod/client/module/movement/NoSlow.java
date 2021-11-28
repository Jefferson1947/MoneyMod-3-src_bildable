//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import wtf.cattyn.moneymod.api.event.MoveEvent;
import wtf.cattyn.moneymod.api.event.SoulSandCollisionEvent;
import wtf.cattyn.moneymod.mixin.mixins.AccessorCPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraft.util.MovementInput;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import wtf.cattyn.moneymod.mixin.ducks.AccessorEntity;
import wtf.cattyn.moneymod.api.event.UpdatePlayerMoveStateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import wtf.cattyn.moneymod.api.event.MotionUpdateEvent;
import wtf.cattyn.moneymod.Main;
import net.minecraft.init.Blocks;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "NoSlow", desc = "Removes movement slow down", cat = Module.Category.MOVEMENT)
public class NoSlow extends Module
{
    public Setting<Boolean> inventory;
    public Setting<Boolean> items;
    public Setting<Boolean> ice;
    public Setting<Boolean> webs;
    public Setting<Boolean> soulsand;
    public Setting<Boolean> slimeblocks;
    public Setting<Boolean> strict;
    public Setting<Boolean> fastfall;
    public Setting<Boolean> strict2b2t;
    private boolean idk;
    private boolean fastfallstate;
    
    public NoSlow() {
        this.inventory = this.register("Inventory", true);
        this.items = this.register("Items", true);
        this.ice = this.register("Ice", true);
        this.webs = this.register("Webs", true);
        this.soulsand = this.register("SoulSand", true);
        this.slimeblocks = this.register("SlimeBlocks", true);
        this.strict = this.register("Strict", false);
        this.fastfall = this.register("WebsFastFall", false);
        this.strict2b2t = this.register("2b2t", false);
        this.idk = false;
        this.fastfallstate = false;
    }
    
    public void onEnable() {
        this.idk = false;
        this.fastfallstate = false;
    }
    
    public void onDisable() {
        this.idk = false;
        this.fastfallstate = false;
        Blocks.ICE.setDefaultSlipperiness(0.98f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
        Blocks.SLIME_BLOCK.setDefaultSlipperiness(0.8f);
        Main.TIMER_VALUE = 1.0f;
    }
    
    @SubscribeEvent
    public void onPreMotionUpdate(final MotionUpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.ice.getValue()) {
            if (NoSlow.mc.player.getRidingEntity() != null) {
                Blocks.ICE.setDefaultSlipperiness(0.98f);
                Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
                Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
            }
            else {
                Blocks.ICE.setDefaultSlipperiness(0.6f);
                Blocks.FROSTED_ICE.setDefaultSlipperiness(0.6f);
                Blocks.PACKED_ICE.setDefaultSlipperiness(0.6f);
            }
        }
        if (this.slimeblocks.getValue()) {
            if (NoSlow.mc.player.getRidingEntity() != null) {
                Blocks.SLIME_BLOCK.setDefaultSlipperiness(0.8f);
            }
            else {
                Blocks.SLIME_BLOCK.setDefaultSlipperiness(0.6f);
            }
        }
        if (this.items.getValue() && NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding() && this.strict2b2t.getValue() && !this.idk && !NoSlow.mc.player.onGround) {
            this.idk = true;
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlow.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            return;
        }
        if (!this.idk || !NoSlow.mc.player.onGround) {
            return;
        }
        if (!NoSlow.mc.player.isHandActive()) {
            this.idk = false;
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    @SubscribeEvent
    public void onUpdatePlayerMoveState(final UpdatePlayerMoveStateEvent event) {
        if (this.webs.getValue() && ((AccessorEntity)NoSlow.mc.player).isInWeb()) {
            this.fastfallstate = (!NoSlow.mc.player.onGround && this.fastfall.getValue());
            if (!this.strict.getValue() && NoSlow.mc.player.onGround) {
                ((AccessorEntity)NoSlow.mc.player).setInWeb(false);
                final EntityPlayerSP player = NoSlow.mc.player;
                player.motionX *= 0.05000000074505806;
                final EntityPlayerSP player2 = NoSlow.mc.player;
                player2.motionZ *= 0.05000000074505806;
                if (NoSlow.mc.player.getRidingEntity() != null) {
                    ((AccessorEntity)NoSlow.mc.player.getRidingEntity()).setInWeb(false);
                }
            }
        }
        else {
            this.fastfallstate = false;
        }
        if (this.items.getValue() && NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding()) {
            if (this.strict.getValue()) {
                NoSlow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, NoSlow.mc.player.getPosition(), EnumFacing.DOWN));
            }
            final MovementInput movementInput = NoSlow.mc.player.movementInput;
            movementInput.moveForward /= 0.2f;
            final MovementInput movementInput2 = NoSlow.mc.player.movementInput;
            movementInput2.moveStrafe /= 0.2f;
        }
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = event.getPacket();
            final AccessorCPacketPlayer ac = (AccessorCPacketPlayer)packet;
            if (this.fastfallstate) {
                if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
                    NoSlow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(packet.getX(0.0), packet.getY(0.0), packet.getZ(0.0), packet.isOnGround()));
                    event.setCanceled(true);
                    return;
                }
                if (event.getPacket() instanceof CPacketPlayer.Rotation) {
                    event.setCanceled(true);
                    return;
                }
            }
            if (this.items.getValue() && NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding() && this.strict2b2t.getValue() && NoSlow.mc.player.onGround && !Main.getModuleManager().get(NewSpeed.class).isToggled()) {
                if (NoSlow.mc.player.ticksExisted % 2 == 0 && packet.isOnGround() && !NoSlow.mc.player.isElytraFlying()) {
                    ac.setY(packet.getY(0.0) + 0.05);
                }
                ac.setOnGround(false);
            }
        }
    }
    
    @SubscribeEvent
    public void onSoulSandCollision(final SoulSandCollisionEvent event) {
        if (this.soulsand.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.fastfallstate) {
            event.motionY *= (this.checkMove() ? 80.0 : 40.0);
            final EntityPlayerSP player = NoSlow.mc.player;
            player.motionY *= (this.checkMove() ? 80.0 : 40.0);
        }
    }
    
    public boolean checkMove() {
        return NoSlow.mc.player.movementInput.moveStrafe != 0.0f || NoSlow.mc.player.movementInput.moveForward != 0.0f;
    }
}
