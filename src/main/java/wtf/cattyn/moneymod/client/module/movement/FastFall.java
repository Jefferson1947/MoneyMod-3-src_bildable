//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import wtf.cattyn.moneymod.client.module.combat.SelfFill;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "FastFall", desc = "Makes you fall faster", cat = Module.Category.MOVEMENT)
public class FastFall extends Module
{
    private final Setting<Boolean> ccMode;
    
    public FastFall() {
        this.ccMode = this.register("CcMode", true);
    }
    
    @Override
    public void onTick() {
        if (Main.getModuleManager().get(SelfFill.class).isToggled()) {
            return;
        }
        if (FastFall.mc.player.isInWater() || FastFall.mc.player.isInLava() || FastFall.mc.player.isOnLadder()) {
            return;
        }
        if (FastFall.mc.player.onGround) {
            final EntityPlayerSP player = FastFall.mc.player;
            player.motionY -= (this.ccMode.getValue() ? 0.62f : 1.0f);
        }
    }
}
