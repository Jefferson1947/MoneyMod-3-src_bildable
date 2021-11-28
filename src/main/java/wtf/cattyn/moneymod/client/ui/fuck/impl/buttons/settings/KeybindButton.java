//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.settings;

import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import java.awt.Color;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import org.lwjgl.input.Keyboard;
import wtf.cattyn.moneymod.client.ui.fuck.Window;
import wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.ModuleButton;
import wtf.cattyn.moneymod.client.ui.fuck.impl.Button;

public class KeybindButton extends Button
{
    private final ModuleButton moduleButton;
    private boolean binding;
    
    public KeybindButton(final double x, final double y, final double width, final double height, final Window window, final ModuleButton moduleButton) {
        super(x, y, width, height, window);
        this.moduleButton = moduleButton;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        String key = (this.moduleButton.module.getKey() == -1) ? "none" : Keyboard.getKeyName(this.moduleButton.module.getKey()).toLowerCase();
        if (this.binding) {
            key = "...";
        }
        final double stringWidth = RenderUtil.isCustomFontEnabled() ? Main.getFontRendererS().getStringWidth(key) : (RenderUtil.getStringWidth(key) * 0.8);
        Renderer2D.drawRoundedOutline((float)(this.x + this.width - stringWidth - 8.0), (float)(int)this.y, (float)(this.x + this.width) - 4.0f, (float)this.y + 10.0f, 1, 1.3f, new Color(92, 98, 110));
        if (RenderUtil.isCustomFontEnabled()) {
            Main.getFontRendererS().drawString("key", (float)(this.x + 2.0), (float)(this.y + 1.0), new Color(12698049).getRGB());
            Main.getFontRendererS().drawString(key, (float)(this.x + (float)this.width - stringWidth - 6.0), (float)(int)(this.y + 2.0), new Color(12698049).getRGB());
        }
        else {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.8f, 0.8f, 0.0f);
            RenderUtil.drawString("key", (float)(this.x + 2.0) / 0.8f, (float)(this.y + 1.0) / 0.8f, new Color(12698049).getRGB());
            RenderUtil.drawString(key, (float)(this.x + (float)this.width - stringWidth - 6.0) / 0.8f, (int)(this.y + 2.0) / 0.8f, new Color(12698049).getRGB());
            GlStateManager.scale(1.0f, 1.0f, 0.0f);
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (this.binding) {
            System.out.println("a");
            this.moduleButton.module.setKey(keyCode);
            if (keyCode == 211) {
                this.moduleButton.module.setKey(-1);
            }
            this.binding = false;
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHover(mouseX, mouseY) && mouseButton == 0) {
            this.binding = !this.binding;
        }
    }
}
