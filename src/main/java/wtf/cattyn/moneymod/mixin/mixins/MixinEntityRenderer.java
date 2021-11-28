// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.client.module.render.NameTags;
import wtf.cattyn.moneymod.Main;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.moneymod.api.event.Render3DEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer
{
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.clear(I)V", ordinal = 1, shift = At.Shift.AFTER) })
    private void renderWorldPass_Pre(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo info) {
        final Render3DEvent event = new Render3DEvent(partialTicks);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "drawNameplate" }, at = { @At("HEAD") }, cancellable = true)
    private static void drawNameplate(final FontRenderer fontRendererIn, final String str, final float x, final float y, final float z, final int verticalShift, final float viewerYaw, final float viewerPitch, final boolean isThirdPersonFrontal, final boolean isSneaking, final CallbackInfo callbackInfo) {
        if (Main.getModuleManager().get(NameTags.class).isToggled()) {
            callbackInfo.cancel();
        }
    }
}
