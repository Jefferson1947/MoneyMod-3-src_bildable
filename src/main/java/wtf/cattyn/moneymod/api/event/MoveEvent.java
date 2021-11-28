// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class MoveEvent extends Event
{
    public double motionX;
    public double motionY;
    public double motionZ;
    
    public MoveEvent(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
}
