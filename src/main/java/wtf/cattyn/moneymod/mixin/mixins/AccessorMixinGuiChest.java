// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.gui.inventory.GuiChest;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiChest.class })
public interface AccessorMixinGuiChest
{
    @Accessor("lowerChestInventory")
    IInventory getLowerInventory();
}
