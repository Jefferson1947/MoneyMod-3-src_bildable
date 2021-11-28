//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import wtf.cattyn.moneymod.mixin.mixins.AccessorCPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.block.BlockObsidian;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import wtf.cattyn.moneymod.util.impl.BlockUtil;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import wtf.cattyn.moneymod.util.impl.render.Renderer3D;
import java.awt.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import wtf.cattyn.moneymod.util.impl.Timer;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "SelfPlace", cat = Module.Category.COMBAT)
public class SelfPlace extends Module
{
    private final Setting<Number> bpt;
    private final Setting<Number> delay;
    private final Setting<Boolean> autoDisable;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> render;
    final Timer timer;
    boolean didPlace;
    boolean rotating;
    int placed;
    float yaw;
    float pitch;
    
    public SelfPlace() {
        this.bpt = this.register("BPS", 8.0, 1.0, 20.0, 1.0);
        this.delay = this.register("Delay", 26.0, 0.0, 250.0, 1.0);
        this.autoDisable = this.register("Disable", false);
        this.rotate = this.register("Rotate", false);
        this.render = this.register("Render", false);
        this.timer = new Timer();
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    @SubscribeEvent
    public void onRender3D(final RenderWorldLastEvent event) {
        if (this.render.getValue()) {
            for (final BlockPos bp : this.getBlocks((Entity)SelfPlace.mc.player)) {
                Renderer3D.drawBoxESP(bp, Color.WHITE, 1.0f, true, true, 60, 255, 1.0f);
            }
        }
    }
    
    @Override
    protected void onEnable() {
        this.placed = 0;
        this.didPlace = false;
        this.timer.reset();
        this.rotating = false;
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (!this.timer.passed(this.delay.getValue().intValue()) && this.didPlace) {
            return;
        }
        if (this.getBlocks((Entity)SelfPlace.mc.player).size() == 0) {
            if (this.autoDisable.getValue()) {
                this.setToggled(false);
            }
            this.rotating = false;
            return;
        }
        this.placeBlocks(this.getBlocks((Entity)SelfPlace.mc.player));
        this.placed = 0;
        this.timer.reset();
    }
    
    List<BlockPos> getBlocks(final Entity target) {
        final AtomicBoolean add = new AtomicBoolean(false);

        return null;
    }
    
    void placeBlocks(final List<BlockPos> blockPos) {
        this.rotating = true;
        for (final BlockPos bp : blockPos) {
            if (this.placed >= this.bpt.getValue().intValue()) {
                return;
            }
            final int old = SelfPlace.mc.player.inventory.currentItem;
            if (ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(BlockObsidian.class), false) == -1) {
                return;
            }
            switch (BlockUtil.isPlaceable(bp)) {
                case 0: {
                    this.yaw = Main.getRotationManager().look(bp, false, false)[0];
                    this.pitch = Main.getRotationManager().look(bp, false, false)[1];
                    BlockUtil.placeBlock(bp);
                    this.didPlace = true;
                    ++this.placed;
                    break;
                }
            }
            ItemUtil.switchToHotbarSlot(old, false);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.rotating && this.rotate.getValue()) {

        }
    }
}
