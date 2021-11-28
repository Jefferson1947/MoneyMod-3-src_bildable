//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click.components.settings;

import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.ClickGui;
import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.client.ui.click.Screen;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import java.math.RoundingMode;
import java.math.BigDecimal;
import wtf.cattyn.moneymod.client.ui.click.components.ModuleButton;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.ui.click.Component;

public class SliderButton extends Component
{
    private final Setting<Double> setting;
    private final ModuleButton button;
    private boolean isHovered;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private double renderWidth;
    
    public SliderButton(final Setting<Double> setting, final ModuleButton button, final int offset) {
        this.setting = setting;
        this.button = button;
        this.x = button.panel.getX() + button.panel.getWidth();
        this.y = button.panel.getY() + button.offset;
        this.offset = offset;
    }
    
    private static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0 && this.button.open) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseReleased(final double mouseX, final double mouseY, final int mouseButton) {
        this.dragging = false;
    }
    
    @Override
    public void updateComponent(final double mouseX, final double mouseY) {
        this.isHovered = this.isHovered(mouseX, mouseY);
        this.y = this.button.panel.getY() + this.offset;
        this.x = this.button.panel.getX();
        final double diff = Math.min(106.0, Math.max(0.0, mouseX - this.x));
        final double min = this.setting.getMin();
        final double max = this.setting.getMax();
        this.renderWidth = 104.0 * (this.setting.getValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.setting.setValue(String.valueOf(this.setting.getMin()));
            }
            else {
                final double newValue = round(diff / 104.0 * (max - min) + min, 1);
                final double precision = 1.0 / this.setting.getInc();
                this.setting.setValue(String.valueOf(Math.round(Math.max(min, Math.min(max, newValue)) * precision) / precision));
            }
        }
    }
    
    @Override
    public void render(final int mouseX, final int mouseY) {
        Gui.drawRect(this.button.panel.getX(), this.button.panel.getY() + this.offset, this.button.panel.getX() + this.button.panel.getWidth(), this.button.panel.getY() + this.offset + 12, this.isHovered ? new Color(0, 0, 0, 160).getRGB() : new Color(0, 0, 0, 140).getRGB());
        Gui.drawRect(this.button.panel.getX() + 3, this.button.panel.getY() + this.offset + 11, (int)(this.button.panel.getX() + 3 + this.renderWidth), this.button.panel.getY() + this.offset + 10, this.isHovered ? Screen.color.darker().getRGB() : Screen.color.getRGB());
        RenderUtil.drawStringWithShadow(this.setting.getName() + ": " + ChatFormatting.GRAY + this.setting.getValue(), (float)(this.button.panel.getX() + 5), this.button.panel.getY() + this.offset + (((ClickGui)Main.getModuleManager().get(ClickGui.class)).bounding.getValue() ? (this.isHovered ? 0.0f : 1.5f) : 1.5f), this.isHovered ? new Color(170, 170, 170).getRGB() : -1);
        if (((ClickGui)Main.getModuleManager().get(ClickGui.class)).glow.getValue()) {
            Renderer2D.drawGlow(this.button.panel.getX() + 2, this.button.panel.getY() + this.offset + 8, (int)(this.button.panel.getX() + 3 + this.renderWidth) + 2, this.button.panel.getY() + this.offset + 12, ColorUtil.injectAlpha(Screen.color, 90).getRGB());
        }
    }
    
    public boolean isHovered(final double x, final double y) {
        return x > this.x && x < this.x + 110 && y > this.y && y < this.y + 12;
    }
}
