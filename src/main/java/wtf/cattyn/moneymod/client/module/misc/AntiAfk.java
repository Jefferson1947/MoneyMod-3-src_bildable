//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import wtf.cattyn.moneymod.util.Globals;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AntiAfk", cat = Module.Category.MISC)
public class AntiAfk extends Module
{
    int time;
    
    @Override
    public void onTick() {
        ++this.time;
        if (this.time == 40) {
            AntiAfk.mc.player.rotationYaw = (float)Globals.random.nextInt(360);
            AntiAfk.mc.player.rotationPitch = (float)Globals.random.nextInt(180);
        }
        if (this.time == 60) {
            AntiAfk.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        }
        if (this.time == 80) {
            AntiAfk.mc.player.rotationYaw = (float)Globals.random.nextInt(360);
            AntiAfk.mc.player.rotationPitch = (float)Globals.random.nextInt(180);
            this.time = 0;
        }
    }
    
    public void onEnable() {
        this.time = 0;
    }
}
