//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.fuck;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;

public class OTCScreen extends GuiScreen
{
    public Window mainWindow;
    
    public OTCScreen() {
        this.mainWindow = new Window(30.0, 40.0);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.mainWindow.drawScreen(mouseX, mouseY, partialTicks);
        this.mainWindow.update(mouseX, mouseY);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.mainWindow.keyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.mainWindow.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.mainWindow.mouseReleased(mouseX, mouseY, state);
    }
    
    public void initGui() {
        super.initGui();
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
}
