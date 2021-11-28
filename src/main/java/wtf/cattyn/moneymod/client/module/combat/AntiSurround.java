//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.init.Items;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumHand;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.item.ItemEndCrystal;
import java.util.Comparator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3i;
import wtf.cattyn.moneymod.util.impl.BlockUtil;
import wtf.cattyn.moneymod.util.impl.EntityUtil;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.impl.render.Renderer3D;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import wtf.cattyn.moneymod.util.impl.Timer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AntiSurround", desc = "Replaces target's feetblock with anvil", cat = Module.Category.COMBAT)
public class AntiSurround extends Module
{
    private final Setting<Number> range;
    private final Setting<Number> delay;
    private final Setting<Boolean> silentSwitch;
    private final Setting<Boolean> protect;
    public final Setting<Color> color;
    private HashMap<BlockPos, Vec3d> lole;
    private Timer timer;
    private Entity crystal;
    private EntityPlayer target;
    private BlockPos targetBlock;
    boolean digging;
    int step;
    int action;
    
    public AntiSurround() {
        this.range = this.register("Range", 4.5, 0.0, 7.0, 0.1);
        this.delay = this.register("Delay", 50.0, 0.0, 300.0, 1.0);
        this.silentSwitch = this.register("SilentSwitch", false);
        this.protect = this.register("Protect", true);
        this.color = this.register("Color", new Color(255, 255, 255), false);
        this.lole = new HashMap<BlockPos, Vec3d>();
        this.timer = new Timer();
        this.action = 0;
    }
    
    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.setToggled(false);
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.targetBlock != null) {
            Renderer3D.drawBoxESP(this.targetBlock, this.color.getValue(), 1.0f, true, true, this.color.getValue().getAlpha(), this.color.getValue().getAlpha(), 1.0f);
        }
    }
    
    public void onDisable() {
        if (this.protect.getValue() && this.action == 2) {
            Main.getModuleManager().get(AutoCrystal.class).setToggled(true);
            this.action = 0;
        }
    }
    
    @Override
    protected void onEnable() {
        this.step = 0;
        this.crystal = null;
        this.target = null;
        this.targetBlock = null;
        this.digging = false;
        this.lole.clear();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            if (!this.protect.getValue()) {
                return;
            }
            this.setToggled(false);
            Main.getModuleManager().get(AutoCrystal.class).setToggled(false);
        }
        if (this.protect.getValue() ) {
            return;
        }
        if (Main.getModuleManager().get(AutoCrystal.class).isToggled()) {
            Main.getModuleManager().get(AutoCrystal.class).setToggled(false);
            this.action = 2;
        }
        this.target = EntityUtil.getTarget(this.range.getValue().floatValue());
        if (this.target != null) {
            this.info = this.target.getName();
            if (this.targetBlock == null) {
                final BlockPos[] lambdaPos = new BlockPos[1];
                final BlockPos[] crystalPos = new BlockPos[1];
                this.targetBlock = BlockUtil.getLevels(0).stream().map(vec -> {
                    lambdaPos[0] = new BlockPos(this.target.getPositionVector()).add(vec.x, vec.y, vec.z);
                    this.lole.put(lambdaPos[0], vec);
                    return lambdaPos[0];
                }).filter(bp -> {
                    crystalPos[0] = new BlockPos((Vec3i)bp).add(this.lole.get(bp).x, -1.0, this.lole.get(bp).z);
                    return AntiSurround.mc.world.getBlockState(bp).getBlock() != Blocks.BEDROCK && BlockUtil.canPlaceCrystal(crystalPos[0], true, false);
                }).min(Comparator.comparing(bp -> AntiSurround.mc.player.getDistanceSq(bp))).orElse(null);
                return;
            }
            final IBlockState state = AntiSurround.mc.world.getBlockState(this.targetBlock);
            final int old = AntiSurround.mc.player.inventory.currentItem;
            if (this.step == 0) {
                if (BlockUtil.canPlaceCrystal(new BlockPos((Vec3i)this.targetBlock).add(this.lole.get(this.targetBlock).x, -1.0, this.lole.get(this.targetBlock).z), true) && ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(ItemEndCrystal.class), false) != -1) {
                    BlockUtil.placeCrystalOnBlock(new BlockPos((Vec3i)this.targetBlock).add(this.lole.get(this.targetBlock).x, -1.0, this.lole.get(this.targetBlock).z), EnumHand.MAIN_HAND, true);
                    this.crystal = (Entity)AntiSurround.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).min(Comparator.comparing(c -> AntiSurround.mc.player.getDistance(c))).orElse(null);
                    ItemUtil.switchToHotbarSlot(old, false);
                }
                if (!(state.getBlock() instanceof BlockAir)) {
                    if ((AntiSurround.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE) || ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(ItemPickaxe.class), false) != -1) && !this.digging) {
                        this.digging = true;
                        AntiSurround.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        AntiSurround.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.targetBlock, EnumFacing.DOWN));
                        AntiSurround.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.targetBlock, EnumFacing.DOWN));
                    }
                }
                else {
                    this.digging = false;
                    this.step = 1;
                }
            }
            else if (this.step == 1 && this.timer.passed(this.delay.getValue().intValue())) {
                if (this.crystal != null) {
                    AntiSurround.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(this.crystal));
                }
                if (ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(Blocks.ANVIL), false) != -1) {
                    BlockUtil.placeBlock(this.targetBlock);
                    ItemUtil.switchToHotbarSlot(ItemUtil.getItemSlot(Items.DIAMOND_PICKAXE), false);
                    this.step = 0;
                }
                this.timer.reset();
            }
            if (this.silentSwitch.getValue()) {
                ItemUtil.switchToHotbarSlot(old, false);
            }
        }
        else {
            this.info = "";
        }
    }
}
