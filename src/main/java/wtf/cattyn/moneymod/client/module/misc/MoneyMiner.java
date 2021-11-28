// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import wtf.cattyn.moneymod.client.module.client.Global;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "MoneyMiner", desc = "Ez Money Using Ur Pc", cat = Module.Category.MISC)
public class MoneyMiner extends Module
{
    int money;
    int delay;
    
    public void onEnable() {
        this.money = 0;
        this.delay = 0;
    }
    
    public void onDisable() {
        ChatUtil.sendMessageId(ChatFormatting.RED + "[moneyminer]" + ChatFormatting.YELLOW + " finish balance " + ChatFormatting.GREEN + this.money + "$", true, 777);
    }
    
    @Override
    public void onTick() {
        if (this.delay == 0) {
            ++this.delay;
        }
        if (this.delay >= Global.random.nextInt(120)) {
            this.money += Global.random.nextInt(8);
            ChatUtil.sendMessageId(ChatFormatting.RED + "[moneyminer]" + ChatFormatting.YELLOW + " you mined " + ChatFormatting.GREEN + this.money + "$", true, 777);
            this.delay = 0;
        }
    }
}
