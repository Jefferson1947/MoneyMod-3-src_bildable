// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketEvent extends Event
{
    private final Packet<?> packet;
    
    public PacketEvent(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public <T extends Packet<?>> T getPacket() {
        return (T)this.packet;
    }
    
    public static final class Receive extends PacketEvent
    {
        public Receive(final Packet<?> packet) {
            super(packet);
        }
    }
    
    public static final class Send extends PacketEvent
    {
        public Send(final Packet<?> packet) {
            super(packet);
        }
    }
}
