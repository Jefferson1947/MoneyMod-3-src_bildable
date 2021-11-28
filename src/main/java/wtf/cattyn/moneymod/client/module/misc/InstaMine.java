//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import wtf.cattyn.moneymod.api.event.EventBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.util.impl.render.Renderer3D;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import wtf.cattyn.moneymod.mixin.ducks.IPlayerControllerMP;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Blocks;
import wtf.cattyn.moneymod.util.impl.MathUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import wtf.cattyn.moneymod.util.impl.Timer;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "InstaMine", desc = "", cat = Module.Category.MISC)
public class InstaMine extends Module
{
    public Timer instaTimer;
    private BlockPos iRenderBlock;
    private BlockPos iLastBlock;
    private BlockPos iRender;
    private EnumFacing iDirection;
    public final Setting<Color> daColor;
    private final Setting<Boolean> render;
    private final Setting<Number> resetRange;
    private final Setting<Boolean> ccMode;
    
    public InstaMine() {
        this.instaTimer = new Timer();
        this.daColor = this.register("Color", new Color(Color.RED.getRed(), Color.RED.getBlue(), Color.RED.getGreen(), 64), false);
        this.render = this.register("Render", false);
        this.resetRange = this.register("Reset Range", 5.0, 1.0, 6.0, 1.0);
        this.ccMode = this.register("CcMode", false);
    }
    
    public void onEnable() {
        this.iRender = null;
        this.iRenderBlock = null;
        this.iLastBlock = null;
        this.instaTimer.reset();
    }
    
    public void doShit1() {
        InstaMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.iRenderBlock, this.iDirection));
        this.instaTimer.reset();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (this.iRenderBlock != null) {
            if (InstaMine.mc.player.getDistanceSq(this.iRenderBlock) > MathUtil.square(this.resetRange.getValue().floatValue()) && InstaMine.mc.world.getBlockState(this.iRenderBlock).getBlock() == Blocks.AIR) {
                this.iRender = null;
                this.iRenderBlock = null;
                this.iLastBlock = null;
                this.instaTimer.reset();
                return;
            }
            if (InstaMine.mc.world.getBlockState(this.iRenderBlock).getBlock() == Blocks.AIR) {
                this.iRender = null;
            }
            else if (InstaMine.mc.world.getBlockState(this.iRenderBlock).getBlock() == Blocks.ANVIL || InstaMine.mc.world.getBlockState(this.iRenderBlock).getBlock() == Blocks.ENDER_CHEST) {
                this.iRender = this.iRenderBlock;
            }
            if (this.iRenderBlock == null || !this.instaTimer.passed(100L)) {
                return;
            }
            if (InstaMine.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() != Items.DIAMOND_PICKAXE) {
                return;
            }
            if (this.ccMode.getValue()) {
                if (InstaMine.mc.world.getBlockState(this.iRenderBlock).getBlock() != Blocks.ANVIL && InstaMine.mc.world.getBlockState(this.iRenderBlock).getBlock() != Blocks.ENDER_CHEST) {
                    return;
                }
                this.doShit1();
            }
            else {
                this.doShit1();
            }
        }
        try {
            ((IPlayerControllerMP)InstaMine.mc).setBlockHitDelay(0);
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.render.getValue() && this.iRender != null) {
            Renderer3D.drawBoxESP(this.iRender, this.daColor.getValue(), 1.0f, true, true, this.daColor.getValue().getAlpha(), 110, 1.0f);
        }
    }
    
    @SubscribeEvent
    public void OnDamageBlock(final EventBlock event) {
        if (this.nullCheck()) {
            return;
        }
        if (canInsta(event.getPos())) {
            if (this.ccMode.getValue()) {
                if (InstaMine.mc.world.getBlockState(event.getPos()).getBlock() != Blocks.ANVIL && InstaMine.mc.world.getBlockState(event.getPos()).getBlock() != Blocks.ENDER_CHEST) {
                    return;
                }
                if (this.iLastBlock != null && (event.getPos().getX() != this.iLastBlock.getX() || event.getPos().getY() != this.iLastBlock.getY() || event.getPos().getZ() != this.iLastBlock.getZ())) {
                    InstaMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                }
                InstaMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                InstaMine.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.iRenderBlock = event.getPos();
                this.iLastBlock = event.getPos();
                this.iDirection = event.getDirection();
                event.setCanceled(true);
            }
            else {
                if (this.iLastBlock != null && (event.getPos().getX() != this.iLastBlock.getX() || event.getPos().getY() != this.iLastBlock.getY() || event.getPos().getZ() != this.iLastBlock.getZ())) {
                    InstaMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                }
                InstaMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                InstaMine.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.iRenderBlock = event.getPos();
                this.iLastBlock = event.getPos();
                this.iDirection = event.getDirection();
                event.setCanceled(true);
            }
        }
    }
    
    public static boolean canInsta(final BlockPos pos) {
        final IBlockState blockState = InstaMine.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)InstaMine.mc.world, pos) != -1.0f;
    }
}
