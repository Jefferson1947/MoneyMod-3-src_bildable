//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl.font;

import wtf.cattyn.moneymod.util.Globals;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.ClickGui;

public class RenderUtil
{
    public static boolean isCustomFontEnabled() {
        try {
            final ClickGui clickgui = (ClickGui)Main.getModuleManager().get(ClickGui.class);
            return clickgui.customfont.getValue();
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public static float drawStringWithShadow(final String text, final float x, final float y, final int color) {
        return drawStringWithShadow(text, (int)x, (int)y, color);
    }
    
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
    
    public static float drawString(final String text, final float x, final float y, final int color) {
        return drawString(text, (int)x, (int)y, color);
    }
    
    public static float drawStringWithShadow(final String text, final int x, final int y, final int color) {
        if (isCustomFontEnabled()) {
            return Main.getFontRenderer().drawStringWithShadow(text, x, y, color);
        }
        return (float)Globals.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }
    
    public static float drawString(final String text, final int x, final int y, final int color) {
        if (isCustomFontEnabled()) {
            return Main.getFontRenderer().drawString(text, (float)x, (float)y, color);
        }
        return (float)Globals.mc.fontRenderer.drawString(text, x, y, color);
    }
    
    public static int getStringWidth(final String str) {
        if (isCustomFontEnabled()) {
            return Main.getFontRenderer().getStringWidth(str);
        }
        return Globals.mc.fontRenderer.getStringWidth(str);
    }
    
    public static int getFontHeight() {
        if (isCustomFontEnabled()) {
            return Main.getFontRenderer().getHeight() + 2;
        }
        return Globals.mc.fontRenderer.FONT_HEIGHT;
    }
}
