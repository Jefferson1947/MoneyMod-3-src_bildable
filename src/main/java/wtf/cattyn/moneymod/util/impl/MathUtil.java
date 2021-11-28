// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl;

public class MathUtil
{
    public static double clamp(final double value, final double min, final double max) {
        return Math.max(Math.min(value, max), min);
    }
    
    public static double square(final double input) {
        return input * input;
    }
    
    public static float square(final float input) {
        return input * input;
    }
    
    public static double interpolate(final double from, final double to, final double difference) {
        return from + (to - from) * difference;
    }
}
