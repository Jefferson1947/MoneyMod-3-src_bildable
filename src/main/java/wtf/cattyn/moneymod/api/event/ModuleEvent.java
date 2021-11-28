// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import wtf.cattyn.moneymod.client.module.Module;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ModuleEvent extends Event
{
    private final Module module;
    private final Type type;
    
    public ModuleEvent(final Module module, final Type type) {
        this.module = module;
        this.type = type;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        ENABLE, 
        DISABLE;
    }
}
