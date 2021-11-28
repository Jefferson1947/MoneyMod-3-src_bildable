//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBow;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "BowSpam", cat = Module.Category.COMBAT)
public class BowSpam extends Module
{
    private final Setting<Number> bowSpamTick;
    
    public BowSpam() {
        this.bowSpamTick = this.register("Spam Tick", 5.0, 0.0, 20.0, 1.0);
    }
    
    @Override
    public void onTick() {
        if (BowSpam.mc.player.isHandActive() && BowSpam.mc.player.getItemInUseMaxCount() >= this.bowSpamTick.getValue().intValue() && BowSpam.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, BowSpam.mc.player.getHorizontalFacing()));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            BowSpam.mc.player.stopActiveHand();
        }
    }
}
