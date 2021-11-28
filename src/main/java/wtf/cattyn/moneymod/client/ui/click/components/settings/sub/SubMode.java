//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click.components.settings.sub;

import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import wtf.cattyn.moneymod.client.ui.click.components.settings.ModeButton;
import wtf.cattyn.moneymod.client.ui.click.Component;

public class SubMode extends Component
{
    final ModeButton modeButton;
    final String mode;
    int offset;
    
    public SubMode(final ModeButton modeButton, final String mode, final int offset) {
        this.mode = mode;
        this.offset = offset;
        this.modeButton = modeButton;
    }
    
    @Override
    public void render(final int mouseX, final int mouseY) {
        Gui.drawRect(this.modeButton.getButton().panel.getX(), this.modeButton.getButton().panel.getY() + this.modeButton.getOffset() + this.offset, this.modeButton.getButton().panel.getX() + this.modeButton.getButton().panel.getWidth(), this.modeButton.getButton().panel.getY() + this.modeButton.getOffset() + this.offset + 12, this.isHover(mouseX, mouseY) ? new Color(0, 0, 0, 160).getRGB() : new Color(0, 0, 0, 140).getRGB());
        RenderUtil.drawStringWithShadow(this.mode, this.modeButton.getButton().panel.getX() + this.modeButton.getButton().panel.getWidth() / 2 - RenderUtil.getStringWidth(this.mode) / 2, this.modeButton.getButton().panel.getY() + this.modeButton.getOffset() + this.offset + 2, this.modeButton.getSetting().getValue().equalsIgnoreCase(this.mode) ? ColorUtil.getGuiColor().getRGB() : -1);
    }
    
    @Override
    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (this.isHover(mouseX, mouseY) && this.modeButton.getButton().open && this.modeButton.isOpen() && button == 0) {
            this.modeButton.getSetting().setValue(this.mode);
        }
    }
    
    private boolean isHover(final double x, final double y) {
        return x > this.modeButton.getX() && x < this.modeButton.getX() + 110 && y > this.modeButton.getY() + this.offset && y < this.modeButton.getY() + 12 + this.offset;
    }
    
    public int getOffset() {
        return this.offset;
    }
}
