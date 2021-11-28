//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import wtf.cattyn.moneymod.util.impl.EntityUtil;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Sprint", desc = "Auto Sprint", cat = Module.Category.MOVEMENT)
public class Sprint extends Module
{
    @Override
    public void onTick() {
        if (EntityUtil.isMoving()) {
            Sprint.mc.player.setSprinting(true);
        }
    }
}
