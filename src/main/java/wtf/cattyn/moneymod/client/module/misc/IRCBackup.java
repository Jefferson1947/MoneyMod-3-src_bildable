//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.combat.AutoCrystal;
import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "IRCBackup", cat = Module.Category.MISC)
public class IRCBackup extends Module
{
    public final Setting<String> mode;
    String text;
    
    public IRCBackup() {
        this.mode = this.register("Mode", "OnlyCoords", Arrays.asList("OnlyCoords", "Backup", "TargetCoords"));
        this.text = "";
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        final String s = this.mode.getValue();
        switch (s) {
            case "OnlyCoords": {
                this.text = String.format("%s %s %s - my coords", (int)IRCBackup.mc.player.posX, (int)IRCBackup.mc.player.posY, (int)IRCBackup.mc.player.posZ);
                break;
            }
            case "Backup": {
                this.text = String.format("I need backup! My coords: %s %s %s", (int)IRCBackup.mc.player.posX, (int)IRCBackup.mc.player.posY, (int)IRCBackup.mc.player.posZ);
                break;
            }
            case "TargetCoords": {
                this.text = String.format("i need help, XYZ: %s %s %s - Target=[%s]", (int)IRCBackup.mc.player.posX, (int)IRCBackup.mc.player.posY, (int)IRCBackup.mc.player.posZ, (AutoCrystal.getInstance().currentTarget == null) ? "none" : AutoCrystal.getInstance().currentTarget);
                break;
            }
        }
        Main.getCapeThread().sendChatMessage(this.text);
        this.setToggled(false);
    }
}
