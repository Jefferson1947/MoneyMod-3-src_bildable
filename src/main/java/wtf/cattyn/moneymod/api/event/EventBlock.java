// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class EventBlock extends Event
{
    private BlockPos BlockPos;
    private EnumFacing Direction;
    
    public EventBlock(final BlockPos posBlock, final EnumFacing directionFacing) {
        this.BlockPos = posBlock;
        this.setDirection(directionFacing);
    }
    
    public BlockPos getPos() {
        return this.BlockPos;
    }
    
    public EnumFacing getDirection() {
        return this.Direction;
    }
    
    public void setDirection(final EnumFacing direction) {
        this.Direction = direction;
    }
}
