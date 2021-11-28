//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.Packet;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import wtf.cattyn.moneymod.util.Globals;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager implements Globals
{
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    public void onPacketReceive(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo ci) {
        if (MixinNetworkManager.mc.player == null && MixinNetworkManager.mc.world == null) {
            return;
        }
        final PacketEvent.Receive event = new PacketEvent.Receive(packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    public void onPacketSend(final Packet<?> packet, final CallbackInfo ci) {
        if (MixinNetworkManager.mc.player == null && MixinNetworkManager.mc.world == null) {
            return;
        }
        final PacketEvent.Send event = new PacketEvent.Send(packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
