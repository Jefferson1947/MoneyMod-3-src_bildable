//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.util.EnumHand;
import org.lwjgl.opengl.GL11;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.render.ViewModel;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderItem;
import org.spongepowered.asm.mixin.Mixin;
import wtf.cattyn.moneymod.util.Globals;

@Mixin(value = { RenderItem.class }, priority = Integer.MAX_VALUE)
public abstract class MixinRenderItem implements Globals
{
    @Inject(method = { "renderItemModel" }, at = { @At("INVOKE") })
    public void renderItem(final ItemStack stack, final IBakedModel bakedmodel, final ItemCameraTransforms.TransformType transform, final boolean leftHanded, final CallbackInfo ci) {
        final ViewModel viewChanger = (ViewModel)Main.getModuleManager().get(ViewModel.class);
        if (viewChanger.isToggled() && (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transform == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)) {
            GL11.glScalef(viewChanger.scX.getValue().floatValue() / 10.0f, viewChanger.scY.getValue().floatValue() / 10.0f, viewChanger.scZ.getValue().floatValue() / 10.0f);
            if (transform.equals((Object)ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND)) {
                if (MixinRenderItem.mc.player.getActiveHand() == EnumHand.OFF_HAND && MixinRenderItem.mc.player.isHandActive()) {
                    return;
                }
                GL11.glTranslated((double)(viewChanger.posX.getValue().floatValue() / 15.0f), (double)(viewChanger.posY.getValue().floatValue() / 15.0f), (double)(viewChanger.posZ.getValue().floatValue() / 15.0f));
            }
            else {
                if (MixinRenderItem.mc.player.getActiveHand() == EnumHand.MAIN_HAND && MixinRenderItem.mc.player.isHandActive()) {
                    return;
                }
                GL11.glTranslated((double)(-viewChanger.posX.getValue().floatValue() / 15.0f), (double)(viewChanger.posY.getValue().floatValue() / 15.0f), (double)(viewChanger.posZ.getValue().floatValue() / 15.0f));
            }
        }
    }
}
