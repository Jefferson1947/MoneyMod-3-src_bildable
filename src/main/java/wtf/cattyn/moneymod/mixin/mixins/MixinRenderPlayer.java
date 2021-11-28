//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.moneymod.api.event.RenderPlayerRotationsEvent;
import net.minecraft.client.gui.inventory.GuiInventory;
import wtf.cattyn.moneymod.util.Globals;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderPlayer.class })
public class MixinRenderPlayer
{
    private float yaw;
    private float pitch;
    private float yawoffset;
    private float prevyaw;
    private float prevpitch;
    private float prevyawoffset;
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    public void doRenderPre(final AbstractClientPlayer player, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo info) {
        if (player.equals((Object)Globals.mc.player) && Globals.mc.gameSettings.thirdPersonView != 0 && !(Globals.mc.currentScreen instanceof GuiInventory)) {
            final RenderPlayerRotationsEvent event = new RenderPlayerRotationsEvent(Globals.mc.player.rotationYawHead, Globals.mc.player.rotationPitch);
            MinecraftForge.EVENT_BUS.post((Event)event);
            this.yaw = player.rotationYawHead;
            this.pitch = player.rotationPitch;
            this.prevyaw = player.prevRotationYawHead;
            this.prevpitch = player.prevRotationPitch;
            this.yawoffset = player.renderYawOffset;
            this.prevyawoffset = player.prevRenderYawOffset;
            if (this.yaw != event.getYaw() || this.pitch != event.getPitch()) {
                player.rotationYawHead = event.getYaw();
                player.prevRotationYawHead = event.getYaw();
                player.renderYawOffset = event.getYaw();
                player.prevRenderYawOffset = event.getYaw();
                player.rotationPitch = event.getPitch();
                player.prevRotationPitch = event.getPitch();
            }
        }
    }
    
    @Inject(method = { "doRender" }, at = { @At("RETURN") })
    public void doRenderPost(final AbstractClientPlayer player, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo info) {
        if (player.equals((Object)Globals.mc.player) && Globals.mc.gameSettings.thirdPersonView != 0 && !(Globals.mc.currentScreen instanceof GuiInventory)) {
            player.rotationYawHead = this.yaw;
            player.prevRotationYawHead = this.prevyaw;
            player.renderYawOffset = this.yawoffset;
            player.prevRenderYawOffset = this.prevyawoffset;
            player.rotationPitch = this.pitch;
            player.prevRotationPitch = this.prevpitch;
        }
    }
}
