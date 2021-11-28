//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl;

import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.Iterator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import wtf.cattyn.moneymod.util.Globals;

public class BlockUtil implements Globals
{
    private static boolean unshift;
    
    public static List<BlockPos> getSphere(final float radius, final boolean ignoreAir) {
        final ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(BlockUtil.mc.player.getPositionVector());
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        final int radiuss = (int)radius;
        for (int x = posX - radiuss; x <= posX + radius; ++x) {
            for (int z = posZ - radiuss; z <= posZ + radius; ++z) {
                for (int y = posY - radiuss; y < posY + radius; ++y) {
                    if ((posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y) < radius * radius) {
                        final BlockPos position = new BlockPos(x, y, z);
                        if (!ignoreAir || BlockUtil.mc.world.getBlockState(position).getBlock() != Blocks.AIR) {
                            sphere.add(position);
                        }
                    }
                }
            }
        }
        return sphere;
    }
    
    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand, final boolean swing) {
        if (pos == null || hand == null) {
            return;
        }
        final EnumFacing facing = EnumFacing.UP;
        BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
        if (swing) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        }
        BlockUtil.mc.playerController.updateController();
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean check) {
        return canPlaceCrystal(blockPos, check, true);
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean check, final boolean entity) {
        if (BlockUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && BlockUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        final BlockPos boost = blockPos.add(0, 1, 0);
        return BlockUtil.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && BlockUtil.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock() == Blocks.AIR && (!entity || BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB((double)boost.getX(), (double)boost.getY(), (double)boost.getZ(), (double)(boost.getX() + 1), (double)(boost.getY() + (check ? 2 : 1)), (double)(boost.getZ() + 1)), e -> !(e instanceof EntityEnderCrystal)).size() == 0);
    }
    
    public static boolean placeBlock(final BlockPos pos) {
        final Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();
        final EnumFacing direction = calcSide(pos);
        if (direction == null) {
            return false;
        }
        final boolean activated = block.onBlockActivated((World)BlockUtil.mc.world, pos, BlockUtil.mc.world.getBlockState(pos), (EntityPlayer)BlockUtil.mc.player, EnumHand.MAIN_HAND, direction, 0.0f, 0.0f, 0.0f);
        if (activated) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos.offset(direction), direction.getOpposite(), EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        if (activated || BlockUtil.unshift) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            BlockUtil.unshift = false;
        }
        BlockUtil.mc.playerController.updateController();
        return true;
    }
    
    public static EnumFacing calcSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final IBlockState offsetState = BlockUtil.mc.world.getBlockState(pos.offset(side));
            final boolean activated = offsetState.getBlock().onBlockActivated((World)BlockUtil.mc.world, pos, offsetState, (EntityPlayer)BlockUtil.mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
            if (activated) {
                BlockUtil.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                BlockUtil.unshift = true;
            }
            if (offsetState.getBlock().canCollideCheck(offsetState, false) && !offsetState.getMaterial().isReplaceable()) {
                return side;
            }
        }
        return null;
    }
    
    public static List<BlockPos> getUnsafePositions(final Vec3d vector, final int level) {
return null;    }
    
    public static List<Vec3d> getLevels(final int y) {
        return Arrays.asList(new Vec3d(-1.0, (double)y, 0.0), new Vec3d(1.0, (double)y, 0.0), new Vec3d(0.0, (double)y, 1.0), new Vec3d(0.0, (double)y, -1.0));
    }
    
    public static int isPlaceable(final BlockPos bp) {
        if (BlockUtil.mc.world == null || bp == null) {
            return 1;
        }
        if (!BlockUtil.mc.world.getBlockState(bp).getMaterial().isReplaceable()) {
            return 1;
        }
        for (final Object e : BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(bp))) {
            if (!(e instanceof EntityXPOrb)) {
                if (e instanceof EntityItem) {
                    continue;
                }
                if (e instanceof EntityPlayer) {
                    return 1;
                }
                return -1;
            }
        }
        return 0;
    }
    
    public static BlockResistance getBlockResistance(final BlockPos block) {
        if (BlockUtil.mc.world.isAirBlock(block)) {
            return BlockResistance.Blank;
        }
        if (BlockUtil.mc.world.getBlockState(block).getBlock().getBlockHardness(BlockUtil.mc.world.getBlockState(block), (World)BlockUtil.mc.world, block) != -1.0f && !BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) && !BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) && !BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) && !BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)) {
            return BlockResistance.Breakable;
        }
        if (BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) || BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) || BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) || BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)) {
            return BlockResistance.Resistant;
        }
        if (BlockUtil.mc.world.getBlockState(block).getBlock().equals(Blocks.BEDROCK)) {
            return BlockResistance.Unbreakable;
        }
        return null;
    }
    
    static {
        BlockUtil.unshift = false;
    }
    
    enum BlockResistance
    {
        Unbreakable, 
        Blank, 
        Breakable, 
        Resistant;
    }
}
