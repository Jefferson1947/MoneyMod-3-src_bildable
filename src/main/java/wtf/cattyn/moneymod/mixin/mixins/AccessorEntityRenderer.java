//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.mixin.mixins;

import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import wtf.cattyn.moneymod.mixin.ducks.IEntityRenderer;

@Mixin({ EntityRenderer.class })
public class AccessorEntityRenderer implements IEntityRenderer
{
    @Shadow
    private void setupCameraTransform(final float partialTicks, final int pass) {
    }
    
    @Override
    public void setupCamera(final float partialTicks, final int pass) {
        this.setupCameraTransform(partialTicks, pass);
    }
}
