// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class UpdateWalkingPlayerEvent extends Event
{
    private final int stage;
    
    public UpdateWalkingPlayerEvent(final int stage) {
        this.stage = stage;
    }
    
    public int getStage() {
        return this.stage;
    }
}
