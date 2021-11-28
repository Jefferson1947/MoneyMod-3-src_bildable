//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.dev;

import net.minecraft.item.ItemChorusFruit;
import wtf.cattyn.moneymod.util.impl.render.Renderer3D;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraft.network.Packet;
import java.util.Arrays;
import java.util.LinkedList;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import wtf.cattyn.moneymod.util.impl.Timer;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.Queue;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "ChorusHelper", cat = Module.Category.CLIENT)
public class ChorusLag extends Module
{
    int delay;
    int delay2;
    boolean ateChorus;
    boolean hackPacket;
    boolean posTp;
    boolean renderCheck;
    double posX;
    double posY;
    double posZ;
    Queue<CPacketPlayer> packets;
    Queue<CPacketConfirmTeleport> packetss;
    private final Setting<String> mode;
    private final Setting<Number> sDelay;
    private final Setting<Boolean> chorusViewer;
    private final Setting<Number> timeOther;
    private final Setting<Number> timeSelf;
    public final Setting<Color> colorFinaly;
    public final Setting<Color> colorSelf;
    private final Timer timer;
    private final Timer t;
    private BlockPos chorusPos;
    SPacketPlayerPosLook renderPos;
    BlockPos pos;
    
    public ChorusLag() {
        this.delay = 0;
        this.delay2 = 0;
        this.ateChorus = false;
        this.hackPacket = false;
        this.posTp = false;
        this.renderCheck = false;
        this.packets = new LinkedList<CPacketPlayer>();
        this.packetss = new LinkedList<CPacketConfirmTeleport>();
        this.mode = this.register("Mode", "ShiftTp", Arrays.asList("ShiftTp", "Delay", "None"));
        this.sDelay = this.register("Delay", 4.0, 0.0, 30.0, 1.0);
        this.chorusViewer = this.register("Viewer", false);
        this.timeOther = this.register("time", 4.0, 0.0, 300.0, 1.0);
        this.timeSelf = this.register("timeSelf", 4.0, 0.0, 300.0, 1.0);
        this.colorFinaly = this.register("ColorsOther", new Color(158, 21, 255, 82), false);
        this.colorSelf = this.register("ColorsSelf", new Color(158, 21, 255, 82), false);
        this.timer = new Timer();
        this.t = new Timer();
    }
    
    public void onEnable() {
        this.ateChorus = false;
        this.hackPacket = false;
        this.posTp = false;
    }
    
    @Override
    public void onTick() {
        if (!this.renderCheck) {
            this.renderPos = null;
            this.t.reset();
        }
        if (this.renderCheck && this.t.passed(this.timeSelf.getValue().intValue() * 1000)) {
            this.renderPos = null;
            this.t.reset();
            this.renderCheck = false;
        }
        if (!this.mode.getValue().equalsIgnoreCase("None")) {
            if (this.ateChorus) {
                if (this.mode.getValue().equalsIgnoreCase("Delay")) {
                    ++this.delay;
                    ++this.delay2;
                }
                if (!ChorusLag.mc.player.getPosition().equals((Object)new BlockPos(this.posX, this.posY, this.posZ)) && !this.posTp && ChorusLag.mc.player.getDistance(this.posX, this.posY, this.posZ) > 1.0) {
                    ChorusLag.mc.player.setPosition(this.posX, this.posY, this.posZ);
                    this.posTp = true;
                }
            }
            if (this.mode.getValue().equalsIgnoreCase("Delay") && this.ateChorus && this.delay2 > this.sDelay.getValue().intValue()) {
                this.doShit();
            }
            if (this.mode.getValue().equalsIgnoreCase("ShiftTp") && this.ateChorus && ChorusLag.mc.player.isSneaking()) {
                this.doShit();
            }
        }
    }
    
    public void doShit() {
        this.ateChorus = false;
        this.delay = 0;
        this.hackPacket = true;
        this.delay2 = 0;
        this.sendPackets();
    }
    
    public void sendPackets() {
        while (!this.packets.isEmpty()) {
            ChorusLag.mc.player.connection.sendPacket((Packet)this.packets.poll());
        }
        while (!this.packetss.isEmpty()) {
            ChorusLag.mc.player.connection.sendPacket((Packet)this.packetss.poll());
        }
        this.hackPacket = false;
        this.delay2 = 0;
        this.ateChorus = false;
    }
    
    @SubscribeEvent
    public void finishEating(final LivingEntityUseItemEvent.Finish event) {
        if ((this.mode.getValue().equalsIgnoreCase("Delay") || this.mode.getValue().equalsIgnoreCase("ShiftTp")) && event.getEntity() == ChorusLag.mc.player && event.getResultStack().getItem().equals(Items.CHORUS_FRUIT)) {
            this.posX = ChorusLag.mc.player.posX;
            this.posY = ChorusLag.mc.player.posY;
            this.posZ = ChorusLag.mc.player.posZ;
            this.posTp = false;
            this.ateChorus = true;
            this.renderPos = null;
            this.t.reset();
            this.pos = null;
            this.renderCheck = true;
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.mode.getValue().equalsIgnoreCase("Delay") || this.mode.getValue().equalsIgnoreCase("ShiftTp")) {
            if (event.getPacket() instanceof CPacketConfirmTeleport && this.ateChorus && this.delay2 < this.sDelay.getValue().intValue()) {
                this.packetss.add(event.getPacket());
                event.setCanceled(true);
            }
            if (event.getPacket() instanceof CPacketPlayer && this.ateChorus && this.delay2 < this.sDelay.getValue().intValue()) {
                this.packets.add(event.getPacket());
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            this.renderPos = event.getPacket();
        }
        if (event.getPacket() instanceof SPacketSoundEffect && this.chorusViewer.getValue()) {
            final SPacketSoundEffect packet = event.getPacket();
            if (packet.getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT) {
                this.chorusPos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
            }
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.chorusPos != null) {
            if (this.timer.passed(this.timeOther.getValue().intValue() * 1000)) {
                this.chorusPos = null;
                this.renderPos = null;
                this.timer.reset();
                return;
            }
            Renderer3D.drawBoxESP(this.chorusPos, this.colorFinaly.getValue(), 0.2f, true, true, this.colorFinaly.getValue().getAlpha(), this.colorFinaly.getValue().getAlpha(), -0.5f);
        }
        if (this.renderPos != null && ChorusLag.mc.player.getHeldItemMainhand().getItem() instanceof ItemChorusFruit) {
            this.pos = new BlockPos(this.renderPos.getX(), this.renderPos.getY(), this.renderPos.getZ());
            if (ChorusLag.mc.player.getPosition().equals((Object)new BlockPos(this.posX, this.posY, this.posZ))) {
                return;
            }
            Renderer3D.drawBoxESP(this.pos, this.colorSelf.getValue(), 0.2f, true, true, this.colorSelf.getValue().getAlpha(), this.colorSelf.getValue().getAlpha(), 2.0f);
        }
    }
}
