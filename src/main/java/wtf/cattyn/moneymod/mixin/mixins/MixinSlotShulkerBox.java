// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.misc.ShulkerCeption;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.SlotShulkerBox;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ SlotShulkerBox.class })
public class MixinSlotShulkerBox
{
    @Inject(method = { "isItemValid" }, at = { @At("HEAD") }, cancellable = true)
    public void isItemValid(final ItemStack item, final CallbackInfoReturnable<Boolean> info) {
        final ShulkerCeption sc = (ShulkerCeption)Main.getModuleManager().get(ShulkerCeption.class);
        if (sc.isToggled()) {
            info.setReturnValue(true);
        }
    }
}
