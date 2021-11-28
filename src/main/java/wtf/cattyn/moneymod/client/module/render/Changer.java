//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Changer", desc = "Changes some shit", cat = Module.Category.RENDER)
public class Changer extends Module
{
    public final Setting<Number> timeval;
    public final Setting<Number> gammaval;
    public final Setting<Number> fpsval;
    public final Setting<Number> fov;
    public final Setting<Boolean> clearRain;
    public final Setting<Boolean> customTime;
    public final Setting<Boolean> customGamma;
    public final Setting<Boolean> customFps;
    public final Setting<Boolean> customFov;
    
    public Changer() {
        this.timeval = this.register("Time", 12.0, 0.0, 24.0, 1.0);
        this.gammaval = this.register("Gamma", 300.0, 1.0, 300.0, 1.0);
        this.fpsval = this.register("Fps", 240.0, 10.0, 1000.0, 10.0);
        this.fov = this.register("Fps", 100.0, 100.0, 169.0, 1.0);
        this.clearRain = this.register("Clear Rain", false);
        this.customTime = this.register("Custom Time", false);
        this.customGamma = this.register("Custom Gamma", true);
        this.customFps = this.register("Custom FPS", true);
        this.customFov = this.register("Custom Fov", true);
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (this.customTime.getValue()) {
            Changer.mc.world.setWorldTime(this.timeval.getValue().intValue() * 1000L);
        }
        if (this.customGamma.getValue()) {
            Changer.mc.gameSettings.gammaSetting = this.gammaval.getValue().floatValue();
        }
        if (this.customFps.getValue()) {
            Changer.mc.gameSettings.limitFramerate = this.fpsval.getValue().intValue();
        }
        if (this.customFov.getValue()) {
            Changer.mc.gameSettings.fovSetting = (float)this.fov.getValue().intValue();
        }
        if (this.clearRain.getValue()) {
            Changer.mc.world.setRainStrength(0.0f);
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate && this.customTime.getValue()) {
            event.setCanceled(true);
        }
    }
}
