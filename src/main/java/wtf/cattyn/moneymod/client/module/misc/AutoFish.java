//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.item.ItemFishingRod;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AutoFish", desc = "", cat = Module.Category.MISC)
public class AutoFish extends Module
{
    private int rodSlot;
    
    public AutoFish() {
        this.rodSlot = -1;
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            this.setToggled(false);
            return;
        }
        this.rodSlot = ItemUtil.findHotbarBlock(ItemFishingRod.class);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = event.getPacket();
            if (packet.getCategory() == SoundCategory.NEUTRAL && packet.getSound() == SoundEvents.ENTITY_BOBBER_SPLASH) {
                final int startSlot = AutoFish.mc.player.inventory.currentItem;
                AutoFish.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(this.rodSlot));
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                if (startSlot != -1) {
                    AutoFish.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(startSlot));
                }
            }
        }
    }
}
