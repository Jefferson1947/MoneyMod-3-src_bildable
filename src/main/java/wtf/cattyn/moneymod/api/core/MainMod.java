// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.core;

import wtf.cattyn.moneymod.Main;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(name = "moneymod+3", modid = "moneymod+3", version = "1.0.0")
public class MainMod
{
    @Mod.EventHandler
    public void onInitialize(final FMLInitializationEvent e) {
        Main.init();
    }
}
