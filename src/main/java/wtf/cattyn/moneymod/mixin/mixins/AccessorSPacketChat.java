// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.network.play.server.SPacketChat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ SPacketChat.class })
public interface AccessorSPacketChat
{
    @Accessor("chatComponent")
    void setChatComponent(final ITextComponent p0);
}
