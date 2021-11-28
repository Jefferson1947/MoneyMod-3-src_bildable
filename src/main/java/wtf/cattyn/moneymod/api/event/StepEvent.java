// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class StepEvent extends Event
{
    private int stage;
    
    public StepEvent(final int stage) {
        this.stage = stage;
    }
    
    public int getStage() {
        return this.stage;
    }
}
