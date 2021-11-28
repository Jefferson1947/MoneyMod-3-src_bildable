//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.movement;

import wtf.cattyn.moneymod.Main;
import net.minecraft.entity.MoverType;
import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Vclip", desc = "Teleports u vertically", cat = Module.Category.MOVEMENT)
public class Vclip extends Module
{
    public final Setting<String> mode;
    public final Setting<Number> height;
    public final Setting<Boolean> update;
    public final Setting<Boolean> autoDisable;
    
    public Vclip() {
        this.mode = this.register("Mode", "Position", Arrays.asList("Position", "Tick"));
        this.height = this.register("Height", 1.0, -15.0, 15.0, 1.0);
        this.update = this.register("UpdatePos", true);
        this.autoDisable = this.register("AutoDisable", true);
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (this.mode.getValue().equalsIgnoreCase("Position")) {
            if (this.update.getValue()) {
                Vclip.mc.player.setPosition(Vclip.mc.player.posX, Vclip.mc.player.posY + this.height.getValue().doubleValue(), Vclip.mc.player.posZ);
            }
            else {
                Vclip.mc.player.setPositionAndUpdate(Vclip.mc.player.posX, Vclip.mc.player.posY + this.height.getValue().doubleValue(), Vclip.mc.player.posZ);
            }
            if (this.autoDisable.getValue()) {
                this.setToggled(false);
            }
        }
        else {
            Vclip.mc.player.move(MoverType.PLAYER, 0.0, (double)this.height.getValue().intValue(), 0.0);
            Main.getRotationManager().set(Vclip.mc.player.rotationYaw, Vclip.mc.player.rotationPitch, false);
            if (this.autoDisable.getValue()) {
                this.setToggled(false);
            }
        }
    }
}
