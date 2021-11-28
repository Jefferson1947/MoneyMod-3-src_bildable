//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck.impl.buttons;

import wtf.cattyn.moneymod.client.ui.fuck.IComponent;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import java.util.Iterator;
import wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.settings.ComboButton;
import wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.settings.SliderButton;
import wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.settings.BooleanButton;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.settings.KeybindButton;
import wtf.cattyn.moneymod.client.ui.fuck.Window;
import wtf.cattyn.moneymod.client.module.Module;
import java.util.ArrayList;
import wtf.cattyn.moneymod.client.ui.fuck.impl.Button;

public class ModuleButton extends Button
{
    public ArrayList<Button> components;
    public Module module;
    public CategoryButton categoryButton;
    
    public ModuleButton(final double x, final double y, final double width, final double height, final Window window, final Module module, final CategoryButton categoryButton) {
        super(x, y, width, height, window);
        this.components = new ArrayList<Button>();
        this.module = module;
        this.categoryButton = categoryButton;
        int offset = 2;
        this.components.add(new KeybindButton(x + 3.0, y + this.getSize()[1] + offset, width - 3.0, 14.0, window, this));
        offset += 12;
        for (final Setting<?> s : Main.getSettingManager().get(module)) {
            switch (s.getType()) {
                case B: {
                    final BooleanButton button = new BooleanButton(x + 3.0, y + this.getSize()[1] + offset, width - 3.0, 12.0, window, this, (Setting<Boolean>)s);
                    this.components.add(button);
                    offset += (int)button.getSize()[1];
                    continue;
                }
                case N: {
                    final SliderButton sliderButton = new SliderButton(x + 3.0, y + this.getSize()[1] + offset, width - 3.0, 16.0, window, this, (Setting<Double>)s);
                    this.components.add(sliderButton);
                    offset += (int)sliderButton.getSize()[1];
                    continue;
                }
                case M: {
                    final ComboButton comboButton = new ComboButton(x + 3.0, y + this.getSize()[1] + offset, width - 3.0, 12.0, window, this, (Setting<String>)s);
                    this.components.add(comboButton);
                    offset += (int)comboButton.getSize()[1];
                    continue;
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (RenderUtil.isCustomFontEnabled()) {
            Main.getFontRendererS().drawString(this.module.getName().toLowerCase(), (float)this.x, (float)this.y, ColorUtil.getGuiColor().getRGB());
        }
        else {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.scale(0.86f, 0.86f, 0.0f);
            RenderUtil.drawString(this.module.getName().toLowerCase(), (float)this.x / 0.86f, (float)this.y / 0.86f, ColorUtil.getGuiColor().getRGB());
            GL11.glHint(3155, 4352);
            GlStateManager.scale(1.0f, 1.0f, 0.0f);
            GlStateManager.popMatrix();
        }
        Renderer2D.drawRect((float)(this.x + this.width - 7.0), (float)this.y, (float)(this.x + this.width - 1.0), (float)this.y + 6.0f, this.module.isToggled() ? new Color(92, 98, 110).getRGB() : new Color(51, 54, 63).getRGB());
        Renderer2D.drawOutline((float)(this.x + this.width - 7.0), (float)this.y, (float)(this.x + this.width - 1.0), (float)this.y + 6.0f, 1.3f, new Color(92, 98, 110).getRGB());
        int off = 0;
        for (final IComponent c2 : this.components) {
            c2.update(mouseX, mouseY);
            off += (int)c2.getSize()[1];
        }
        Renderer2D.drawRoundedRect((float)(int)this.x, (float)((int)this.y + RenderUtil.getFontHeight() + 3), (float)(int)(this.x + this.width), (float)((int)this.y + RenderUtil.getFontHeight() + 3 + off), 2, new Color(51, 54, 63));
        Renderer2D.drawHGradientRect((float)((int)this.x + 3), (float)((int)this.y + RenderUtil.getFontHeight() + 1), (float)((int)(this.x + this.width) - 3), (float)((int)this.y + RenderUtil.getFontHeight() + 4), ColorUtil.getGuiColor().darker().getRGB(), ColorUtil.getGuiColor().getRGB());
        Renderer2D.drawPolygonPart((int)this.x + 3, (int)this.y + 4 + RenderUtil.getFontHeight(), 3, 0, ColorUtil.getGuiColor().darker().getRGB(), ColorUtil.getGuiColor().darker().getRGB());
        Renderer2D.drawPolygonPart((int)(this.x + this.width) - 3, (int)this.y + RenderUtil.getFontHeight() + 4, 3, 3, ColorUtil.getGuiColor().getRGB(), ColorUtil.getGuiColor().getRGB());
        this.components.forEach(c -> c.drawScreen(mouseX, mouseY, partialTicks));
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHover(mouseX, mouseY) && mouseButton == 0) {
            this.module.toggle();
        }
        this.components.forEach(c -> c.mouseClicked(mouseX, mouseY, mouseButton));
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.components.forEach(c -> c.mouseReleased(mouseX, mouseY, state));
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.components.forEach(c -> c.keyTyped(typedChar, keyCode));
    }
}
