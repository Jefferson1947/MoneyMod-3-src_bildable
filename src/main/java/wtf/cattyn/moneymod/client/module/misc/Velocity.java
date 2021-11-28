//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Velocity", desc = "Removes player's velocity", cat = Module.Category.MISC)
public class Velocity extends Module
{
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if ((event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity) event.getPacket()).getEntityID() == Velocity.mc.player.getEntityId()) || event.getPacket() instanceof SPacketExplosion || event.getPacket() instanceof EntityFishHook) {
            event.setCanceled(true);
        }
    }
}
