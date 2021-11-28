//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.click;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.OpenGlHelper;
import java.util.Iterator;
import org.lwjgl.input.Mouse;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.Global;
import wtf.cattyn.moneymod.client.module.client.ClickGui;
import wtf.cattyn.moneymod.client.module.Module;
import java.util.ArrayList;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class Screen extends GuiScreen
{
    public static List<Panel> panels;
    public static Color color;
    public static Description description;
    
    public Screen() {
        Screen.panels = new ArrayList<Panel>();
        Screen.description = new Description();
        int frameX = 10;
        for (final Module.Category category : Module.Category.values()) {
            final Panel panel = new Panel(category);
            panel.setY(10);
            panel.setX(frameX);
            Screen.panels.add(panel);
            frameX += panel.getWidth() + 10;
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        ClickGui.update();
        Screen.description.reset();
        if (this.mc.player != null) {
            this.doScroll();
        }
        final Global globals = (Global)Main.getModuleManager().get(Global.class);
        Screen.color = globals.colorSetting.getValue();
        Screen.panels.forEach(panel -> {
            panel.renderPanel(mouseX, mouseY);
            panel.updatePosition(mouseX, mouseY);
            panel.getComponents().forEach(c -> c.updateComponent(mouseX, mouseY));
            return;
        });
        final ScaledResolution sr = new ScaledResolution(this.mc);
        RenderUtil.drawStringWithShadow(((ClickGui)Main.getModuleManager().get(ClickGui.class)).niggerz, 2, sr.getScaledHeight() - 11, Screen.color.getRGB());
        Screen.description.draw();
    }
    
    private void doScroll() {
        final int n = Mouse.getDWheel();
        if (n >= 0) {
            if (n > 0) {
                for (final Panel panel : Screen.panels) {
                    panel.setY(panel.getY() + 8);
                }
            }
        }
        else {
            for (final Panel panel : Screen.panels) {
                panel.setY(panel.getY() - 8);
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Panel panel : Screen.panels) {
            if (panel.isHover(mouseX, mouseY) && mouseButton == 0) {
                panel.setDrag(true);
                panel.dragX = mouseX - panel.getX();
                panel.dragY = mouseY - panel.getY();
            }
            if (panel.isHover(mouseX, mouseY) && mouseButton == 1) {
                panel.setOpen(!panel.isOpen());
            }
            if (panel.isOpen() && !panel.getComponents().isEmpty()) {
                for (final Component component : panel.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Panel panel : Screen.panels) {
            panel.setDrag(false);
        }
        for (final Panel panel : Screen.panels) {
            if (panel.isOpen() && !panel.getComponents().isEmpty()) {
                for (final Component component : panel.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }
    
    public void drawGradient(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        this.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        for (final Panel panel : Screen.panels) {
            if (panel.isOpen() && keyCode != 1 && !panel.getComponents().isEmpty()) {
                for (final Component component : panel.getComponents()) {
                    component.keyTyped(keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    public void onGuiClosed() {
        if (this.mc.entityRenderer.isShaderActive()) {
            this.mc.entityRenderer.stopUseShader();
        }
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public Description getDescriptionManager() {
        return Screen.description;
    }
    
    public void initGui() {
        if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer && ((ClickGui)Main.getModuleManager().get(ClickGui.class)).blur.getValue()) {
            if (this.mc.entityRenderer.getShaderGroup() != null) {
                this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }
}
