//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import wtf.cattyn.moneymod.util.impl.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiGameOver;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "PlayerDeath", desc = "", cat = Module.Category.MISC)
public class PlayerDeath extends Module
{
    private final Setting<Boolean> autoRespawn;
    private final Setting<Boolean> deathCoords;
    
    public PlayerDeath() {
        this.autoRespawn = this.register("AutoRespawn", true);
        this.deathCoords = this.register("DeathCoords", true);
    }
    
    @Override
    public void onArtificialTick() {
        if (this.nullCheck()) {
            return;
        }
        if (PlayerDeath.mc.currentScreen instanceof GuiGameOver) {
            if (this.autoRespawn.getValue()) {
                PlayerDeath.mc.player.respawnPlayer();
                PlayerDeath.mc.displayGuiScreen((GuiScreen)null);
            }
            if (this.deathCoords.getValue()) {
                ChatUtil.sendMessage(ChatFormatting.GOLD + "[PlayerDeath] " + ChatFormatting.YELLOW + (int)PlayerDeath.mc.player.posX + " " + (int)PlayerDeath.mc.player.posY + " " + (int)PlayerDeath.mc.player.posZ);
            }
        }
    }
}
