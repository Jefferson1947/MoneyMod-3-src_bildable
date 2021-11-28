// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockPosWithFacing
{
    public BlockPos blockPos;
    public EnumFacing enumFacing;
    
    public BlockPosWithFacing(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
    }
}
