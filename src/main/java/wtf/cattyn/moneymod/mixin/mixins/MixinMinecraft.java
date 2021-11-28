//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import wtf.cattyn.moneymod.Main;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.Session;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import wtf.cattyn.moneymod.mixin.ducks.IMinecraft;

@Mixin({ Minecraft.class })
public class MixinMinecraft implements IMinecraft
{
    @Shadow
    private Session session;
    
    @Override
    public void setClientSession(final Session session) {
        this.session = session;
    }
    
    @Inject(method = { "getLimitFramerate" }, at = { @At("HEAD") }, cancellable = true)
    public void getLimitFramerate(final CallbackInfoReturnable<Integer> info) {
        if (Main.shaders.currentshader != null && Minecraft.getMinecraft().player == null) {
            info.setReturnValue(60);
        }
    }
}
