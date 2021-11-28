// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "CustomCrystal", desc = "Changes crystal scale", cat = Module.Category.RENDER)
public class CustomCrystal extends Module
{
    public final Setting<Number> scale;
    public final Setting<Boolean> cancelRotation;
    public final Setting<Boolean> cancelPosChange;
    public final Setting<Boolean> chams;
    public final Setting<Color> cColor;
    public final Setting<Boolean> lines;
    public final Setting<Number> linesWidht;
    
    public CustomCrystal() {
        this.scale = this.register("Scale", 1.0, 0.0, 1.0, 0.1);
        this.cancelRotation = this.register("CancelRotation", true);
        this.cancelPosChange = this.register("CancelPosChange", true);
        this.chams = this.register("Chams", false);
        this.cColor = this.register("Chams Color", new Color(255, 255, 255), false);
        this.lines = this.register("Lines", false);
        this.linesWidht = this.register("LinesWidht", 1.0, 0.0, 2.0, 0.1);
    }
}
