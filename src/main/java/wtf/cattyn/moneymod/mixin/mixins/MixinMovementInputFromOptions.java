//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiChat;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.movement.NoSlow;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import wtf.cattyn.moneymod.util.Globals;

@Mixin({ MovementInputFromOptions.class })
public class MixinMovementInputFromOptions implements Globals
{
    @Redirect(method = { "updatePlayerMoveState" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(final KeyBinding keyBinding) {
        final NoSlow noslow = (NoSlow)Main.getModuleManager().get(NoSlow.class);
        return (noslow.isToggled() && noslow.inventory.getValue() && !(MixinMovementInputFromOptions.mc.currentScreen instanceof GuiChat)) ? Keyboard.isKeyDown(keyBinding.getKeyCode()) : keyBinding.isKeyDown();
    }
}
