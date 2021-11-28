//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click.components.settings;

import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.client.ui.click.Screen;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import wtf.cattyn.moneymod.client.module.client.Global;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.ClickGui;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import wtf.cattyn.moneymod.client.ui.click.components.ModuleButton;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.ui.click.Component;

public class BooleanButton extends Component
{
    private final Setting<Boolean> setting;
    private final ModuleButton button;
    private boolean isHovered;
    private int offset;
    private int x;
    private int y;
    
    public BooleanButton(final Setting<Boolean> setting, final ModuleButton button, final int offset) {
        this.setting = setting;
        this.button = button;
        this.x = button.panel.getX() + button.panel.getWidth();
        this.y = button.panel.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public void updateComponent(final double mouseX, final double mouseY) {
        this.isHovered = this.isHovered(mouseX, mouseY);
        this.y = this.button.panel.getY() + this.offset;
        this.x = this.button.panel.getX();
    }
    
    @Override
    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0 && this.button.open) {
            this.setting.setValue(String.valueOf(!this.setting.getValue()));
        }
    }
    
    @Override
    public void render(final int mouseX, final int mouseY) {
        Gui.drawRect(this.button.panel.getX(), this.button.panel.getY() + this.offset, this.button.panel.getX() + this.button.panel.getWidth(), this.button.panel.getY() + this.offset + 12, this.isHovered ? new Color(0, 0, 0, 160).getRGB() : new Color(0, 0, 0, 140).getRGB());
        RenderUtil.drawStringWithShadow(this.setting.getName(), this.button.panel.getX() + 5, this.button.panel.getY() + this.offset + (((ClickGui)Main.getModuleManager().get(ClickGui.class)).bounding.getValue() ? (this.isHovered ? 1 : 2) : 2), ((boolean)this.setting.getValue()) ? ((Global)Main.getModuleManager().get(Global.class)).colorSetting.getValue().getRGB() : new Color(160, 160, 160).getRGB());
        if (this.setting.getValue() && ((ClickGui)Main.getModuleManager().get(ClickGui.class)).glow.getValue()) {
            Renderer2D.drawGlow(this.button.panel.getX() + 8, this.button.panel.getY() + this.offset - 1, this.button.panel.getX() + 2 + RenderUtil.getStringWidth(this.setting.getName()), this.button.panel.getY() + this.offset + 4 + RenderUtil.getFontHeight(), ColorUtil.injectAlpha(Screen.color, 90).getRGB());
        }
    }
    
    public boolean isHovered(final double x, final double y) {
        return x > this.x && x < this.x + 110 && y > this.y && y < this.y + 12;
    }
}
