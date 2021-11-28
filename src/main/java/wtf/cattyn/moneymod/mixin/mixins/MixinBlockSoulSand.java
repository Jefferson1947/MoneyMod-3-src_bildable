// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.moneymod.api.event.SoulSandCollisionEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.block.BlockSoulSand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockSoulSand.class })
public class MixinBlockSoulSand
{
    @Inject(method = { "onEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityCollision(final CallbackInfo info) {
        final SoulSandCollisionEvent event = new SoulSandCollisionEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
}
