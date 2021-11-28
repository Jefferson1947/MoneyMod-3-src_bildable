//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import wtf.cattyn.moneymod.mixin.ducks.AccessorEntity;
import net.minecraft.network.play.client.CPacketUseEntity;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Criticals", desc = "Always do critical hits", cat = Module.Category.COMBAT)
public class Criticals extends Module
{
    public final Setting<String> mode;
    public final Setting<Boolean> strict;
    
    public Criticals() {
        this.mode = this.register("Mode", "Packet", Arrays.asList("Packet", "Jump", "Motion"));
        this.strict = this.register("Strict", false);
    }
    
    @Override
    public void onTick() {
        this.info = this.mode.getValue();
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            final CPacketUseEntity packet = event.getPacket();
            if (packet.getAction().equals((Object)CPacketUseEntity.Action.ATTACK) && Criticals.mc.player.onGround && !Criticals.mc.player.isInWater() && !Criticals.mc.player.isInLava() && !((AccessorEntity)Criticals.mc.player).isInWeb()) {
                final Entity entity = packet.getEntityFromWorld((World)Criticals.mc.world);
                if (entity != null && entity instanceof EntityLivingBase) {
                    final String mode = this.mode.getValue();
                    if (mode.equals("Packet")) {
                        final double x = Criticals.mc.player.posX;
                        final double y = Criticals.mc.player.posY;
                        final double z = Criticals.mc.player.posZ;
                        if (this.strict.getValue()) {
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.07, z, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.08, z, false));
                            Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, false));
                        }
                        Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.05, z, false));
                        Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, false));
                        Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.012, z, false));
                        Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, false));
                    }
                    else if (mode.equals("Jump")) {
                        Criticals.mc.player.jump();
                    }
                    else if (mode.equals("Motion")) {
                        Criticals.mc.player.jump();
                        final EntityPlayerSP player = Criticals.mc.player;
                        player.motionY -= 0.1;
                    }
                    Criticals.mc.player.onCriticalHit(entity);
                }
            }
        }
    }
}
