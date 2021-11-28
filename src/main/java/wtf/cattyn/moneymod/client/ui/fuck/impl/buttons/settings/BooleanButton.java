//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.settings;

import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import java.awt.Color;
import wtf.cattyn.moneymod.client.ui.fuck.Window;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.ModuleButton;
import wtf.cattyn.moneymod.client.ui.fuck.impl.Button;

public class BooleanButton extends Button
{
    public final ModuleButton moduleButton;
    public final Setting<Boolean> setting;
    
    public BooleanButton(final double x, final double y, final double width, final double height, final Window window, final ModuleButton moduleButton, final Setting<Boolean> setting) {
        super(x, y, width, height, window);
        this.setting = setting;
        this.moduleButton = moduleButton;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Renderer2D.drawRect((float)(this.x + 2.5), (float)this.y, (float)(this.x + 7.5), (float)this.y + 5.0f, ((boolean)this.setting.getValue()) ? new Color(92, 98, 110).getRGB() : new Color(51, 54, 63).getRGB());
        Renderer2D.drawOutline((float)(this.x + 2.5), (float)this.y, (float)(this.x + 7.5), (float)this.y + 5.0f, 1.3f, new Color(92, 98, 110).getRGB());
        if (RenderUtil.isCustomFontEnabled()) {
            Main.getFontRendererS().drawString(this.setting.getName().toLowerCase(), (float)(this.x + 11.0), (float)this.y, new Color(12698049).getRGB());
        }
        else {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.8f, 0.8f, 0.0f);
            RenderUtil.drawString(this.setting.getName().toLowerCase(), (float)(this.x + 11.0) / 0.8f, (float)this.y / 0.8f, new Color(12698049).getRGB());
            GlStateManager.scale(1.0f, 1.0f, 0.0f);
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHover(mouseX, mouseY) && mouseButton == 0) {
            this.setting.setValue(String.valueOf(!this.setting.getValue()));
        }
    }
    
    @Override
    public double[] getSize() {
        return new double[] { this.width, this.height };
    }
}
