//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click;

import net.minecraft.client.gui.Gui;
import java.awt.Color;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import wtf.cattyn.moneymod.util.Globals;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.ClickGui;

public class Description
{
    private boolean draw;
    private int mouseX;
    private int mouseY;
    private String text;
    
    public void reset() {
        this.draw = false;
        this.mouseX = 0;
        this.mouseY = 0;
        this.text = "";
    }
    
    public void update(final String text, final int mouseX, final int mouseY) {
        this.draw = true;
        this.text = text;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
    
    public void draw() {
        if (this.draw) {
            final ClickGui clickgui = (ClickGui)Main.getModuleManager().get(ClickGui.class);
            if (clickgui.descriptions.getValue() && this.text.length() > 0) {
                final ScaledResolution sr = new ScaledResolution(Globals.mc);
                final int width = RenderUtil.getStringWidth(this.text);
                int height = RenderUtil.getFontHeight();
                if (RenderUtil.isCustomFontEnabled()) {
                    height += 2;
                }
                boolean left = false;
                if (this.mouseX + width >= sr.getScaledWidth_double()) {
                    left = true;
                }
                final int startx = left ? (this.mouseX - width - 2) : (this.mouseX + 2);
                Gui.drawRect(startx, this.mouseY - height - 1, startx + width + 3, this.mouseY + 1, new Color(0, 0, 0, 170).getRGB());
                RenderUtil.drawStringWithShadow(this.text, startx + 2, this.mouseY - height + 1, new Color(255, 255, 255, 255).getRGB());
            }
        }
    }
}
