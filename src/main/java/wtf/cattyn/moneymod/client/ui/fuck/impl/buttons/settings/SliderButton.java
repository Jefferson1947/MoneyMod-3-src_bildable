//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.settings;

import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import java.awt.Color;
import java.math.RoundingMode;
import java.math.BigDecimal;
import wtf.cattyn.moneymod.client.ui.fuck.Window;
import wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.ModuleButton;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.ui.fuck.impl.Button;

public class SliderButton extends Button
{
    private final Setting<Double> setting;
    public final ModuleButton moduleButton;
    private double renderWidth;
    boolean dragging;
    
    public SliderButton(final double x, final double y, final double width, final double height, final Window window, final ModuleButton moduleButton, final Setting<Double> setting) {
        super(x, y, width, height, window);
        this.setting = setting;
        this.moduleButton = moduleButton;
        this.dragging = false;
    }
    
    private double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Renderer2D.drawRoundedRect((float)this.x + 2.0f, (float)this.y + 8.0f, (float)(int)(this.x + this.width - 4.0), (float)this.y + 10.0f, 1, new Color(34, 34, 40));
        Renderer2D.drawRoundedRect((float)this.x + 2.0f, (float)this.y + 8.0f, (float)(int)(this.x + this.renderWidth), (float)this.y + 10.0f, 1, ColorUtil.getGuiColor());
        if (RenderUtil.isCustomFontEnabled()) {
            Main.getFontRendererS().drawString(this.setting.getName().toLowerCase(), (float)(this.x + 2.0), (float)this.y, new Color(12698049).getRGB());
            if (this.isHover(mouseX, mouseY) || this.dragging) {
                Main.getFontRendererS().drawCenteredString(this.setting.getValue().toString(), (float)(this.x + this.width / 2.0), (float)(this.y + 2.0), new Color(12698049).getRGB());
            }
        }
        else {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.8f, 0.8f, 0.0f);
            RenderUtil.drawString(this.setting.getName().toLowerCase(), (float)(this.x + 2.0) / 0.8f, (float)this.y / 0.8f, new Color(12698049).getRGB());
            if (this.isHover(mouseX, mouseY) || this.dragging) {
                RenderUtil.drawString(this.setting.getValue().toString(), (float)(this.x + this.width / 2.0 - RenderUtil.getStringWidth(this.setting.getValue().toString()) * 0.8 / 2.0) / 0.8f, (float)(this.y + 2.0) / 0.8f, new Color(12698049).getRGB());
            }
            GlStateManager.scale(1.0f, 1.0f, 0.0f);
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isHover(mouseX, mouseY) && button == 0) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = false;
    }
    
    @Override
    public void update(final int mouseX, final int mouseY) {
        super.update(mouseX, mouseY);
        if (this.renderWidth < 0.0) {
            this.renderWidth = 0.0;
        }
        final double diff = Math.min(this.width, Math.max(0.0, mouseX - this.x));
        final double min = this.setting.getMin();
        final double max = this.setting.getMax();
        this.renderWidth = (this.width - 8.0) * (this.setting.getValue() - min) / (max - min) + 3.0;
        if (this.dragging) {
            if (diff == 0.0) {
                this.setting.setValue(String.valueOf(this.setting.getMin()));
            }
            else {
                final double newValue = this.round(diff / this.width * (max - min) + min, 1);
                final double precision = 1.0 / this.setting.getInc();
                this.setting.setValue(String.valueOf(Math.round(Math.max(min, Math.min(max, newValue)) * precision) / precision));
            }
        }
    }
}
