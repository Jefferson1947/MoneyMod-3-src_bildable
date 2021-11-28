//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import wtf.cattyn.moneymod.Main;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Timer.class })
public class MixinTimer
{
    @Shadow
    public float elapsedPartialTicks;
    
    @Inject(method = { "updateTimer" }, at = { @At(value = "FIELD", target = "net/minecraft/util/Timer.elapsedPartialTicks:F", ordinal = 1) })
    public void updateTimer(final CallbackInfo info) {
        this.elapsedPartialTicks *= Main.TIMER_VALUE;
    }
}
