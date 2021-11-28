// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityPlayerSP.class })
public interface AccessorEntityPlayerSP
{
    @Accessor("prevOnGround")
    boolean wasPreviouslyOnGround();
}
