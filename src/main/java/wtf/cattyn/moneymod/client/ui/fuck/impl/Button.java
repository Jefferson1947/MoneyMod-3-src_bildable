// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck.impl;

import wtf.cattyn.moneymod.api.managment.FriendManager;
import wtf.cattyn.moneymod.client.ui.fuck.Window;
import wtf.cattyn.moneymod.client.ui.fuck.IComponent;

public abstract class Button implements IComponent
{
    public double x;
    public double y;
    public double width;
    public double height;
    public double scroll;
    public double localX;
    public double localY;
    public boolean hover;
    public Window window;
    public FriendManager components;

    public Button(final double x, final double y, final double width, final double height, final Window window) {
        this.localX = x;
        this.localY = y;
        this.x = window.x + x;
        this.y = window.y + y;
        this.width = width;
        this.height = height;
        this.window = window;
        this.scroll = 0.0;
    }
    
    @Override
    public abstract void drawScreen(final int p0, final int p1, final float p2);
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    @Override
    public void update(final int mouseX, final int mouseY) {
        this.hover = this.isHover(mouseX, mouseY);
        this.x = this.window.x + this.localX;
        this.y = this.window.y + this.localY + this.scroll;
    }
    
    @Override
    public double[] getSize() {
        return new double[] { this.width, this.height };
    }
    
    public boolean isHover(final int x, final int y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
    }
}
