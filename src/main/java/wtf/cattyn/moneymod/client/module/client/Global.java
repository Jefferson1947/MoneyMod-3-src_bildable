// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.client;

import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Globals", cat = Module.Category.CLIENT)
public class Global extends Module
{
    private static Global INSTANCE;
    public final Setting<Boolean> rainbowPrefix;
    public final Setting<Boolean> chatAnimation;
    public final Setting<Color> colorSetting;
    public final Setting<Number> pulseMax;
    public final Setting<Number> pulseMin;
    public final Setting<Number> pulseSpeed;
    
    public Global() {
        this.rainbowPrefix = this.register("RainbowPrefix", false);
        this.chatAnimation = this.register("ChatAnimation", false);
        this.colorSetting = this.register("Color", new Color(0, 255, 0), false);
        this.pulseMax = this.register("PulseMax", 255.0, 0.0, 255.0, 1.0);
        this.pulseMin = this.register("PulseMin", 110.0, 0.0, 255.0, 1.0);
        this.pulseSpeed = this.register("PulseSpeed", 1.5, 0.1, 10.0, 0.1);
        Global.INSTANCE = this;
    }
    
    public static Global getInstance() {
        return Global.INSTANCE;
    }
}
