//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.managment;

import net.minecraft.util.math.MathHelper;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.Global;

public class PulseManager
{
    public static int min;
    public static int max;
    private int current;
    private boolean up;
    
    public PulseManager() {
        this.current = PulseManager.min;
        this.up = true;
    }
    
    public void update() {
        PulseManager.min = ((Global)Main.getModuleManager().get(Global.class)).pulseMin.getValue().intValue();
        PulseManager.max = ((Global)Main.getModuleManager().get(Global.class)).pulseMax.getValue().intValue();
        this.current = this.step(this.current);
    }
    
    public int getCurrentPulse() {
        return this.current;
    }
    
    public int getDifference(final int value) {
        int ret = this.current;
        if (this.up) {
            ret += value % 210;
            if (ret > PulseManager.max) {
                final int i = Math.abs(ret - PulseManager.max);
                ret = PulseManager.max - i;
            }
        }
        else {
            ret -= value % 210;
            if (ret < PulseManager.min) {
                final int i = Math.abs(ret - PulseManager.min);
                ret = PulseManager.min + i;
            }
        }
        return MathHelper.clamp(ret, PulseManager.min, PulseManager.max);
    }
    
    public int clamp(int value) {
        if (this.up) {
            if (value >= PulseManager.max) {
                value = PulseManager.max;
                this.up = false;
            }
        }
        else if (value <= PulseManager.min) {
            value = PulseManager.min;
            this.up = true;
        }
        return value;
    }
    
    public int step(final int from) {
        int ret = (int)(this.up ? (from + PulseManager.max / ((Global)Main.getModuleManager().get(Global.class)).pulseSpeed.getValue().doubleValue() * Main.getFpsManager().getFrametime()) : (from - PulseManager.min / ((Global)Main.getModuleManager().get(Global.class)).pulseSpeed.getValue().doubleValue() * Main.getFpsManager().getFrametime()));
        ret = this.clamp(ret);
        return ret;
    }
    
    static {
        PulseManager.min = 100;
        PulseManager.max = 255;
    }
}
