//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "SoundRemover", desc = "Mutes various sounds", cat = Module.Category.MISC)
public class SoundRemover extends Module
{
    public final Setting<Boolean> chorusteleport;
    public final Setting<Boolean> totempop;
    public final Setting<Boolean> explode;
    
    public SoundRemover() {
        this.chorusteleport = this.register("ChorusTeleport", true);
        this.totempop = this.register("TotemPop", true);
        this.explode = this.register("Explode", true);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = event.getPacket();
            if (this.chorusteleport.getValue() && packet.getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT) {
                event.setCanceled(true);
            }
            if (this.totempop.getValue() && packet.getSound() == SoundEvents.ITEM_TOTEM_USE) {
                event.setCanceled(true);
            }
            if (this.explode.getValue() && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                event.setCanceled(true);
            }
        }
    }
}
