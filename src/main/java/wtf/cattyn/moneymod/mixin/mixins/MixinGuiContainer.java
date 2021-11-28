//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.item.ItemShulkerBox;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.render.ShulkerPreview;
import wtf.cattyn.moneymod.util.Globals;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiContainer.class })
public class MixinGuiContainer
{
    @Inject(method = { "mouseClicked" }, at = { @At("HEAD") }, cancellable = true)
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final CallbackInfo info) {
        if (Globals.mc.player != null && Globals.mc.world != null && mouseButton == 2) {
            final ShulkerPreview sp = (ShulkerPreview)Main.getModuleManager().get(ShulkerPreview.class);
            if (sp.isToggled()) {
                sp.setState(true);
                if (sp.getItem() instanceof ItemShulkerBox) {
                    info.cancel();
                }
            }
        }
    }
}
