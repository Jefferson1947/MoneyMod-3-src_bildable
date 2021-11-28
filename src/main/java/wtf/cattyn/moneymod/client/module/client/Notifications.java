// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.api.event.ModuleEvent;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.api.setting.Setting;
import java.util.ArrayList;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Notifications", desc = "Notifies you when you enable or disable a module", cat = Module.Category.CLIENT)
public class Notifications extends Module
{
    private final ArrayList<Module> blacklist;
    private final Setting<Boolean> module;
    
    public Notifications() {
        this.blacklist = new ArrayList<Module>();
        this.module = this.register("Module", true);
    }
    
    @Override
    protected void onEnable() {
        this.blacklist.clear();
        this.blacklist.add(Main.getModuleManager().get(ClickGui.class));
        this.blacklist.add(Main.getModuleManager().get(Global.class));
    }
    
    @SubscribeEvent
    public void onModuleChange(final ModuleEvent event) {
        if (!this.module.getValue()) {
            return;
        }
        if (event.getType() == ModuleEvent.Type.ENABLE && !this.blacklist.contains(event.getModule())) {
            ChatUtil.sendMessage("" + ChatFormatting.WHITE + ChatFormatting.BOLD + event.getModule().getName() + " : " + ChatFormatting.GREEN + "Enabled", true);
        }
        if (event.getType() == ModuleEvent.Type.DISABLE && !this.blacklist.contains(event.getModule())) {
            ChatUtil.sendMessage("" + ChatFormatting.WHITE + ChatFormatting.BOLD + event.getModule().getName() + " : " + ChatFormatting.RED + "Disabled", true);
        }
    }
}
