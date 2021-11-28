// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import wtf.cattyn.moneymod.api.event.EventBlock;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import wtf.cattyn.moneymod.mixin.ducks.IPlayerControllerMP;

@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP implements IPlayerControllerMP
{
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> p_Info) {
        final EventBlock l_Event = new EventBlock(posBlock, directionFacing);
        MinecraftForge.EVENT_BUS.post((Event)l_Event);
        if (l_Event.isCanceled()) {
            p_Info.setReturnValue(false);
            p_Info.cancel();
        }
    }
}
