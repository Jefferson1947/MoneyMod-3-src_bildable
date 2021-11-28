// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck.impl.buttons;

import org.lwjgl.input.Mouse;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import org.lwjgl.opengl.GL11;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import java.awt.Color;
import wtf.cattyn.moneymod.client.ui.fuck.IComponent;
import java.util.Iterator;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.ui.fuck.Window;
import wtf.cattyn.moneymod.client.module.Module;
import java.util.ArrayList;
import wtf.cattyn.moneymod.client.ui.fuck.impl.Button;

public class CategoryButton extends Button
{
    public final ArrayList<Button> modules;
    public final Module.Category category;
    public boolean open;
    
    public CategoryButton(final float x, final float y, final float width, final float height, final Window window, final Module.Category category) {
        super(x, y, width, height, window);
        this.modules = new ArrayList<Button>();
        this.category = category;
        this.open = false;
        double lOff = 0.0;
        double rOff = 0.0;
        for (int j = 0; j < Main.getModuleManager().get(category).size(); ++j) {
            final Module module = Main.getModuleManager().get(category).get(j);
            if (j % 2 == 0) {
                final ModuleButton m = new ModuleButton(8.0, 46.0 + lOff, 130.0, 15.0, window, module, this);
                this.modules.add(m);
                lOff += 16.0;
                for (final IComponent component : m.components) {
                    lOff += component.getSize()[1];
                }
            }
            else {
                final ModuleButton m = new ModuleButton(6.0 + window.getSize()[0] / 2.0 - 1.0, 46.0 + rOff, 130.0, 15.0, window, module, this);
                this.modules.add(m);
                rOff += 16.0;
                for (final IComponent component : m.components) {
                    rOff += component.getSize()[1];
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.open) {
            Renderer2D.drawRoundedRect((float)this.x - 1.0f, (float)this.y - 1.0f, (float)(this.x + this.width) + 1.0f, (float)(this.y + this.height) + 1.0f, 3, new Color(56, 60, 69));
            GL11.glPushMatrix();
            Main.getIrcScreen().glScissor((int)this.window.x, (int)this.window.y + 40, (int)this.window.getSize()[0], (int)(this.window.getSize()[1] - 43.0));
            GL11.glEnable(3089);
            this.modules.forEach(m -> {
                m.drawScreen(mouseX, mouseY, partialTicks);
                m.update(mouseX, mouseY);
                return;
            });
            GL11.glDisable(3089);
            Renderer2D.drawVGradientRect((float)this.window.x, (float)(this.window.y + this.window.getSize()[1]) - 10.0f, (float)(this.window.x + this.window.getSize()[0]), (float)(this.window.y + this.window.getSize()[1]), new Color(38, 45, 63, 0).getRGB(), new Color(38, 45, 63).getRGB());
            GL11.glPopMatrix();
        }
        if (RenderUtil.isCustomFontEnabled()) {
            Main.getFontRendererS().drawString(this.category.getName().toLowerCase(), (float)this.x + 1.2f, (float)this.y + 2.5f, -1);
        }
        else {
            RenderUtil.drawString(this.category.getName().toLowerCase(), (float)this.x + 1.0f, (float)this.y + 2.0f, -1);
        }
    }
    
    @Override
    public void update(final int mouseX, final int mouseY) {
        super.update(mouseX, mouseY);
        if (this.open) {
            this.doScroll();
        }
    }
    
    private void doScroll() {
        final int w = Mouse.getDWheel();
        if (w < 0) {
            for (final Button btn : this.modules) {
                for (final Button button : ((ModuleButton)btn).components) {
                    final Button setting = button;
                    button.scroll -= 16.0;
                }
                final Button button2 = btn;
                button2.scroll -= 16.0;
            }
        }
        else if (w > 0) {
            if (w < 0) {
                for (final Button btn : this.modules) {
                    for (final Button setting : ((ModuleButton)btn).components) {
                        setting.scroll = 0.0;
                    }
                    btn.scroll = 0.0;
                }
                return;
            }
            for (final Button btn : this.modules) {
                for (final Button button3 : ((ModuleButton)btn).components) {
                    final Button setting = button3;
                    button3.scroll += 16.0;
                }
                final Button button4 = btn;
                button4.scroll += 16.0;
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHover(mouseX, mouseY) && mouseButton == 0) {
            Main.getOtcScreen().mainWindow.categories.forEach(c -> c.open = false);
            this.open = true;
        }
        if (!this.open) {
            return;
        }
        this.modules.forEach(m -> m.mouseClicked(mouseX, mouseY, mouseButton));
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (!this.open) {
            return;
        }
        this.modules.forEach(m -> m.mouseReleased(mouseX, mouseY, state));
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.modules.forEach(m -> m.keyTyped(typedChar, keyCode));
    }
}
