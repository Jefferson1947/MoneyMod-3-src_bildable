//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.irc;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import java.awt.Color;
import net.minecraft.util.ResourceLocation;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.IRCGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextComponentString;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class IrcScreen extends GuiScreen
{
    private final ArrayList<TextComponentString> messages;
    private GuiTextField message;
    private GuiButton back;
    
    public IrcScreen() {
        this.messages = new ArrayList<TextComponentString>();
    }
    
    public ArrayList<TextComponentString> getMessages() {
        return this.messages;
    }
    
    public void initGui() {
        this.message = new GuiTextField(1488, this.fontRenderer, 5, this.height - 26, this.width - 10, 20);
        this.buttonList.add(this.back = new GuiButton(13371, 2, 2, 98, 20, "Back"));
        this.message.setMaxStringLength(256);
        if (this.mc.world != null && OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer && ((IRCGui)Main.getModuleManager().get(IRCGui.class)).blur.getValue()) {
            if (this.mc.entityRenderer.getShaderGroup() != null) {
                this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 13371) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.mc.world == null) {
            this.drawDefaultBackground();
        }
        else {
            Renderer2D.drawRect(0.0f, 0.0f, (float)this.width, (float)this.height, new Color(0, 0, 0, 185).getRGB());
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.message.drawTextBox();
        this.drawString(this.fontRenderer, String.format("Online %s (%s)", Main.getCapeThread().getUserCount(), Main.getCapeThread().getOnline()), 102, 8, -1);
        int y = this.height - 50;
        GL11.glPushMatrix();
        this.glScissor(0, 22, this.width, this.height);
        GL11.glEnable(3089);
        try {
            for (final TextComponentString component : this.messages) {
                this.drawString(this.fontRenderer, component.getText(), 10, y, -1);
                y -= this.fontRenderer.FONT_HEIGHT;
            }
        }
        catch (Exception ex) {}
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        try {
            super.keyTyped(typedChar, keyCode);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (keyCode == 28 && !this.message.getText().isEmpty()) {
            Main.getCapeThread().sendChatMessage(this.message.getText());
            this.message.setText("");
        }
        this.message.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.message.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void glScissor(final int x, final int y, final int width, final int height) {
        final Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution resolution = new ScaledResolution(mc);
        final int scale = resolution.getScaleFactor();
        final int scissorWidth = width * scale;
        final int scissorHeight = height * scale;
        final int scissorX = x * scale;
        final int scissorY = mc.displayHeight - scissorHeight - y * scale;
        GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
    }
    
    public void onGuiClosed() {
        if (this.mc.entityRenderer.isShaderActive()) {
            this.mc.entityRenderer.stopUseShader();
        }
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
}
