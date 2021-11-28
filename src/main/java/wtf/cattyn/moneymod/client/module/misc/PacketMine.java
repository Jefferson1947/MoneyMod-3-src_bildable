//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumHand;
import wtf.cattyn.moneymod.api.event.EventBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.util.impl.render.PMRenderUtil;
import wtf.cattyn.moneymod.api.event.Render3DEvent;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import wtf.cattyn.moneymod.util.impl.ToolUtil;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import wtf.cattyn.moneymod.util.impl.MathUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.util.impl.Timer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "PacketMine", desc = "", cat = Module.Category.MISC)
public class PacketMine extends Module
{
    public BlockPos renderPos;
    public BlockPos breakPos;
    public EnumFacing breakFace;
    public Timer timer;
    public Timer instaTimer;
    public boolean readyToMine;
    public int oldSlot;
    private float breaktimer;
    long start;
    boolean swapBack;
    public final Setting<Color> daColor;
    public final Setting<Color> rdyColor;
    private final Setting<Boolean> render;
    private final Setting<Boolean> fill;
    private final Setting<Boolean> outline;
    private final Setting<Boolean> reverse;
    private final Setting<Boolean> autoSwitch;
    private final Setting<Boolean> silent;
    private final Setting<Number> resetRange;
    
    public PacketMine() {
        this.renderPos = null;
        this.breakPos = null;
        this.breakFace = null;
        this.timer = new Timer();
        this.instaTimer = new Timer();
        this.readyToMine = false;
        this.oldSlot = -1;
        this.breaktimer = 0.0f;
        this.start = -1L;
        this.daColor = this.register("Color", new Color(Color.RED.getRed(), Color.RED.getBlue(), Color.RED.getGreen(), 64), false);
        this.rdyColor = this.register("ReadyColor", new Color(Color.GREEN.getRed(), Color.GREEN.getBlue(), Color.GREEN.getGreen(), 64), false);
        this.render = this.register("Render", false);
        this.fill = this.register("RenderFill", true);
        this.outline = this.register("RenderOutline", true);
        this.reverse = this.register("RenderReverse", true);
        this.autoSwitch = this.register("Auto Switch", false);
        this.silent = this.register("Silent", true);
        this.resetRange = this.register("Reset Range", 5.0, 1.0, 6.0, 1.0);
    }
    
    public void onEnable() {
        this.breakPos = null;
        this.instaTimer.reset();
        this.timer.reset();
        this.breaktimer = 0.0f;
        this.swapBack = false;
    }
    
    @Override
    public void onTick() {
        if (!this.nullCheck()) {
            if (this.swapBack) {
                if (this.oldSlot != -1) {
                    PacketMine.mc.player.inventory.currentItem = this.oldSlot;
                    PacketMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
                }
                this.swapBack = false;
            }
            if (this.breakPos != null) {
                this.oldSlot = PacketMine.mc.player.inventory.currentItem;
                if (PacketMine.mc.player.getDistanceSq(this.breakPos) > MathUtil.square(this.resetRange.getValue().floatValue()) || PacketMine.mc.world.getBlockState(this.breakPos).getBlock() == Blocks.AIR) {
                    this.breakPos = null;
                    this.breakFace = null;
                    this.readyToMine = false;
                    this.breaktimer = 0.0f;
                    return;
                }
                if (this.render.getValue()) {
                    this.renderPos = this.breakPos;
                }
                final float breakTime = PacketMine.mc.world.getBlockState(this.breakPos).getBlockHardness((World)PacketMine.mc.world, this.breakPos) * 40.0f;
                if (this.timer.passed((long)breakTime)) {
                    this.readyToMine = true;
                }
                if (this.autoSwitch.getValue()) {
                    if (this.timer.passed((long)breakTime) && ToolUtil.bestSlot(this.breakPos) != -1) {
                        PacketMine.mc.player.inventory.currentItem = ToolUtil.bestSlot(this.breakPos);
                        PacketMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(ToolUtil.bestSlot(this.breakPos)));
                    }
                    PacketMine.mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.breakFace));
                    if (this.oldSlot != -1) {
                        PacketMine.mc.player.inventory.currentItem = this.oldSlot;
                        PacketMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.breakPos == null) {
            return;
        }
        if (this.render.getValue()) {
            this.breaktimer = (System.currentTimeMillis() - this.start) / (float)ToolUtil.time(this.breakPos, PacketMine.mc.player.inventory.getStackInSlot(ToolUtil.bestSlot(this.breakPos)));
            Color color;
            if (this.timer.passed((long)(PacketMine.mc.world.getBlockState(this.breakPos).getBlockHardness((World)PacketMine.mc.world, this.breakPos) * 40.0f))) {
                color = this.rdyColor.getValue();
            }
            else {
                color = this.daColor.getValue();
            }
            if (this.breaktimer > 1.0f) {
                this.breaktimer = 1.0f;
            }
            PMRenderUtil.drawUsingMyCode(this.reverse.getValue(), this.breakPos, color, this.breaktimer, this.fill.getValue(), this.outline.getValue());
        }
    }
    
    @SubscribeEvent
    public void OnDamageBlock(final EventBlock event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.breakPos != null && event.getPos().toLong() == this.breakPos.toLong() && this.timer.passed((long)(PacketMine.mc.world.getBlockState(this.breakPos).getBlockHardness((World)PacketMine.mc.world, this.breakPos) * 40.0f)) && ToolUtil.bestSlot(event.getPos()) != -1) {
            if (!this.silent.getValue()) {
                PacketMine.mc.player.inventory.currentItem = ToolUtil.bestSlot(this.breakPos);
            }
            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(ToolUtil.bestSlot(this.breakPos)));
            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.breakFace));
            event.setCanceled(this.swapBack = true);
            return;
        }
        if (canBlockBeBroken(event.getPos())) {
            if (this.breakPos != null) {
                PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.breakFace));
            }
            this.start = System.currentTimeMillis();
            this.timer.reset();
            this.instaTimer.reset();
            this.breakPos = event.getPos();
            this.breakFace = event.getDirection();
            this.readyToMine = false;
            PacketMine.mc.player.swingArm(EnumHand.MAIN_HAND);
            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, this.breakFace));
            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.breakFace));
            event.setCanceled(true);
        }
    }
    
    public static boolean canBlockBeBroken(final BlockPos pos) {
        final IBlockState blockState = PacketMine.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)PacketMine.mc.world, pos) != -1.0f;
    }
    
    public void onDisable() {
        this.breakPos = null;
    }
}
