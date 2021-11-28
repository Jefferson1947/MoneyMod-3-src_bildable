// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AntiPacket", desc = "Anti Packet?", cat = Module.Category.MISC)
public class AntiPacket extends Module
{
    public final Setting<Boolean> cPacketPlayer;
    public final Setting<Boolean> CPacketPlayerPos;
    public final Setting<Boolean> cPacketPlayerPosRot;
    public final Setting<Boolean> cPacketPlayerRot;
    public final Setting<Boolean> cPacketVehicleMove;
    public final Setting<Boolean> cPacketTryUseItemOnBlock;
    public final Setting<Boolean> cPacketPlayerTryUseItem;
    
    public AntiPacket() {
        this.cPacketPlayer = this.register("CPacketPlayer", true);
        this.CPacketPlayerPos = this.register("CPacketPlayerPos", true);
        this.cPacketPlayerPosRot = this.register("CPacketPlayerPosRot", true);
        this.cPacketPlayerRot = this.register("CPacketPlayerRot", true);
        this.cPacketVehicleMove = this.register("CPacketVehicleMove", true);
        this.cPacketTryUseItemOnBlock = this.register("CPacketUseItemBlock", false);
        this.cPacketPlayerTryUseItem = this.register("CPacketUseItem", false);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.cPacketPlayer.getValue() && event.getPacket() instanceof CPacketPlayer) {
            event.setCanceled(true);
        }
        if (this.CPacketPlayerPos.getValue() && event.getPacket() instanceof CPacketPlayer.Position) {
            event.setCanceled(true);
        }
        if (this.cPacketPlayerPosRot.getValue() && event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            event.setCanceled(true);
        }
        if (this.cPacketPlayerRot.getValue() && event.getPacket() instanceof CPacketPlayer.Rotation) {
            event.setCanceled(true);
        }
        if (this.cPacketVehicleMove.getValue() && event.getPacket() instanceof CPacketVehicleMove) {
            event.setCanceled(true);
        }
        if (this.cPacketTryUseItemOnBlock.getValue() && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            event.setCanceled(true);
        }
        if (this.cPacketPlayerTryUseItem.getValue() && event.getPacket() instanceof CPacketPlayerTryUseItem) {
            event.setCanceled(true);
        }
    }
}
