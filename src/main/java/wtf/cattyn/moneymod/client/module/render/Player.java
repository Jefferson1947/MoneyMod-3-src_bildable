//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Player", desc = "Spawns a fake player", cat = Module.Category.RENDER)
public class Player extends Module
{
    private EntityOtherPlayerMP player;
    
    @SubscribeEvent
    public void onEvent(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.setToggled(false);
    }
    
    public void onEnable() {
        if (Player.mc.player == null) {
            this.disable();
            return;
        }
        if (this.player == null) {
            (this.player = new EntityOtherPlayerMP((World)Player.mc.world, new GameProfile(UUID.randomUUID(), "Player"))).copyLocationAndAnglesFrom((Entity)Player.mc.player);
            this.player.inventory.copyInventory(Player.mc.player.inventory);
        }
        Player.mc.world.spawnEntity((Entity)this.player);
    }
    
    public void onDisable() {
        if (this.player != null) {
            Player.mc.world.removeEntity((Entity)this.player);
            this.player = null;
        }
    }
}
