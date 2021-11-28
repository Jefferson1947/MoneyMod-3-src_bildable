//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click.components.settings;

import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.ClickGui;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import java.util.Iterator;
import wtf.cattyn.moneymod.client.ui.click.components.ModuleButton;
import wtf.cattyn.moneymod.client.ui.click.components.settings.sub.SubMode;
import java.util.ArrayList;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.ui.click.Component;

public class ModeButton extends Component
{
    private final Setting<String> setting;
    private final ArrayList<SubMode> modes;
    private final ModuleButton button;
    private boolean isHovered;
    private boolean open;
    private int offset;
    private int x;
    private int y;
    private int modeIndex;
    
    public ModeButton(final Setting<String> setting, final ModuleButton button, final int offset) {
        this.setting = setting;
        this.button = button;
        this.x = button.panel.getX() + button.panel.getWidth();
        this.y = button.panel.getY() + button.offset;
        this.offset = offset;
        this.modeIndex = 0;
        this.open = false;
        this.modes = new ArrayList<SubMode>();
        int off = 12;
        for (final String m : setting.getModes()) {
            this.modes.add(new SubMode(this, m, off));
            off += 12;
        }
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
        this.modes.forEach(v -> v.mouseClicked(mouseX, mouseY, button));
        if (this.isHovered(mouseX, mouseY) && this.button.open) {
            final int maxIndex = this.setting.getModes().size() - 1;
            if (button == 0) {
                ++this.modeIndex;
                if (this.modeIndex > maxIndex) {
                    this.modeIndex = 0;
                }
                this.setting.setValue(this.setting.getModes().get(this.modeIndex));
            }
            if (button == 1) {
                this.open = !this.open;
                this.button.panel.update();
            }
        }
    }
    
    @Override
    public void updateComponent(final double mouseX, final double mouseY) {
        this.isHovered = this.isHovered(mouseX, mouseY);
        this.y = this.button.panel.getY() + this.offset;
        this.x = this.button.panel.getX();
    }
    
    @Override
    public void render(final int mouseX, final int mouseY) {
        Gui.drawRect(this.button.panel.getX(), this.button.panel.getY() + this.offset, this.button.panel.getX() + this.button.panel.getWidth(), this.button.panel.getY() + this.offset + 12, this.isHovered ? new Color(0, 0, 0, 160).getRGB() : new Color(0, 0, 0, 140).getRGB());
        RenderUtil.drawStringWithShadow(this.setting.getName(), this.button.panel.getX() + 5, this.button.panel.getY() + this.offset + 2, -1);
        RenderUtil.drawStringWithShadow(this.setting.getValue(), this.button.panel.getX() + this.button.panel.getWidth() - 5 - RenderUtil.getStringWidth(this.setting.getValue()), this.button.panel.getY() + this.offset + (((ClickGui)Main.getModuleManager().get(ClickGui.class)).bounding.getValue() ? (this.isHovered ? 1 : 2) : 2), -1);
        if (this.open) {
            this.modes.forEach(m -> m.render(mouseX, mouseY));
        }
    }
    
    public boolean isHovered(final double x, final double y) {
        return x > this.x && x < this.x + 110 && y > this.y && y < this.y + 12;
    }
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 12 + this.setting.getModes().size() * 12;
        }
        return super.getHeight();
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public ModuleButton getButton() {
        return this.button;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public Setting<String> getSetting() {
        return this.setting;
    }
}
