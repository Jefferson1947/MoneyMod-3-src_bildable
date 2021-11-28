//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl.render;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wtf.cattyn.moneymod.util.Globals;
import java.awt.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.mixin.mixins.AccessorRenderManager;

public class PMRenderUtil
{
    public static final AccessorRenderManager rendermgr;
    
    public static void doRandomShitWithGL(final boolean state) {
        if (state) {
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.enableAlpha();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glHint(3154, 4354);
        }
        else {
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GlStateManager.depthMask(!state);
    }
    
    public static float[] getColor(final Object[] var0) {
        final int var = (int)var0[0];
        boolean var2 = true;
        int var3 = var >> 16;
        var2 = true;
        final float var4 = (var3 & 0xFF) / 255.0f;
        var2 = true;
        var3 = var >> 8;
        var2 = true;
        final float var5 = (var3 & 0xFF) / 255.0f;
        var2 = true;
        final float var6 = (var & 0xFF) / 255.0f;
        var2 = true;
        var3 = var >> 24;
        var2 = true;
        final float var7 = (var3 & 0xFF) / 255.0f;
        boolean var8 = true;
        final float[] var9 = new float[4];
        var8 = true;
        boolean var10 = true;
        var9[0] = var4;
        var10 = true;
        var9[1] = var5;
        var10 = true;
        var9[2] = var6;
        var10 = true;
        var9[3] = var7;
        return var9;
    }
    
    public static void thisIsMyCode1(final Object[] objectArray) {
        final AxisAlignedBB axisAlignedBB = (AxisAlignedBB)objectArray[0];
        final int n = (int)objectArray[1];
        final float[] fArray = getColor(new Object[] { n });
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder2;
        final BufferBuilder bufferBuilder = bufferBuilder2 = tessellator.getBuffer();
        final AxisAlignedBB axisAlignedBB2 = axisAlignedBB;
        final BufferBuilder bufferBuilder3 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB3 = axisAlignedBB;
        final BufferBuilder bufferBuilder4 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB4 = axisAlignedBB;
        final BufferBuilder bufferBuilder5 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB5 = axisAlignedBB;
        final BufferBuilder bufferBuilder6 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB6 = axisAlignedBB;
        final BufferBuilder bufferBuilder7 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB7 = axisAlignedBB;
        final BufferBuilder bufferBuilder8 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB8 = axisAlignedBB;
        final BufferBuilder bufferBuilder9 = bufferBuilder;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder9.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder9.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB8.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder8.pos(axisAlignedBB8.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder8.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB7.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder7.pos(axisAlignedBB7.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder7.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB6.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder6.pos(axisAlignedBB6.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder6.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB5.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder5.pos(axisAlignedBB5.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder5.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB4.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder4.pos(axisAlignedBB4.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder4.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB3.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder3.pos(axisAlignedBB3.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder3.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB2.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder2.pos(axisAlignedBB2.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder2.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        tessellator.draw();
    }
    
    public static void thisIsMyCode2(final Object[] objectArray) {
        final AxisAlignedBB axisAlignedBB = (AxisAlignedBB)objectArray[0];
        float f = (float)objectArray[1];
        final int n = (int)objectArray[2];
        final float[] fArray = getColor(new Object[] { n });
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        if (f < 1.0f) {
            f = 1.0f;
        }
        GL11.glLineWidth(f);
        final BufferBuilder bufferBuilder2 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB2 = axisAlignedBB;
        final BufferBuilder bufferBuilder3 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB3 = axisAlignedBB;
        final BufferBuilder bufferBuilder4 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB4 = axisAlignedBB;
        final BufferBuilder bufferBuilder5 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB5 = axisAlignedBB;
        final BufferBuilder bufferBuilder6 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB6 = axisAlignedBB;
        final BufferBuilder bufferBuilder7 = bufferBuilder;
        final AxisAlignedBB axisAlignedBB7 = axisAlignedBB;
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(axisAlignedBB7.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 0.0f).endVertex();
        bufferBuilder7.pos(axisAlignedBB7.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder7.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB6.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder6.pos(axisAlignedBB6.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder6.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB5.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder5.pos(axisAlignedBB5.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder5.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB4.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder4.pos(axisAlignedBB4.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder4.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], 0.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB3.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder3.pos(axisAlignedBB3.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], 0.0f).endVertex();
        bufferBuilder3.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder.pos(axisAlignedBB2.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 0.0f).endVertex();
        bufferBuilder2.pos(axisAlignedBB2.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 1.0f).endVertex();
        bufferBuilder2.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(fArray[0], fArray[1], fArray[2], 0.0f).endVertex();
        tessellator.draw();
    }
    
    public static void drawUsingMyCode(final boolean reverse, final BlockPos pos, final Color color, double timer, final boolean fill, final boolean outline) {
        if (!reverse) {
            timer = 1.0 - timer;
        }
        final AxisAlignedBB axisAlignedBB = Globals.mc.world.getBlockState(pos).getSelectedBoundingBox((World)Globals.mc.world, pos);
        double d2 = axisAlignedBB.minX;
        double d3 = axisAlignedBB.minY;
        double d4 = axisAlignedBB.minZ;
        final double d5 = axisAlignedBB.maxX - axisAlignedBB.minX;
        final double d6 = axisAlignedBB.maxY - axisAlignedBB.minY;
        final double d7 = axisAlignedBB.maxZ - axisAlignedBB.minZ;
        double d8 = timer * d5;
        double d9 = timer * d6;
        double d10 = timer * d7;
        d8 /= 2.0;
        d9 /= 2.0;
        d10 /= 2.0;
        final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB((d2 -= Globals.mc.getRenderManager().viewerPosX) + d8, (d3 -= Globals.mc.getRenderManager().viewerPosY) + d9, (d4 -= Globals.mc.getRenderManager().viewerPosZ) + d10, d2 + d5 - d8, d3 + d6 - d9, d4 + d7 - d10);
        doRandomShitWithGL(true);
        if (fill) {
            final Object[] sex = { axisAlignedBB2, color.getRGB() };
            thisIsMyCode1(sex);
        }
        if (outline) {
            final Object[] sex = { axisAlignedBB2, 2.0f, color.getRGB() };
            thisIsMyCode2(sex);
        }
        doRandomShitWithGL(false);
    }
    
    private static boolean rhY(final Object[] objectArray) {
        final ItemStack itemStack = (ItemStack)objectArray[0];
        final Block block = (Block)objectArray[1];
        final EntityPlayer entityPlayer = (EntityPlayer)objectArray[2];
        final World world = (World)objectArray[3];
        final BlockPos blockPos = (BlockPos)objectArray[4];
        IBlockState iBlockState = world.getBlockState(blockPos);
        if ((iBlockState = iBlockState.getBlock().getActualState(iBlockState, (IBlockAccess)world, blockPos)).getMaterial().isToolNotRequired()) {
            return true;
        }
        final String string = block.getHarvestTool(iBlockState);
        if (itemStack.isEmpty()) {}
        return entityPlayer.canHarvestBlock(iBlockState);
    }
    
    public static double rhN(final Object[] objectArray) {
        final ItemStack itemStack = (ItemStack)objectArray[0];
        final IBlockState iBlockState = (IBlockState)objectArray[1];
        final BlockPos blockPos = (BlockPos)objectArray[2];
        final IBlockState iBlockState2 = iBlockState;
        final double d = itemStack.getDestroySpeed(iBlockState2);
        final int n = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemStack);
        if (iBlockState2.getBlockHardness((World)Globals.mc.world, blockPos) > 0.0f) {
            final double d2 = d;
            int n3;
            if (Double.compare(d2, 1.0) > 0) {
                final int n2 = n;
                n3 = n2 * n2 + 1;
            }
            else {
                n3 = 0;
            }
            return Math.max(d2 + n3, 0.0);
        }
        return 1.0;
    }
    
    public static double getSize(final ItemStack itemStack, final IBlockState iBlockState, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos) {
        final float f = iBlockState.getBlockHardness(world, blockPos);
        if (Float.compare(f, 0.0f) < 0) {
            return 0.0;
        }
        final Object[] objectArray2 = { itemStack, iBlockState.getBlock(), entityPlayer, world, blockPos };
        if (!rhY(objectArray2)) {
            final Object[] objectArray3 = { itemStack, iBlockState, blockPos };
            return rhN(objectArray3) / f / 100.0;
        }
        final Object[] objectArray4 = { itemStack, iBlockState, blockPos };
        return rhN(objectArray4) / f / 30.0;
    }
    
    public static void rhm(final Object[] objectArray) {
        double d = (double)objectArray[0];
        double d2 = (double)objectArray[1];
        double d3 = (double)objectArray[2];
        double d4 = (double)objectArray[3];
        double d5 = (double)objectArray[4];
        double d6 = (double)objectArray[5];
        final boolean bl = (boolean)objectArray[6];
        final boolean bl2 = (boolean)objectArray[7];
        final float f = (float)objectArray[8];
        final Color color = (Color)objectArray[9];
        d -= PMRenderUtil.rendermgr.getRenderPosX();
        d2 -= PMRenderUtil.rendermgr.getRenderPosY();
        d3 -= PMRenderUtil.rendermgr.getRenderPosZ();
        d4 -= PMRenderUtil.rendermgr.getRenderPosX();
        d5 -= PMRenderUtil.rendermgr.getRenderPosY();
        d6 -= PMRenderUtil.rendermgr.getRenderPosZ();
        doRandomShitWithGL(true);
        if (bl) {
            final Object[] objectArray2 = { new AxisAlignedBB(d, d2, d3, d4, d5, d6), color.getRGB() };
            thisIsMyCode1(objectArray2);
        }
        if (bl2) {
            final Object[] objectArray3 = { new AxisAlignedBB(d, d2, d3, d4, d5, d6), f, color.getRGB() };
            thisIsMyCode2(objectArray3);
        }
        doRandomShitWithGL(false);
    }
    
    public static void rhK(final BlockPos blockPos, final boolean bl, final boolean bl2, final Color color) {
        final BlockPos blockPos2 = blockPos;
        final double d = blockPos2.getX();
        final double d2 = blockPos2.getY();
        final double d3 = blockPos2.getZ();
        final Object[] objectArray2 = { d, d2, d3, d + 1.0, d2 + 1.0, d3 + 1.0, bl, bl2, 2.0f, color };
        rhm(objectArray2);
    }
    
    static {
        rendermgr = (AccessorRenderManager)Globals.mc.getRenderManager();
    }
}
