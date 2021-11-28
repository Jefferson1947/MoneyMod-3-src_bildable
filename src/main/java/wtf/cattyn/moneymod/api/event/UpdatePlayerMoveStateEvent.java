// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.Event;

public class UpdatePlayerMoveStateEvent extends Event
{
    private MovementInput input;
    
    public UpdatePlayerMoveStateEvent(final MovementInput input) {
        this.input = input;
    }
    
    public MovementInput getMovementInput() {
        return this.input;
    }
}
