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

public class ComboButton extends Button
{
    public final ModuleButton moduleButton;
    public final Setting<String> setting;
    int modeIndex;
    
    public ComboButton(final double x, final double y, final double width, final double height, final Window window, final ModuleButton moduleButton, final Setting<String> setting) {
        super(x, y, width, height, window);
        this.moduleButton = moduleButton;
        this.setting = setting;
        this.modeIndex = 0;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Renderer2D.drawRoundedRect((float)(this.x + this.width / 2.0 + 1.9), (float)this.y, (float)(this.x + this.width - 4.0), (float)(this.y + 9.0), 1, new Color(59, 64, 70));
        Renderer2D.drawRoundedOutline((float)(this.x + this.width / 2.0 + 1.9), (float)this.y, (float)(this.x + this.width - 4.0), (float)(this.y + 9.0), 1, 1.0f, new Color(92, 98, 110));
        if (RenderUtil.isCustomFontEnabled()) {
            Main.getFontRendererS().drawString(this.setting.getName().toLowerCase(), (float)(this.x + 2.0), (float)(this.y + 1.0), new Color(12698049).getRGB());
            Main.getFontRendererS().drawString(this.setting.getValue().toLowerCase(), (float)(this.x + this.width / 2.0 + 6.0), (float)(this.y + 2.0), new Color(12698049).getRGB());
        }
        else {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.8f, 0.8f, 0.0f);
            RenderUtil.drawString(this.setting.getName().toLowerCase(), (float)(this.x + 2.0) / 0.8f, (float)(this.y + 1.0) / 0.8f, new Color(12698049).getRGB());
            RenderUtil.drawString(this.setting.getValue().toLowerCase(), (float)(this.x + this.width / 2.0 + 6.0) / 0.8f, (float)(this.y + 2.0) / 0.8f, new Color(12698049).getRGB());
            GlStateManager.scale(1.0f, 1.0f, 0.0f);
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHover(mouseX, mouseY)) {
            final int maxIndex = this.setting.getModes().size() - 1;
            if (mouseButton == 0) {
                ++this.modeIndex;
                if (this.modeIndex > maxIndex) {
                    this.modeIndex = 0;
                }
                this.setting.setValue(this.setting.getModes().get(this.modeIndex));
            }
            if (mouseButton == 1) {
                --this.modeIndex;
                if (this.modeIndex < 0) {
                    this.modeIndex = maxIndex;
                }
                this.setting.setValue(this.setting.getModes().get(this.modeIndex));
            }
        }
    }
}
