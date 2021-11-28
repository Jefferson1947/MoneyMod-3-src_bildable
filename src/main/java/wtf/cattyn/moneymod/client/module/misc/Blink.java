//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraft.entity.Entity;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Arrays;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import java.util.Queue;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Blink", cat = Module.Category.MISC)
public class Blink extends Module
{
    public final Setting<String> logic;
    public final Setting<Number> ticks;
    public final Setting<Boolean> render;
    Queue<Packet<?>> packets;
    private EntityOtherPlayerMP renders;
    int tick;
    int flTick;
    
    public Blink() {
        this.logic = this.register("Logic", "Tick", Arrays.asList("Tick", "Manual", "FakeLag"));
        this.ticks = this.register("Tick", 0.0, 1.0, 60.0, 1.0);
        this.render = this.register("Render", true);
        this.packets = new ConcurrentLinkedQueue<Packet<?>>();
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            this.setToggled(false);
            return;
        }
        this.tick = 0;
        this.flTick = 0;
        if (this.logic.getValue().equalsIgnoreCase("Manual") || this.logic.getValue().equalsIgnoreCase("Tick")) {
            this.doRender();
        }
    }
    
    public void onDisable() {
        if (this.nullCheck()) {
            return;
        }
        Blink.mc.world.removeEntity((Entity)this.renders);
        while (!this.packets.isEmpty()) {
            Blink.mc.getConnection().sendPacket((Packet)this.packets.poll());
        }
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        ++this.tick;
        ++this.flTick;
        if (this.logic.getValue().equalsIgnoreCase("FakeLag")) {
            if (this.flTick == 1) {
                this.doRender();
            }
            if (this.flTick >= this.ticks.getValue().intValue()) {
                Blink.mc.world.removeEntity((Entity)this.renders);
            }
            if (this.flTick >= this.ticks.getValue().intValue() + 4) {
                this.tick = 0;
                this.flTick = 0;
            }
        }
        if (this.tick >= this.ticks.getValue().intValue() && this.logic.getValue().equalsIgnoreCase("Tick")) {
            this.setNull();
            this.setToggled(false);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.flTick >= this.ticks.getValue().intValue() && this.logic.getValue().equalsIgnoreCase("Tick")) {
            return;
        }
        if (event.getPacket() instanceof CPacketPlayer) {
            this.packets.add(event.getPacket());
            event.setCanceled(true);
        }
    }
    
    public void doRender() {
        if (this.render.getValue()) {
            (this.renders = new EntityOtherPlayerMP((World)Blink.mc.world, Blink.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)Blink.mc.player);
            this.renders.rotationYawHead = Blink.mc.player.rotationYawHead;
            this.renders.setHealth(Blink.mc.player.getHealth() + Blink.mc.player.getAbsorptionAmount());
            this.renders.setSneaking(Blink.mc.player.isSneaking());
            this.renders.inventory.copyInventory(Blink.mc.player.inventory);
            Blink.mc.world.addEntityToWorld(-100, (Entity)this.renders);
        }
    }
    
    public void setNull() {
        this.tick = 0;
        this.flTick = 0;
    }
}
