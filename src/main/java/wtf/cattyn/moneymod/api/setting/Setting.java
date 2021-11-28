// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.setting;

import java.util.List;
import wtf.cattyn.moneymod.client.module.Module;

public class Setting<T>
{
    private String name;
    private T value;
    private Type type;
    private Module parent;
    private double min;
    private double max;
    private double inc;
    private List<String> modes;
    private boolean rainbow;
    
    public Setting(final String name, final Module module, final T value) {
        this.name = name;
        this.parent = module;
        this.value = value;
        this.type = Type.B;
    }
    
    public Setting(final String name, final Module module, final T value, final double min, final double max, final double inc) {
        this.name = name;
        this.parent = module;
        this.value = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.type = Type.N;
    }
    
    public Setting(final String name, final Module module, final T value, final List<String> modes) {
        this.name = name;
        this.parent = module;
        this.value = value;
        this.modes = modes;
        this.type = Type.M;
    }
    
    public Setting(final String name, final Module module, final T value, final boolean rainbow) {
        this.name = name;
        this.parent = module;
        this.value = value;
        this.rainbow = rainbow;
        this.type = Type.C;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Module getParent() {
        return this.parent;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = (T) value;
    }
    
    public double getInc() {
        return this.inc;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public List<String> getModes() {
        return this.modes;
    }
    
    public boolean isRainbow() {
        return this.rainbow;
    }
    
    public void setRainbow(final boolean rainbow) {
        this.rainbow = rainbow;
    }

    public enum Type
    {
        B, 
        N, 
        M, 
        C;
    }
}
