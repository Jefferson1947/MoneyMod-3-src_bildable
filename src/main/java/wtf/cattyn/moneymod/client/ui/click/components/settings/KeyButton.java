//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click.components.settings;

import org.lwjgl.input.Keyboard;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.ClickGui;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import wtf.cattyn.moneymod.client.ui.click.components.ModuleButton;
import wtf.cattyn.moneymod.client.ui.click.Component;

public class KeyButton extends Component
{
    private boolean binding;
    private final ModuleButton button;
    private boolean isHovered;
    private int offset;
    private int x;
    private int y;
    
    public KeyButton(final ModuleButton button, final int offset) {
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
            this.binding = !this.binding;
        }
    }
    
    @Override
    public void keyTyped(final int key) {
        if (this.binding) {
            if (key == 211) {
                this.button.module.setKey(0);
            }
            else {
                this.button.module.setKey(key);
            }
            this.binding = false;
        }
    }
    
    @Override
    public void render(final int mouseX, final int mouseY) {
        Gui.drawRect(this.button.panel.getX(), this.button.panel.getY() + this.offset, this.button.panel.getX() + this.button.panel.getWidth(), this.button.panel.getY() + this.offset + 12, this.isHovered ? new Color(0, 0, 0, 160).getRGB() : new Color(0, 0, 0, 140).getRGB());
        RenderUtil.drawStringWithShadow("Key", this.button.panel.getX() + 5, this.button.panel.getY() + this.offset + 2, -1);
        if (this.binding) {
            RenderUtil.drawStringWithShadow("...", this.button.panel.getX() + this.button.panel.getWidth() - 5 - RenderUtil.getStringWidth("..."), this.button.panel.getY() + this.offset + (((ClickGui)Main.getModuleManager().get(ClickGui.class)).bounding.getValue() ? (this.isHovered ? 1 : 2) : 2), -1);
        }
        else {
            String key = null;
            switch (this.button.module.getKey()) {
                case 345: {
                    key = "RCtrl";
                    break;
                }
                case 341: {
                    key = "Ctrl";
                    break;
                }
                case 346: {
                    key = "RAlt";
                    break;
                }
                case -1: {
                    key = "NONE";
                    break;
                }
                default: {
                    key = Keyboard.getKeyName(this.button.module.getKey());
                    break;
                }
            }
            RenderUtil.drawStringWithShadow(key, this.button.panel.getX() + this.button.panel.getWidth() - 5 - RenderUtil.getStringWidth(key), this.button.panel.getY() + this.offset + (((ClickGui)Main.getModuleManager().get(ClickGui.class)).bounding.getValue() ? (this.isHovered ? 1 : 2) : 2), -1);
        }
    }
    
    public boolean isHovered(final double x, final double y) {
        return x > this.x && x < this.x + 110 && y > this.y && y < this.y + 12;
    }
}
