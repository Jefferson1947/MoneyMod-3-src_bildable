//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraft.util.EnumHand;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Swing", desc = "Changes ur view model", cat = Module.Category.RENDER)
public class Swing extends Module
{
    private final Setting<Boolean> offhand;
    
    public Swing() {
        this.offhand = this.register("Offhand", false);
    }
    
    @Override
    public void onTick() {
        Swing.mc.player.swingingHand = (this.offhand.getValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
    }
}
