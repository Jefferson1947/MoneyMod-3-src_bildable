//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.ui.tokenlogin.gui.GuiAltManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.gui.GuiButton;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.GuiScreen;

@Mixin({ GuiMainMenu.class })
public class MixinGuiMainMenu extends GuiScreen
{
    @Inject(method = { "addSingleplayerMultiplayerButtons" }, at = { @At("RETURN") })
    private void addSingleplayerMultiplayerButtons(final int p_73969_1_, final int p_73969_2_, final CallbackInfo info) {
        this.buttonList.add(new GuiButton(93543, this.width / 2 - 100, 5, "Access Token Login"));
        this.buttonList.add(new GuiButton(93544, this.width / 2 - 100, 20, "IRC"));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") })
    public void actionPerformed(final GuiButton button, final CallbackInfo info) {
        if (button.id == 93543) {
            this.mc.displayGuiScreen((GuiScreen)new GuiAltManager());
        }
        if (button.id == 93544) {
            this.mc.displayGuiScreen((GuiScreen)Main.getIrcScreen());
        }
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    public void initGui(final CallbackInfo info) {
        Main.shaders.init();
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("HEAD") })
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo info) {
        if (Main.shaders.currentshader != null) {
            GlStateManager.disableCull();
            Main.shaders.currentshader.useShader(this.width * 2, this.height * 2, (float)(mouseX * 2), (float)(mouseY * 2), (System.currentTimeMillis() - Main.shaders.time) / 1000.0f);
            GL11.glBegin(7);
            GL11.glVertex2f(-1.0f, -1.0f);
            GL11.glVertex2f(-1.0f, 1.0f);
            GL11.glVertex2f(1.0f, 1.0f);
            GL11.glVertex2f(1.0f, -1.0f);
            GL11.glEnd();
            GL20.glUseProgram(0);
        }
    }
    
    @Inject(method = { "renderSkybox" }, at = { @At("HEAD") }, cancellable = true)
    public void renderSkybox(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo info) {
        if (Main.shaders.currentshader != null) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 0))
    public void drawGradientRect1(final GuiMainMenu guiMainMenu, final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        if (Main.shaders.currentshader == null) {
            this.drawGradientRect(left, top, right, bottom, startColor, endColor);
        }
    }
    
    @Redirect(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 1))
    public void drawGradientRect2(final GuiMainMenu guiMainMenu, final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        if (Main.shaders.currentshader == null) {
            this.drawGradientRect(left, top, right, bottom, startColor, endColor);
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("TAIL") }, cancellable = true)
    public void drawText(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        RenderUtil.drawStringWithShadow("MoneyMod+3 b550", 2, 2, -1);
        RenderUtil.drawStringWithShadow("Online users: " + Main.getCapeThread().getUserCount(), 2, 2 + RenderUtil.getFontHeight(), -1);
    }
}
