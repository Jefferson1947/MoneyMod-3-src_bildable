//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "FastXP", cat = Module.Category.MISC)
public class FastXP extends Module
{
    @Override
    public void onTick() {
        if (FastXP.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() != Items.EXPERIENCE_BOTTLE || FastXP.mc.player.isHandActive()) {}
    }
}
