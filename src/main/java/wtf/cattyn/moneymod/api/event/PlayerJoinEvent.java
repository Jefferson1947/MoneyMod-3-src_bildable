// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PlayerJoinEvent extends Event
{
    public String name;
    
    public PlayerJoinEvent(final String name) {
        this.name = name;
    }
    
    public String getJoinName() {
        return this.name;
    }
}
