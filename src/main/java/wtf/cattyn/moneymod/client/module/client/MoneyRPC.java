// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.client;

import wtf.cattyn.moneymod.MoneyCord;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "MoneyRPC", desc = "Discord Rich Presence", cat = Module.Category.CLIENT)
public class MoneyRPC extends Module
{
    public void onEnable() {
        MoneyCord.start();
    }
    
    public void onDisable() {
        MoneyCord.stop();
    }
}
