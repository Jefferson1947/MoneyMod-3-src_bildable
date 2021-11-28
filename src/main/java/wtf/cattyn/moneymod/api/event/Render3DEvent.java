// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class Render3DEvent extends Event
{
    private float partialTicks;
    
    public Render3DEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
