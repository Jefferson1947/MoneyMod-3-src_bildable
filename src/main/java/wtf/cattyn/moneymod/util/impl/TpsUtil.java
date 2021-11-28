//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import java.util.Arrays;

public class TpsUtil
{
    public static float[] tickRates;
    public int nextIndex;
    public long timeLastTimeUpdate;
    
    public TpsUtil() {
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(TpsUtil.tickRates, Float.intBitsToFloat(Float.floatToIntBits(2.9576416E38f) ^ 0x7F5E821B));
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public static float getTickRate() {
        float numTicks = Float.intBitsToFloat(Float.floatToIntBits(2.6594344E38f) ^ 0x7F4812D8);
        float sumTickRates = Float.intBitsToFloat(Float.floatToIntBits(2.9529825E38f) ^ 0x7F5E2860);
        for (final float tickRate : TpsUtil.tickRates) {
            if (tickRate > Float.intBitsToFloat(Float.floatToIntBits(2.4348146E38f) ^ 0x7F372CD3)) {
                sumTickRates += tickRate;
                numTicks += Float.intBitsToFloat(Float.floatToIntBits(74.97167f) ^ 0x7D15F17F);
            }
        }
        return MathHelper.clamp(sumTickRates / numTicks, Float.intBitsToFloat(Float.floatToIntBits(2.5962172E38f) ^ 0x7F435153), Float.intBitsToFloat(Float.floatToIntBits(0.2559092f) ^ 0x7F230688));
    }
    
    public static float getTpsFactor() {
        final float TPS = getTickRate();
        return Float.intBitsToFloat(Float.floatToIntBits(0.38616f) ^ 0x7F65B6C3) / TPS;
    }
    
    public void onTimeUpdate() {
        if (this.timeLastTimeUpdate != -1L) {
            final float timeElapsed = (System.currentTimeMillis() - this.timeLastTimeUpdate) / Float.intBitsToFloat(Float.floatToIntBits(0.0028837158f) ^ 0x7F46FCB9);
            TpsUtil.tickRates[this.nextIndex % TpsUtil.tickRates.length] = MathHelper.clamp(Float.intBitsToFloat(Float.floatToIntBits(0.4944555f) ^ 0x7F5D2945) / timeElapsed, Float.intBitsToFloat(Float.floatToIntBits(1.4012424E38f) ^ 0x7ED2D5E5), Float.intBitsToFloat(Float.floatToIntBits(0.026974885f) ^ 0x7D7CFA6F));
            ++this.nextIndex;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
    
    @SubscribeEvent
    public void onUpdate(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            this.onTimeUpdate();
        }
    }
    
    static {
        TpsUtil.tickRates = new float[20];
    }
}
