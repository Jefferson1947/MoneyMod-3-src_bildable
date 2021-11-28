// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderManager.class })
public interface AccessorRenderManager
{
    @Accessor
    double getRenderPosX();
    
    @Accessor
    double getRenderPosY();
    
    @Accessor
    double getRenderPosZ();
}
