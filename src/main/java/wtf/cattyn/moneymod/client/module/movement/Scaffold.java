//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import wtf.cattyn.moneymod.api.event.MotionUpdateEvent;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import wtf.cattyn.moneymod.api.event.MoveEvent;
import wtf.cattyn.moneymod.api.event.RenderPlayerRotationsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.util.impl.render.PMRenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.api.event.Render3DEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import wtf.cattyn.moneymod.util.impl.Timer;
import wtf.cattyn.moneymod.util.impl.BlockPosWithFacing;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Scaffold", desc = "Automatically towers with blocks", cat = Module.Category.MOVEMENT)
public class Scaffold extends Module
{
    public final Setting<Boolean> rotate;
    public final Setting<Boolean> autoswap;
    public final Setting<Boolean> tower;
    public final Setting<Boolean> safewalk;
    public final Setting<Boolean> echestholding;
    public final Setting<Boolean> render;
    public final Setting<Color> rendercolor;
    private BlockPosWithFacing currentblock;
    private Timer timer;
    
    public Scaffold() {
        this.rotate = this.register("Rotate", true);
        this.autoswap = this.register("AutoSwap", true);
        this.tower = this.register("Tower", true);
        this.safewalk = this.register("SafeWalk", true);
        this.echestholding = this.register("EchestHolding", false);
        this.render = this.register("Render", true);
        this.rendercolor = this.register("Color", new Color(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue(), 50), false);
        this.timer = new Timer();
    }
    
    private boolean isBlockValid(final Block block) {
        return block.getDefaultState().getMaterial().isSolid();
    }
    
    private BlockPosWithFacing checkNearBlocks(final BlockPos blockPos) {
        if (this.isBlockValid(Scaffold.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isBlockValid(Scaffold.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isBlockValid(Scaffold.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isBlockValid(Scaffold.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isBlockValid(Scaffold.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }
    
    private BlockPosWithFacing checkNearBlocksExtended(final BlockPos blockPos) {
        BlockPosWithFacing ret = null;
        ret = this.checkNearBlocks(blockPos);
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(-1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, 1));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, -1));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(-2, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(2, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, 2));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, -2));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, -1, 0));
        final BlockPos blockPos2 = blockPos.add(0, -1, 0);
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(-1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(0, 0, 1));
        if (ret != null) {
            return ret;
        }
        return this.checkNearBlocks(blockPos2.add(0, 0, -1));
    }
    
    private int findBlockToPlace() {
        if (Scaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && this.isBlockValid(((ItemBlock)Scaffold.mc.player.getHeldItemMainhand().getItem()).getBlock())) {
            return Scaffold.mc.player.inventory.currentItem;
        }
        for (int n = 0, n2 = 0; n2 < 9; n2 = ++n) {
            if (Scaffold.mc.player.inventory.getStackInSlot(n).getCount() != 0 && Scaffold.mc.player.inventory.getStackInSlot(n).getItem() instanceof ItemBlock && (!this.echestholding.getValue() || (this.echestholding.getValue() && !Scaffold.mc.player.inventory.getStackInSlot(n).getItem().equals(Item.getItemFromBlock(Blocks.ENDER_CHEST)))) && this.isBlockValid(((ItemBlock)Scaffold.mc.player.inventory.getStackInSlot(n).getItem()).getBlock())) {
                return n;
            }
        }
        return -1;
    }
    
    private boolean someblockcheck(final int itemnum) {
        final Item item = Scaffold.mc.player.inventory.getStackInSlot(itemnum).getItem();
        if (item instanceof ItemBlock) {
            final Vec3d vec3d = Scaffold.mc.player.getPositionVector();
            final Block block = ((ItemBlock)item).getBlock();
            return Scaffold.mc.world.rayTraceBlocks(vec3d, vec3d.add(0.0, -block.getDefaultState().getSelectedBoundingBox((World)Scaffold.mc.world, BlockPos.ORIGIN).maxY, 0.0), false, true, false) == null;
        }
        return false;
    }
    
    private int countValidBlocks() {
        int n = 36;
        int n2 = 0;
        while (n < 45) {
            if (Scaffold.mc.player.inventoryContainer.getSlot(n).getHasStack()) {
                final ItemStack itemStack = Scaffold.mc.player.inventoryContainer.getSlot(n).getStack();
                if (itemStack.getItem() instanceof ItemBlock && this.isBlockValid(((ItemBlock)itemStack.getItem()).getBlock())) {
                    n2 += itemStack.getCount();
                }
            }
            ++n;
        }
        return n2;
    }
    
    private Vec3d getEyePosition() {
        return new Vec3d(Scaffold.mc.player.posX, Scaffold.mc.player.posY + Scaffold.mc.player.getEyeHeight(), Scaffold.mc.player.posZ);
    }
    
    private float[] getRotations(final BlockPos blockPos, final EnumFacing enumFacing) {
        Vec3d vec3d = new Vec3d(blockPos.getX() + 0.5, Scaffold.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)Scaffold.mc.world, blockPos).maxY - 0.01, blockPos.getZ() + 0.5);
        vec3d = vec3d.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
        final Vec3d vec3d2 = this.getEyePosition();
        final double d = vec3d.x - vec3d2.x;
        final double d2 = vec3d.y - vec3d2.y;
        final double d3 = vec3d.z - vec3d2.z;
        final double d4 = d;
        final double d5 = d3;
        final double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        final float f = (float)(Math.toDegrees(Math.atan2(d3, d)) - 90.0);
        final float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d6)));
        final float[] ret = { Scaffold.mc.player.rotationYaw + MathHelper.wrapDegrees(f - Scaffold.mc.player.rotationYaw), Scaffold.mc.player.rotationPitch + MathHelper.wrapDegrees(f2 - Scaffold.mc.player.rotationPitch) };
        return ret;
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (this.render.getValue() && this.currentblock != null) {
            GlStateManager.pushMatrix();
            PMRenderUtil.rhK(this.currentblock.blockPos, true, true, this.rendercolor.getValue());
            GlStateManager.popMatrix();
        }
    }
    
    @SubscribeEvent
    public void onRenderRotations(final RenderPlayerRotationsEvent event) {
        if (this.rotate.getValue() && this.currentblock != null && this.countValidBlocks() > 0) {
            final float[] rotations = this.getRotations(this.currentblock.blockPos, this.currentblock.enumFacing);
            event.setYaw(rotations[0]);
            event.setPitch(rotations[1]);
        }
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.safewalk.getValue()) {
            ChatUtil.sendMessage("safewalk not implemented YET");
        }
    }
    
    @SubscribeEvent
    public void onMotionUpdate(final MotionUpdateEvent event) {
        if (this.countValidBlocks() > 0) {
            if (Double.compare(Scaffold.mc.player.posY, 257.0) <= 0) {
                if (this.countValidBlocks() > 0) {
                    if (!this.autoswap.getValue()) {
                        if (!(Scaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
                            return;
                        }
                    }
                    if (event.stage != 0) {
                        if (this.currentblock != null) {
                            final int n = Scaffold.mc.player.inventory.currentItem;
                            Label_0504: {
                                if (Scaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
                                    if (this.isBlockValid(((ItemBlock)Scaffold.mc.player.getHeldItemMainhand().getItem()).getBlock())) {
                                        break Label_0504;
                                    }
                                }
                                if (this.autoswap.getValue()) {
                                    final int n2 = this.findBlockToPlace();
                                    if (n2 != -1) {
                                        Scaffold.mc.player.inventory.currentItem = n2;
                                        Scaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Scaffold.mc.player.inventory.currentItem));
                                    }
                                }
                            }
                            Scaffold scaffold4 = null;
                            Label_0648: {
                                Label_0646: {
                                    if (Scaffold.mc.player.movementInput.jump) {
                                        if (Scaffold.mc.player.moveForward == 0.0f) {
                                            if (Scaffold.mc.player.moveStrafing == 0.0f) {
                                                if (this.tower.getValue()) {
                                                    Scaffold.mc.player.setVelocity(0.0, 0.42, 0.0);
                                                    final Object[] objectArray = { null };
                                                    if (!this.timer.passed(1500L)) {
                                                        break Label_0646;
                                                    }
                                                    Scaffold.mc.player.motionY = -0.28;
                                                    final Scaffold scaffold3 = scaffold4 = this;
                                                    this.timer.reset();
                                                    break Label_0648;
                                                }
                                            }
                                        }
                                    }
                                    this.timer.reset();
                                }
                                scaffold4 = this;
                            }
                            final BlockPos blockPos2;
                            final BlockPos blockPos = blockPos2 = scaffold4.currentblock.blockPos;
                            final boolean bl = Scaffold.mc.world.getBlockState(blockPos).getBlock().onBlockActivated((World)Scaffold.mc.world, blockPos2, Scaffold.mc.world.getBlockState(blockPos2), (EntityPlayer)Scaffold.mc.player, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
                            if (bl) {
                                Scaffold.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Scaffold.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                            }
                            Scaffold.mc.playerController.processRightClickBlock(Scaffold.mc.player, Scaffold.mc.world, blockPos, this.currentblock.enumFacing, new Vec3d(blockPos.getX() + Math.random(), Scaffold.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)Scaffold.mc.world, blockPos).maxY - 0.01, blockPos.getZ() + Math.random()), EnumHand.MAIN_HAND);
                            Scaffold.mc.player.swingArm(EnumHand.MAIN_HAND);
                            if (bl) {
                                Scaffold.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Scaffold.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                            }
                            Scaffold.mc.player.inventory.currentItem = n;
                            Scaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Scaffold.mc.player.inventory.currentItem));
                        }
                    }
                    else {
                        this.currentblock = null;
                        if (!Scaffold.mc.player.isSneaking()) {
                            final int n3 = this.findBlockToPlace();
                            if (n3 != -1) {
                                final Item item = Scaffold.mc.player.inventory.getStackInSlot(n3).getItem();
                                if (item instanceof ItemBlock) {
                                    final Block block = ((ItemBlock)item).getBlock();
                                    final boolean bl2 = block.getDefaultState().isFullBlock();
                                    final double d = bl2 ? 1.0 : 0.01;
                                    final BlockPos blockPos3 = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - d, Scaffold.mc.player.posZ);
                                    if (Scaffold.mc.world.getBlockState(blockPos3).getMaterial().isReplaceable()) {
                                        if (!bl2) {
                                            if (!this.someblockcheck(n3)) {
                                                return;
                                            }
                                        }
                                        final Scaffold scaffold5 = this;
                                        scaffold5.currentblock = this.checkNearBlocksExtended(blockPos3);
                                        if (scaffold5.currentblock != null && this.rotate.getValue()) {
                                            final float[] rotations = this.getRotations(this.currentblock.blockPos, this.currentblock.enumFacing);
                                            event.rotationYaw = rotations[0];
                                            event.rotationPitch = rotations[1];
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return;
            }
        }
        this.currentblock = null;
    }
}
