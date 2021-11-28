//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "PingBypass", desc = "Fake module, say fake commands when you join in to the server", cat = Module.Category.MISC)
public class PingBypass extends Module
{
    String[] message;
    boolean check;
    
    public PingBypass() {
        this.message = new String[] { "@Moneymod.AutoCrystal pigPredict value=true", "@Moneymod.AutoCrystal placeRange value=6,1,6", "@Moneymod.AutoCrystal breakRange value=6,1,6", "@Moneymod.AutoCrystal placeCrystal value=true", "@Moneymod.AutoCrystal breakCrystal value=true", "@Moneymod.AutoCrystal silentSwitch value=true", "@Moneymod.AutoCrystal yawStep value=false", "@Moneymod.AutoCrystal mainHand value=true", "@Moneymod.AutoCrystal debug value=false", "@Moneymod.AntiSurround delay value=26,1,60", "@Moneymod.AntiSurround silentSwitch value=true", "@Moneymod.AntiSurround instantAnvil value=true", "@Moneymod.FeetPlace mode value=Mode(`Instant`)", "@Moneymod.FeetPlace delay value=30,1,120" };
        this.check = false;
    }
    
    public void onEnable() {
        this.check = false;
    }
    
    @Override
    public void onTick() {
        if (this.check) {
            final String text = this.message[PingBypass.random.nextInt(this.message.length)];
            PingBypass.mc.player.sendChatMessage(text);
            ChatUtil.sendMessage(text);
            this.check = false;
        }
    }
    
    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.check = true;
    }
}
