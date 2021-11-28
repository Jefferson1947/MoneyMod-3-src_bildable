//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.client.module.movement.FastFall;
import wtf.cattyn.moneymod.Main;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import wtf.cattyn.moneymod.util.impl.BlockUtil;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.block.BlockWeb;
import java.util.Arrays;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "SelfFill", desc = "konas moment", cat = Module.Category.COMBAT)
public class SelfFill extends Module
{
    private final Setting<Number> height;
    private final Setting<Boolean> onlyWeb;
    private final List<Double> offsets;
    private BlockPos startPos;
    int tick;
    boolean fill;
    
    public SelfFill() {
        this.height = this.register("Height", 3.0, -10.0, 10.0, 1.0);
        this.onlyWeb = this.register("Only Web", false);
        this.offsets = Arrays.asList(0.4199999, 0.7531999, 1.0013359, 1.1661092);
        this.tick = 0;
        this.fill = false;
    }
    
    public void doWebPlace() {
        this.startPos = new BlockPos(SelfFill.mc.player.getPositionVector());
        final int webSlot = ItemUtil.findHotbarBlock(BlockWeb.class);
        final int oldSlot = SelfFill.mc.player.inventory.currentItem;
        if (!this.check()) {
            return;
        }
        if (webSlot != -1) {
            ItemUtil.switchToHotbarSlot(webSlot, false);
            BlockUtil.placeBlock(this.startPos);
            ItemUtil.switchToHotbarSlot(oldSlot, false);
        }
        this.setToggled(false);
    }
    
    public void onEnable() {
        this.fill = true;
    }
    
    @Override
    public void onTick() {
        if (!this.onlyWeb.getValue()) {
            final int startSlot = SelfFill.mc.player.inventory.currentItem;
            this.startPos = new BlockPos(SelfFill.mc.player.getPositionVector());
            if (ItemUtil.findHotbarBlock(Blocks.ENDER_CHEST, Blocks.OBSIDIAN, (Block)Blocks.CHEST) == -1) {
                this.setToggled(false);
                this.tick = 0;
                return;
            }
            if (!this.check()) {
                return;
            }
            ++this.tick;
            if (this.fill) {
                Main.getModuleManager().get(FastFall.class).disable();
                ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(Blocks.ENDER_CHEST, Blocks.OBSIDIAN, (Block)Blocks.CHEST), false);
                this.offsets.forEach(offset -> SelfFill.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(SelfFill.mc.player.posX, SelfFill.mc.player.posY + offset, SelfFill.mc.player.posZ, true)));
                SelfFill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfFill.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                BlockUtil.placeBlock(this.startPos);
                SelfFill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfFill.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                SelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(SelfFill.mc.player.posX, SelfFill.mc.player.posY + this.height.getValue().intValue(), SelfFill.mc.player.posZ, false));
                ItemUtil.switchToHotbarSlot(startSlot, false);
                this.fill = false;
            }
            if (this.tick >= 8) {
                this.tick = 0;
                this.setToggled(false);
            }
        }
        else {
            this.doWebPlace();
        }
    }
    
    private boolean check() {
        return SelfFill.mc.player.onGround && SelfFill.mc.world.getBlockState(this.startPos).getBlock() == Blocks.AIR && SelfFill.mc.world.getBlockState(this.startPos.add(0, ((boolean)this.onlyWeb.getValue()) ? 0 : 3, 0)).getBlock() == Blocks.AIR;
    }
}
