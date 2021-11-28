//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import wtf.cattyn.moneymod.api.event.TotemPopEvent;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "PopNotify", desc = "Notifies you when somebody pops a totem", cat = Module.Category.MISC)
public class PopNotify extends Module
{
    private final Setting<Boolean> self;
    
    public PopNotify() {
        this.self = this.register("notSelf", true);
    }
    
    @SubscribeEvent
    public void pop_it(final TotemPopEvent event) {
        if (this.self.getValue() && PopNotify.mc.getSession().getUsername().equalsIgnoreCase(event.getEntityPlayerSP().getName())) {
            return;
        }
        ChatUtil.sendMessageId(String.format("%s has popped!", event.getEntityPlayerSP().getName()), true, 843);
    }
}
