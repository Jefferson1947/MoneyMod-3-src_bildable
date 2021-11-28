//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import java.util.Iterator;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.block.BlockObsidian;
import java.util.List;
import wtf.cattyn.moneymod.util.impl.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.client.module.movement.Warp;
import wtf.cattyn.moneymod.client.module.movement.Step;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.api.event.StepEvent;
import java.util.Arrays;
import wtf.cattyn.moneymod.util.impl.Timer;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "FeetPlace", cat = Module.Category.COMBAT, exception = true)
public class FeetPlace extends Module
{
    private static FeetPlace INSTANCE;
    private final Setting<String> update;
    private final Setting<Number> delay;
    private final Setting<Number> bps;
    private final Setting<Boolean> retry;
    private final Setting<Boolean> help;
    private final Setting<Boolean> jump;
    private final Setting<Boolean> autoDisable;
    private final Timer timer;
    private int placed;
    public boolean didPlace;
    private double y;
    
    public FeetPlace() {
        this.update = this.register("Update", "Tick", Arrays.asList("Tick", "Artificial"));
        this.delay = this.register("Delay", 26.0, 0.0, 250.0, 1.0);
        this.bps = this.register("BPS", 12.0, 1.0, 20.0, 1.0);
        this.retry = this.register("Retry", true);
        this.help = this.register("Help", true);
        this.jump = this.register("JumpDisable", true);
        this.autoDisable = this.register("Disable", false);
        this.timer = new Timer();
        FeetPlace.INSTANCE = this;
    }
    
    public static FeetPlace getInstance() {
        return FeetPlace.INSTANCE;
    }
    
    @Override
    protected void onEnable() {
        this.placed = 0;
        this.y = FeetPlace.mc.player.posY;
        this.didPlace = false;
        this.timer.reset();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (this.update.getValue().equalsIgnoreCase("Tick")) {
            this.doFeetPlace();
        }
    }
    
    @Override
    public void onArtificialTick() {
        if (this.update.getValue().equalsIgnoreCase("Artificial")) {
            this.doFeetPlace();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onStep(final StepEvent event) {
        if (this.jump.getValue() && event.getStage() == 0 && (Main.getModuleManager().get(Step.class).isToggled() || Main.getModuleManager().get(Warp.class).isToggled())) {
            this.setToggled(false);
        }
    }
    
    public void doFeetPlace() {
        if (!this.timer.passed(this.delay.getValue().intValue()) && this.didPlace) {
            return;
        }
        if (FeetPlace.mc.player.posY != this.y && this.jump.getValue()) {
            this.setToggled(false);
        }
        final int offset = (FeetPlace.mc.world.getBlockState(new BlockPos(FeetPlace.mc.player.getPositionVector())).getBlock() == Blocks.ENDER_CHEST && FeetPlace.mc.player.posY - Math.floor(FeetPlace.mc.player.posY) > 0.5) ? 1 : 0;
        if (BlockUtil.getUnsafePositions(FeetPlace.mc.player.getPositionVector(), offset).size() == 0) {
            if (this.autoDisable.getValue()) {
                this.setToggled(false);
            }
            return;
        }
        if (this.help.getValue()) {
            this.placeBlocks(BlockUtil.getUnsafePositions(FeetPlace.mc.player.getPositionVector(), offset - 1));
        }
        this.placeBlocks(BlockUtil.getUnsafePositions(FeetPlace.mc.player.getPositionVector(), offset));
        this.placed = 0;
        this.timer.reset();
    }
    
    private void placeBlocks(final List<BlockPos> blocks) {
        for (final BlockPos bp : blocks) {
            if (this.placed >= this.bps.getValue().intValue()) {
                return;
            }
            final int old = FeetPlace.mc.player.inventory.currentItem;
            if (ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(BlockObsidian.class), false) == -1) {
                return;
            }
            switch (BlockUtil.isPlaceable(bp)) {
                case 0: {
                    BlockUtil.placeBlock(bp);
                    this.didPlace = true;
                    ++this.placed;
                    break;
                }
                case -1: {
                    if (this.retry.getValue()) {
                        BlockUtil.placeBlock(bp);
                        ++this.placed;
                        break;
                    }
                    break;
                }
            }
            if (FeetPlace.mc.player.inventory.currentItem == old) {
                continue;
            }
            ItemUtil.switchToHotbarSlot(old, false);
        }
    }
}
