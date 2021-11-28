//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemBow;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "SelfHit", desc = "2012 nocheatplus bypass", cat = Module.Category.COMBAT)
public class SelfHit extends Module
{
    private final Setting<Number> count;
    
    public SelfHit() {
        this.count = this.register("TickCount", 4.0, 1.0, 20.0, 1.0);
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (SelfHit.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow && SelfHit.mc.player.isHandActive()) {
            final float oldP = SelfHit.mc.player.rotationPitch;
            if (SelfHit.mc.player.getItemInUseMaxCount() >= this.count.getValue().intValue()) {
                SelfHit.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(SelfHit.mc.player.rotationYaw, -90.0f, SelfHit.mc.player.onGround));
                SelfHit.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, SelfHit.mc.player.getHorizontalFacing()));
                SelfHit.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                SelfHit.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(SelfHit.mc.player.rotationYaw, oldP, SelfHit.mc.player.onGround));
                SelfHit.mc.player.stopActiveHand();
            }
        }
    }
}
