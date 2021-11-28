// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ TileEntity.class })
public interface AccessorTileEntity
{
    @Accessor("blockType")
    void setBlockType(final Block p0);
}
