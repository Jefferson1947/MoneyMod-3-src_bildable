// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck;

import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import java.awt.Color;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.client.ui.fuck.impl.buttons.CategoryButton;
import java.util.ArrayList;

public class Window implements IComponent
{
    public double x;
    public double y;
    public double dragX;
    public double dragY;
    boolean dragging;
    public final ArrayList<CategoryButton> categories;
    
    public Window(final double x, final double y) {
        this.categories = new ArrayList<CategoryButton>();
        this.x = x;
        this.y = y;
        this.dragging = false;
        int offset = 0;
        for (final Module.Category category : Module.Category.values()) {
            this.categories.add(new CategoryButton((float)(84 + offset), 15.0f, RenderUtil.isCustomFontEnabled() ? ((float)(Main.getFontRendererS().getStringWidth(category.getName().toLowerCase()) + 2)) : ((float)(RenderUtil.getStringWidth(category.getName().toLowerCase()) + 2)), (float)(RenderUtil.getFontHeight() + 2), this, category));
            offset += RenderUtil.getStringWidth(category.getName().toLowerCase()) + 8;
        }
        this.categories.get(0).open = true;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawHeader();
        this.drawMenu();
        this.categories.forEach(button -> {
            button.drawScreen(mouseX, mouseY, partialTicks);
            button.update(mouseX, mouseY);
        });
    }
    
    void drawHeader() {
        Renderer2D.drawRoundedRect((float)this.x, (float)this.y, (float)(this.x + this.getSize()[0]), (float)this.y + 35.0f, 2, new Color(45, 48, 55));
        Renderer2D.drawHGradientRect((float)this.x + 5.0f, (float)this.y, (float)(this.x + this.getSize()[0]) - 5.0f, (float)this.y + 5.0f, ColorUtil.getGuiColor().darker().getRGB(), ColorUtil.getGuiColor().getRGB());
        Renderer2D.drawPolygonPart((float)this.x + 5.0f, (float)this.y + 5.0f, 5, 0, ColorUtil.getGuiColor().darker().getRGB(), ColorUtil.getGuiColor().darker().getRGB());
        Renderer2D.drawPolygonPart((float)(this.x + this.getSize()[0]) - 5.0f, (float)this.y + 5.0f, 5, 3, ColorUtil.getGuiColor().getRGB(), ColorUtil.getGuiColor().getRGB());
        Renderer2D.drawLine((float)this.x, (float)this.y + 5.0f, (float)(this.x + this.getSize()[0]), (float)this.y + 5.0f, 1.0f, new Color(25, 38, 47).getRGB());
        RenderUtil.drawString(ChatFormatting.BOLD + "moneymod", (float)this.x + 14.0f, (float)this.y + 17.0f, -1);
        Renderer2D.drawRect((float)this.x + 77.0f, (float)this.y + 10.0f, (float)this.x + 78.0f, (float)this.y + 30.0f, new Color(56, 60, 69).getRGB());
        Renderer2D.drawRoundedOutline((float)this.x, (float)this.y, (float)(this.x + this.getSize()[0]), (float)this.y + 35.0f, 2, 1.0f, new Color(25, 38, 47));
    }
    
    void drawMenu() {
        Renderer2D.drawRoundedRect((float)this.x, (float)this.y + 40.0f, (float)(this.x + this.getSize()[0]), (float)(this.y + this.getSize()[1]), 2, new Color(45, 48, 55));
        Renderer2D.drawRoundedOutline((float)this.x, (float)this.y + 40.0f, (float)(this.x + this.getSize()[0]), (float)(this.y + this.getSize()[1]), 2, 1.0f, new Color(25, 38, 47));
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        this.categories.forEach(c -> c.keyTyped(typedChar, keyCode));
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHoldingHeader(mouseX, mouseY) && mouseButton == 0) {
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
            this.dragging = true;
        }
        this.categories.forEach(button -> button.mouseClicked(mouseX, mouseY, mouseButton));
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.categories.forEach(c -> c.mouseReleased(mouseX, mouseY, state));
        this.dragging = false;
    }
    
    @Override
    public void update(final int mouseX, final int mouseY) {
        if (this.dragging) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }
    }
    
    @Override
    public double[] getSize() {
        return new double[] { 286.0, 320.0 };
    }
    
    boolean isHoldingHeader(final int x, final int y) {
        return x > this.x && x < this.x + this.getSize()[0] && y > this.y && y < this.y + 8.0;
    }
}
