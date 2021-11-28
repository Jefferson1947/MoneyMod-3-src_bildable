//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.client;

import net.minecraft.client.gui.GuiScreen;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "IRC-Gui", cat = Module.Category.CLIENT, key = 199)
public class IRCGui extends Module
{
    public final Setting<Boolean> blur;
    
    public IRCGui() {
        this.blur = this.register("Blur", false);
    }
    
    @Override
    protected void onEnable() {
        if (IRCGui.mc.currentScreen != Main.getIrcScreen()) {
            IRCGui.mc.displayGuiScreen((GuiScreen)Main.getIrcScreen());
        }
        this.setToggled(false);
    }
}
