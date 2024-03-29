//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl.render;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.RenderGlobal;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import java.awt.Color;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import wtf.cattyn.moneymod.util.Globals;

public class Renderer3D implements Globals
{
    private static ICamera camera;
    
    public static void prepare() {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
    }
    
    public static AxisAlignedBB fixBB(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX - Renderer3D.mc.getRenderManager().viewerPosX, bb.minY - Renderer3D.mc.getRenderManager().viewerPosY, bb.minZ - Renderer3D.mc.getRenderManager().viewerPosZ, bb.maxX - Renderer3D.mc.getRenderManager().viewerPosX, bb.maxY - Renderer3D.mc.getRenderManager().viewerPosY, bb.maxZ - Renderer3D.mc.getRenderManager().viewerPosZ);
    }
    
    public static void drawBlockOutline(final AxisAlignedBB bb, final Color color, final float linewidth) {
        final float red = color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.010800879f) ^ 0x7F4FF62C);
        final float green = color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.013595752f) ^ 0x7F21C0B8);
        final float blue = color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.014829914f) ^ 0x7F0DF92B);
        final float alpha = Float.intBitsToFloat(Float.floatToIntBits(5.635761f) ^ 0x7F345827);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawBlockOutline(final BlockPos pos, final Color color, final float linewidth, final boolean air, final double height) {
        final IBlockState iblockstate = Renderer3D.mc.world.getBlockState(pos);
        if (!air && iblockstate.getMaterial() == Material.AIR) {
            return;
        }
        if (Renderer3D.mc.world.getWorldBorder().contains(pos)) {
            final AxisAlignedBB blockAxis = new AxisAlignedBB(pos.getX() - Renderer3D.mc.getRenderManager().viewerPosX, pos.getY() - Renderer3D.mc.getRenderManager().viewerPosY, pos.getZ() - Renderer3D.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - Renderer3D.mc.getRenderManager().viewerPosX, pos.getY() + 1 - Renderer3D.mc.getRenderManager().viewerPosY + height, pos.getZ() + 1 - Renderer3D.mc.getRenderManager().viewerPosZ);
            drawBlockOutline(blockAxis.grow(Double.longBitsToDouble(Double.doubleToLongBits(3177.4888695024906) ^ 0x7FC8B0B7AD1A7A6BL)), color, linewidth);
        }
    }
    
    public static void drawBoxESP(final BlockPos pos, final Color color, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final int outlineAlpha, final float height) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - Renderer3D.mc.getRenderManager().viewerPosX, pos.getY() - Renderer3D.mc.getRenderManager().viewerPosY, pos.getZ() - Renderer3D.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - Renderer3D.mc.getRenderManager().viewerPosX, pos.getY() + height - Renderer3D.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - Renderer3D.mc.getRenderManager().viewerPosZ);
        Renderer3D.camera.setPosition(Objects.requireNonNull(Renderer3D.mc.getRenderViewEntity()).posX, Renderer3D.mc.getRenderViewEntity().posY, Renderer3D.mc.getRenderViewEntity().posZ);
        if (Renderer3D.camera.isBoundingBoxInFrustum(new AxisAlignedBB(pos))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            if (box) {
                RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, boxAlpha / 255.0f);
            }
            if (outline) {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, outlineAlpha / 255.0f);
            }
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawBoxESP(final AxisAlignedBB pos, final Color color, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final int outlineAlpha) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.minX - Renderer3D.mc.getRenderManager().viewerPosX, pos.minY - Renderer3D.mc.getRenderManager().viewerPosY, pos.minZ - Renderer3D.mc.getRenderManager().viewerPosZ, pos.maxX - Renderer3D.mc.getRenderManager().viewerPosX, pos.maxY - Renderer3D.mc.getRenderManager().viewerPosY, pos.maxZ - Renderer3D.mc.getRenderManager().viewerPosZ);
        Renderer3D.camera.setPosition(Objects.requireNonNull(Renderer3D.mc.getRenderViewEntity()).posX, Renderer3D.mc.getRenderViewEntity().posY, Renderer3D.mc.getRenderViewEntity().posZ);
        if (Renderer3D.camera.isBoundingBoxInFrustum(pos)) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            if (box) {
                RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, boxAlpha / 255.0f);
            }
            if (outline) {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, outlineAlpha / 255.0f);
            }
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawFilledBox(final AxisAlignedBB bb, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        final float alpha = (color >> 24 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.0121679185f) ^ 0x7F385BF3);
        final float red = (color >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.009070697f) ^ 0x7F6B9D43);
        final float green = (color >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.013924689f) ^ 0x7F1B2461);
        final float blue = (color & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.067761265f) ^ 0x7EF5C66B);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    static {
        Renderer3D.camera = (ICamera)new Frustum();
    }
}
