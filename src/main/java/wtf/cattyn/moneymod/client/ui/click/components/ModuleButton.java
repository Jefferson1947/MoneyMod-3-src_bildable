//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click.components;

import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import wtf.cattyn.moneymod.client.ui.click.Screen;
import wtf.cattyn.moneymod.client.module.client.ClickGui;
import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import wtf.cattyn.moneymod.client.ui.click.components.settings.KeyButton;
import java.awt.Color;
import wtf.cattyn.moneymod.client.ui.click.components.settings.ColorButton;
import wtf.cattyn.moneymod.client.ui.click.components.settings.ModeButton;
import wtf.cattyn.moneymod.client.ui.click.components.settings.SliderButton;
import wtf.cattyn.moneymod.client.ui.click.components.settings.BooleanButton;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.Main;
import java.util.ArrayList;
import wtf.cattyn.moneymod.client.ui.click.Panel;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.client.ui.click.Component;

public class ModuleButton extends Component
{
    public Module module;
    public Panel panel;
    public int offset;
    private boolean isHovered;
    public final ArrayList<Component> components;
    public boolean open;
    private String openText;
    
    public ModuleButton(final Module module, final Panel panel, final int offset) {
        this.openText = "+";
        this.module = module;
        this.panel = panel;
        this.offset = offset;
        this.components = new ArrayList<Component>();
        this.open = false;
        int settingY = this.offset + 12;
        if (!Main.getSettingManager().get(module).isEmpty()) {
            for (final Setting s : Main.getSettingManager().get(module)) {
                switch (s.getType()) {
                    case B: {
                        this.components.add(new BooleanButton(s, this, settingY));
                        continue;
                    }
                    case N: {
                        this.components.add(new SliderButton(s, this, settingY));
                        continue;
                    }
                    case M: {
                        this.components.add(new ModeButton(s, this, settingY));
                        continue;
                    }
                    case C: {
                        this.components.add(new ColorButton(s, this, settingY));
                        continue;
                    }
                    default: {
                        settingY += 12;
                        continue;
                    }
                }
            }
        }
        this.components.add(new KeyButton(this, settingY));
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
        int settingY = this.offset + 12;
        for (final Component c : this.components) {
            c.setOffset(settingY);
            settingY += c.getHeight();
        }
    }
    
    @Override
    public int getHeight() {
        int height = 12;
        if (this.open) {
            for (final Component component : this.components) {
                height += component.getHeight();
            }
        }
        return height;
    }
    
    @Override
    public void updateComponent(final double mouseX, final double mouseY) {
        this.isHovered = this.isHovered(mouseX, mouseY);
        if (!this.components.isEmpty()) {
            this.components.forEach(c -> c.updateComponent(mouseX, mouseY));
        }
    }
    
    @Override
    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
        if (this.isHovered(mouseX, mouseY) && button == 0) {
            this.module.toggle();
        }
        if (this.isHovered(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.panel.update();
        }
        this.components.forEach(c -> c.mouseClicked(mouseX, mouseY, button));
    }
    
    @Override
    public void mouseReleased(final double mouseX, final double mouseY, final int mouseButton) {
        this.components.forEach(c -> c.mouseReleased(mouseX, mouseY, mouseButton));
    }
    
    @Override
    public void keyTyped(final int key) {
        this.components.forEach(c -> c.keyTyped(key));
    }
    
    @Override
    public void render(final int mouseX, final int mouseY) {
        Gui.drawRect(this.panel.getX(), this.panel.getY() + this.offset, this.panel.getX() + this.panel.getWidth(), this.panel.getY() + this.offset + 12, this.isHovered ? new Color(0, 0, 0, 160).getRGB() : new Color(0, 0, 0, 140).getRGB());
        RenderUtil.drawStringWithShadow(this.module.getName(), this.panel.getX() + 3, this.panel.getY() + this.offset + (((ClickGui)Main.getModuleManager().get(ClickGui.class)).bounding.getValue() ? (this.isHovered ? 1 : 2) : 2), this.module.isToggled() ? Screen.color.getRGB() : -1);
        if (this.module.isToggled() && ((ClickGui)Main.getModuleManager().get(ClickGui.class)).glow.getValue()) {
            Renderer2D.drawGlow(this.panel.getX() + 6, this.panel.getY() + this.offset - 1, this.panel.getX() + RenderUtil.getStringWidth(this.module.getName()), this.panel.getY() + this.offset + 4 + RenderUtil.getFontHeight(), ColorUtil.injectAlpha(Screen.color, 90).getRGB());
        }
        if (this.open) {
            this.components.forEach(component -> component.render(mouseX, mouseY));
            this.openText = "-";
        }
        else {
            this.openText = "+";
        }
        RenderUtil.drawStringWithShadow(this.openText, this.panel.getX() + this.panel.getWidth() - 10, this.panel.getY() + this.offset + 2, -1);
        if (this.isHovered) {
            Main.getScreen().getDescriptionManager().update(this.module.getDesc(), mouseX, mouseY);
        }
    }
    
    public boolean isHovered(final double x, final double y) {
        return x > this.panel.getX() && x < this.panel.getX() + this.panel.getWidth() && y > this.panel.getY() + this.offset && y < this.panel.getY() + 12 + this.offset;
    }
}
