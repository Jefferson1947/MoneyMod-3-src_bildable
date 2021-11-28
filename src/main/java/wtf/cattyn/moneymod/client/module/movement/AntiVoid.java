//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AntiVoid", cat = Module.Category.MOVEMENT)
public class AntiVoid extends Module
{
    public final Setting<String> mode;
    
    public AntiVoid() {
        this.mode = this.register("Mode", "Glide", Arrays.asList("Glide", "Bounce"));
    }
    
    @Override
    public void onTick() {
        if (!AntiVoid.mc.player.onGround && AntiVoid.mc.player.posY <= 0.0) {
            AntiVoid.mc.player.motionY = (this.mode.getValue().equalsIgnoreCase("Glide") ? -0.009999999776482582 : 0.4000000059604645);
        }
    }
}
