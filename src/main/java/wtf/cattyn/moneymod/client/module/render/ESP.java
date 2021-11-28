//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.util.impl.shader.impl.GlowShader;
import net.minecraft.entity.player.EntityPlayer;
import wtf.cattyn.moneymod.util.impl.shader.impl.FlowShader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "ESP", desc = "player wallhak", cat = Module.Category.RENDER)
public class ESP extends Module
{
    public final Setting<Boolean> glowEsp;
    public final Setting<Number> radius;
    public final Setting<Number> quality;
    public final Setting<Color> color;
    public final Setting<Boolean> playerShader;
    
    public ESP() {
        this.glowEsp = this.register("Glow Esp", false);
        this.radius = this.register("Radius", 2.0, 0.0, 5.0, 0.1);
        this.quality = this.register("Quality", 1.0, 0.0, 5.0, 0.1);
        this.color = this.register("Color", new Color(255, 255, 255), false);
        this.playerShader = this.register("PlayerShader", false);
    }
    
    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Pre event) {
        if (this.nullCheck()) {
            return;
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            GlStateManager.pushMatrix();
            if (this.playerShader.getValue()) {
                FlowShader.INSTANCE.startDraw(event.getPartialTicks());
                ESP.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer && e != ESP.mc.player).forEach(e -> ESP.mc.getRenderManager().renderEntityStatic(e, event.getPartialTicks(), true));
                FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f);
            }
            if (this.glowEsp.getValue()) {
                GlowShader.INSTANCE.startDraw(event.getPartialTicks());
                ESP.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer && e != ESP.mc.player).forEach(e -> ESP.mc.getRenderManager().renderEntityStatic(e, event.getPartialTicks(), true));
                GlowShader.INSTANCE.stopDraw(this.color.getValue(), this.radius.getValue().floatValue(), this.quality.getValue().floatValue());
            }
            GlStateManager.popMatrix();
        }
    }
}
