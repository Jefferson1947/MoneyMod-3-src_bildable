//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.client.module.client.Capes;
import wtf.cattyn.moneymod.Main;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ AbstractClientPlayer.class })
public class MixinAbstractClientPlayer
{
    @Shadow
    private NetworkPlayerInfo playerInfo;
    
    @Inject(method = { "getLocationCape" }, at = { @At("RETURN") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> info) {
        try {
            final String uuid = this.playerInfo.getGameProfile().getId().toString();
            if (Main.getCapeThread().isCapePresent(uuid)) {
                final Capes mod = (Capes)Main.getModuleManager().get(Capes.class);
                if (info.getReturnValue() != null && !mod.override.getValue()) {
                    return;
                }
                info.setReturnValue(Main.getCapeThread().getCapeLocation(uuid));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
