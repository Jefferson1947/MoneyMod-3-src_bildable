// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TotemPopEvent extends Event
{
    private final EntityPlayer entityPlayerSP;
    
    public TotemPopEvent(final EntityPlayer entityPlayerSP) {
        this.entityPlayerSP = entityPlayerSP;
    }
    
    public EntityPlayer getEntityPlayerSP() {
        return this.entityPlayerSP;
    }
}
