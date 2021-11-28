//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.render.CustomCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderEnderCrystal.class })
public class MixinRenderEnderCrystal
{
    @Redirect(method = { "doRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void doRender(final ModelBase modelBase, final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        final CustomCrystal cc = (CustomCrystal)Main.getModuleManager().get(CustomCrystal.class);
        if (cc.isToggled()) {
            final float red = cc.cColor.getValue().getRed() / 255.0f;
            final float green = cc.cColor.getValue().getGreen() / 255.0f;
            final float blue = cc.cColor.getValue().getBlue() / 255.0f;
            final float alpha = cc.cColor.getValue().getAlpha() / 255.0f;
            final float limb = cc.cancelRotation.getValue() ? 0.0f : limbSwingAmount;
            final float age = cc.cancelPosChange.getValue() ? 0.0f : ageInTicks;
            final float scales = cc.scale.getValue().floatValue();
            GlStateManager.scale(scales, scales, scales);
            if (cc.lines.getValue()) {
                GL11.glPushMatrix();
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6913);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor3f(red, green, blue);
                GL11.glLineWidth(cc.linesWidht.getValue().floatValue());
                modelBase.render(entityIn, limbSwing, limb, age, netHeadYaw, headPitch, scale);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (cc.chams.getValue()) {
                GL11.glPushMatrix();
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                if (cc.lines.getValue()) {
                    GL11.glLineWidth(cc.linesWidht.getValue().floatValue());
                }
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
                GL11.glColor4f(red, green, blue, alpha);
                modelBase.render(entityIn, limbSwing, limb, age, netHeadYaw, headPitch, scale);
                GL11.glDisable(2848);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glEnable(3042);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
        }
        else {
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
}
