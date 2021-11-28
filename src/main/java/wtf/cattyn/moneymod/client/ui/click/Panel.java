//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click;

import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.client.module.client.ClickGui;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Iterator;
import wtf.cattyn.moneymod.client.ui.click.components.ModuleButton;
import wtf.cattyn.moneymod.Main;
import java.util.ArrayList;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.util.Globals;

public class Panel implements Globals
{
    public Module.Category category;
    public ArrayList<Component> components;
    private boolean open;
    private final int width;
    private int y;
    private int x;
    private final int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    private int height;
    
    public Panel(final Module.Category category) {
        this.category = category;
        this.components = new ArrayList<Component>();
        this.width = 110;
        this.barHeight = 18;
        this.dragX = 0;
        this.open = true;
        this.isDragging = false;
        int componentY = this.barHeight;
        for (final Module m : Main.getModuleManager().get(category)) {
            final ModuleButton moduleButton = new ModuleButton(m, this, componentY);
            this.components.add(moduleButton);
            componentY += 12;
        }
        this.update();
    }
    
    public void renderPanel(final int mouseX, final int mouseY) {
        final ScaledResolution sr = new ScaledResolution(Panel.mc);
        GlStateManager.scale(ClickGui.size, ClickGui.size, 0.0f);
        final ClickGui clickgui = (ClickGui)Main.getModuleManager().get(ClickGui.class);
        Gui.drawRect(this.x - 2, this.y - 2, this.x + this.width + 2, this.y, new Color(255, 255, 255, 90).getRGB());
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight - 2, new Color(0, 0, 0, 210).getRGB());
        if (this.open) {
            Gui.drawRect(this.x, this.y + this.barHeight - 2, this.x + this.width, this.y + this.barHeight, new Color(255, 255, 255, 90).getRGB());
        }
        RenderUtil.drawStringWithShadow(this.category.name() + (clickgui.modulecounter.getValue() ? (ChatFormatting.WHITE + " (" + this.components.size() + ")") : ""), this.x + 3, this.y + 4, Screen.color.getRGB());
        if (((ClickGui)Main.getModuleManager().get(ClickGui.class)).glow.getValue()) {
            Renderer2D.drawGlow(this.x + 4, this.y + 2, this.x + RenderUtil.getStringWidth(this.category.getName()) + 7, this.y + 4 + RenderUtil.getFontHeight(), ColorUtil.injectAlpha(Screen.color, 90).getRGB());
        }
        RenderUtil.drawStringWithShadow(this.open ? "-" : "+", this.x + this.width - 10, this.y + 4, Color.GRAY.getRGB());
        if (this.open && !this.components.isEmpty()) {
            ModuleButton moduleButton;
            this.components.forEach(component -> {
                component.render(mouseX, mouseY);
                if (component instanceof ModuleButton) {
                }
                return;
            });
        }
        final int localHeight = this.open ? this.height : (this.barHeight - 2);
        Gui.drawRect(this.x - 2, this.y, this.x, this.y + localHeight, new Color(255, 255, 255, 90).getRGB());
        Gui.drawRect(this.x + this.width, this.y, this.x + this.width + 2, this.y + localHeight, new Color(255, 255, 255, 90).getRGB());
        Gui.drawRect(this.x - 2, this.y + localHeight, this.x + this.width + 2, this.y + localHeight + 2, new Color(255, 255, 255, 90).getRGB());
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public void update() {
        int off = this.barHeight;
        for (final Component comp : this.components) {
            comp.setOffset(off);
            off += comp.getHeight();
        }
        this.height = off;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int newX) {
        this.x = newX;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int newY) {
        this.y = newY;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }
    
    public boolean isHover(final double x, final double y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
}
