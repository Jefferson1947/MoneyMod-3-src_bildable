//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import wtf.cattyn.moneymod.client.module.client.Global;
import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AntiAim", cat = Module.Category.MISC)
public class AntiAim extends Module
{
    private final Setting<Number> speed;
    private final Setting<Number> jitterOffset;
    private final Setting<String> type;
    int aim_tick;
    int jitter_tick;
    int lag_tick;
    
    public AntiAim() {
        this.speed = this.register("Speed", 5.0, -20.0, 20.0, 0.1);
        this.jitterOffset = this.register("JitterOffset", 230.0, -360.0, 360.0, 10.0);
        this.type = this.register("Mode", "Spin", Arrays.asList("Spin", "Jitter", "Random", "Back", "Lag", "None"));
    }
    
    public void onEnable() {
        this.aim_tick = 0;
        this.jitter_tick = 0;
        this.lag_tick = 0;
    }
    
    @Override
    public void onTick() {
        if (this.type.getValue().equalsIgnoreCase("Spin")) {
            final float value = this.speed.getValue().floatValue();
            this.setAim(this.aim_tick += (int)value);
            if (this.aim_tick >= 360) {
                this.aim_tick = 0;
            }
        }
        if (this.type.getValue().equalsIgnoreCase("Jitter")) {
            final int oldAim = (int)AntiAim.mc.player.rotationYawHead;
            ++this.jitter_tick;
            if (this.jitter_tick == 2) {
                this.setAim((int)(AntiAim.mc.player.rotationYawHead + this.jitterOffset.getValue().floatValue()));
            }
            if (this.jitter_tick >= 6) {
                this.setAim(oldAim);
                this.jitter_tick = 0;
            }
        }
        if (this.type.getValue().equalsIgnoreCase("Random")) {
            this.setAim(Global.random.nextInt(360));
        }
        if (this.type.getValue().equalsIgnoreCase("Back")) {
            this.setAim((int)(AntiAim.mc.player.rotationYawHead + 180.0f));
        }
        if (this.type.getValue().equalsIgnoreCase("Lag")) {
            final int oldAim = (int)AntiAim.mc.player.rotationYawHead;
            ++this.lag_tick;
            if (this.lag_tick >= 1) {
                this.setAim(oldAim + 5);
            }
            if (this.lag_tick >= 2) {
                this.setAim(oldAim - 5);
            }
            if (this.lag_tick >= 3) {
                this.setAim(oldAim);
                this.lag_tick = 0;
            }
        }
        if (this.type.getValue().equalsIgnoreCase("None")) {
            return;
        }
    }
    
    public void setAim(final int aim) {
        AntiAim.mc.player.renderYawOffset = (float)aim;
        AntiAim.mc.player.prevRenderYawOffset = (float)aim;
    }
}
