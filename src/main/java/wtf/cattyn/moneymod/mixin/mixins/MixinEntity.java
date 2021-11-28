//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.moneymod.api.event.StepEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import wtf.cattyn.moneymod.mixin.ducks.AccessorEntity;

@Mixin({ Entity.class })
public class MixinEntity implements AccessorEntity
{
    @Shadow
    public boolean isInWeb;
    
    @Override
    public boolean isInWeb() {
        return this.isInWeb;
    }
    
    @Override
    public void setInWeb(final boolean state) {
        this.isInWeb = state;
    }
    
    @Inject(method = { "move" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEntityBoundingBox()Lnet/minecraft/util/math/AxisAlignedBB;", ordinal = 12, shift = At.Shift.BEFORE) })
    public void onPreStep(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.equals((Object)Entity.class.cast(this))) {
            final StepEvent event = new StepEvent(0);
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
    
    @Inject(method = { "move" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setEntityBoundingBox(Lnet/minecraft/util/math/AxisAlignedBB;)V", ordinal = 7, shift = At.Shift.AFTER) })
    public void onPostStep(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.equals((Object)Entity.class.cast(this))) {
            final StepEvent event = new StepEvent(1);
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
}
