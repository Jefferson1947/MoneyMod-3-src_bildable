//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import wtf.cattyn.moneymod.util.impl.EntityUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.init.Items;
import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AutoLog", cat = Module.Category.MISC)
public class AutoLog extends Module
{
    private final Setting<String> type;
    private final Setting<Number> health;
    
    public AutoLog() {
        this.type = this.register("Mode", "Normal", Arrays.asList("Bsod", "Normal"));
        this.health = this.register("Health", 8.0, 1.0, 36.0, 1.0);
    }
    
    @Override
    public void onTick() {
        final float h = AutoLog.mc.player.getHealth();
        if (h <= this.health.getValue().intValue() && AutoLog.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
            if (this.type.getValue().equalsIgnoreCase("Normal")) {
                AutoLog.mc.getConnection().handleDisconnect(new SPacketDisconnect((ITextComponent)new TextComponentString("retard. " + h + "hp")));
            }
            if (this.type.getValue().equalsIgnoreCase("Bsod")) {
                EntityUtil.invokeBsod();
            }
        }
    }
}
