//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.client;

import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.Globals;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "OTCHud", cat = Module.Category.CLIENT)
public class CSHud extends Module
{
    public final Setting<Color> startColor;
    public final Setting<Color> endColor;
    public final Setting<Color> textColor;
    private final Setting<Boolean> watermark;
    private final Setting<Boolean> testShit;
    long hp;
    
    public CSHud() {
        this.startColor = this.register("StartColor", new Color(51, 255, 0), false);
        this.endColor = this.register("End Color", new Color(233, 0, 255), false);
        this.textColor = this.register("Text Color", new Color(255, 251, 244), false);
        this.watermark = this.register("Watermark", true);
        this.testShit = this.register("Creative", true);
    }
    
    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Pre event) {
        if (this.testShit.getValue()) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.ARMOR) {
                event.setCanceled(true);
            }
            if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
                event.setCanceled(true);
            }
            if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
            }
            if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
                event.setCanceled(true);
            }
            if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) {
                event.setCanceled(true);
            }
        }
    }
    
    @Override
    public void onTick() {
        this.hp = (long)Math.min(CSHud.mc.player.getHealth() * 5.0f, 100.0f);
    }
    
    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Text event) {
        final ScaledResolution sr = new ScaledResolution(CSHud.mc);
        RenderUtil.drawString("HP: " + this.hp, 20, 20, this.textColor.getValue().getRGB());
        if (this.watermark.getValue()) {
            final int x = 4;
            final int y = 7;
            final String text = "moneymod + | " + Globals.getPlayerPing() + "ms | " + Main.getFpsManager().getFPS() + "fps";
            Renderer2D.drawHGradientRect((float)x, (float)(y - 4), (float)(RenderUtil.getStringWidth(text) + 6), 14.0f, ColorUtil.getGuiColor().getRGB(), ColorUtil.getGuiColor().brighter().getRGB());
            Renderer2D.drawRect((float)x, (float)(y - 2), (float)(RenderUtil.getStringWidth(text) + 6), 16.0f, new Color(56, 60, 69).getRGB());
            RenderUtil.drawString(text, x + 1, y, this.textColor.getValue().getRGB());
        }
    }
}
