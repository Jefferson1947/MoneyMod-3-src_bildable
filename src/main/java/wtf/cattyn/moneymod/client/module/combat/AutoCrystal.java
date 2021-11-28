//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.api.managment.RotationManager;
import net.minecraft.util.math.Vec3d;
import wtf.cattyn.moneymod.mixin.mixins.AccessorCPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.List;
import java.util.function.Consumer;
import wtf.cattyn.moneymod.util.impl.render.Renderer3D;
import java.util.ArrayList;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import wtf.cattyn.moneymod.client.module.misc.AutoEz;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.mixin.mixins.AccessorCPacketUseEntity;
import net.minecraft.network.play.server.SPacketSpawnObject;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.init.Items;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.EntityLivingBase;
import wtf.cattyn.moneymod.util.impl.EntityUtil;
import wtf.cattyn.moneymod.util.impl.BlockUtil;
import java.util.HashSet;
import java.util.Arrays;
import wtf.cattyn.moneymod.util.impl.Timer;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.util.math.BlockPos;
import java.util.Set;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "SCAR-20", desc = "Places and breaks ender crystals", cat = Module.Category.COMBAT)
public class AutoCrystal extends Module
{
    private static AutoCrystal INSTANCE;
    public final Setting<Boolean> placeCrystal;
    public final Setting<Boolean> breakCrystal;
    public final Setting<String> logic;
    public final Setting<Number> range;
    public final Setting<Number> placeRange;
    public final Setting<Number> placeDelay;
    public final Setting<Number> breakRange;
    public final Setting<Number> breakWallRange;
    public final Setting<Number> breakDelay;
    public final Setting<Number> predictDelay;
    public final Setting<Number> facePlaceHp;
    public final Setting<Number> minDamage;
    public final Setting<Number> armorScale;
    public final Setting<Number> tick;
    public final Setting<Boolean> rotate;
    public final Setting<String> switchMode;
    public final Setting<Boolean> antiWeakness;
    public final Setting<Boolean> instant;
    public final Setting<Boolean> secondCheck;
    public final Setting<Color> color;
    public final Setting<Number> boxHeight;
    public final Setting<Number> linewidht;
    public final Setting<Boolean> outline;
    public final Setting<Boolean> box;
    public final Setting<Boolean> upRender;
    private final Set<BlockPos> placeSet;
    private final ConcurrentSet<RenderPos> renderSet;
    private BlockPos currentBlock;
    public EntityPlayer currentTarget;
    private boolean offhand;
    private boolean rotating;
    private boolean lowArmor;
    private double currentDamage;
    private int ticks;
    private float yaw;
    private float pitch;
    private final Timer breakTimer;
    private final Timer placeTimer;
    private final Timer predictTimer;
    
    public AutoCrystal() {
        this.placeCrystal = this.register("Place", true);
        this.breakCrystal = this.register("Break", true);
        this.logic = this.register("Logic", "BreakPlace", Arrays.asList("BreakPlace", "PlaceBreak"));
        this.range = this.register("Range", 10.0, 1.0, 20.0, 1.0);
        this.placeRange = this.register("PlaceRange", 5.0, 1.0, 200.0, 0.1);
        this.placeDelay = this.register("PlaceDelay", 0.0, 0.0, 500.0, 10.0);
        this.breakRange = this.register("BreakRange", 5.0, 1.0, 6.0, 0.1);
        this.breakWallRange = this.register("BreakWallRange", 5.0, 1.0, 6.0, 0.1);
        this.breakDelay = this.register("BreakDelay", 10.0, 0.0, 500.0, 10.0);
        this.predictDelay = this.register("PredictDelay", 1.0, 0.0, 500.0, 10.0);
        this.facePlaceHp = this.register("FacePlaceHP", 5.0, 1.0, 36.0, 1.0);
        this.minDamage = this.register("MinDMG", 5.0, 1.0, 36.0, 1.0);
        this.armorScale = this.register("Armor Scale", 30.0, 1.0, 100.0, 1.0);
        this.tick = this.register("Ticks Existed", 5.0, 0.0, 20.0, 1.0);
        this.rotate = this.register("Rotate", false);
        this.switchMode = this.register("SwitchMode", "Silent", Arrays.asList("Auto", "Silent", "None"));
        this.antiWeakness = this.register("AntiWeakness", false);
        this.instant = this.register("PigPredict", true);
        this.secondCheck = this.register("SecondCheck", false);
        this.color = this.register("Color", new Color(255, 255, 255), false);
        this.boxHeight = this.register("BoxHeight", 1.0, -1.0, 1.0, 0.1);
        this.linewidht = this.register("LineWidht", 1.4, 0.0, 3.0, 0.1);
        this.outline = this.register("Outline", false);
        this.box = this.register("Box", false);
        this.upRender = this.register("UpRender", false);
        this.placeSet = new HashSet<BlockPos>();
        this.renderSet = (ConcurrentSet<RenderPos>)new ConcurrentSet();
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.breakTimer = new Timer();
        this.placeTimer = new Timer();
        this.predictTimer = new Timer();
        AutoCrystal.INSTANCE = this;
    }
    
    public static AutoCrystal getInstance() {
        return AutoCrystal.INSTANCE;
    }
    
    public void onEnable() {
        this.rotating = false;
        this.breakTimer.reset();
        this.placeTimer.reset();
        this.predictTimer.reset();
    }
    
    public void onDisable() {
        this.placeSet.clear();
        this.renderSet.clear();
        this.breakTimer.reset();
        this.placeTimer.reset();
        this.predictTimer.reset();
    }
    
    private void place() {
        EnumHand hand = null;
        BlockPos placePos = null;
        double maxDamage = 0.5;
        for (final BlockPos pos : BlockUtil.getSphere(this.placeRange.getValue().floatValue(), true)) {
            final double targetDamage;
            final double selfDamage;
            if (BlockUtil.canPlaceCrystal(pos, this.secondCheck.getValue()) && ((targetDamage = EntityUtil.calculate(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, (EntityLivingBase)this.currentTarget)) >= this.minDamage.getValue().intValue() || EntityUtil.getHealth((EntityLivingBase)this.currentTarget) <= this.facePlaceHp.getValue().intValue() || this.lowArmor) && (selfDamage = EntityUtil.calculate(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, (EntityLivingBase)AutoCrystal.mc.player)) + 2.0 < EntityUtil.getHealth((EntityLivingBase)AutoCrystal.mc.player) && selfDamage < targetDamage) {
                if (maxDamage > targetDamage) {
                    continue;
                }
                if (this.currentTarget.isDead) {
                    continue;
                }
                placePos = pos;
                maxDamage = targetDamage;
            }
        }
        if (AutoCrystal.mc.player.isPotionActive(MobEffects.WEAKNESS) && this.antiWeakness.getValue() && !AutoCrystal.mc.player.isPotionActive(MobEffects.STRENGTH)) {
            final int sword = ItemUtil.findHotbarBlock(ItemSword.class);
            if (sword == -1) {
                return;
            }
            ItemUtil.switchToHotbarSlot(sword, false);
        }
        if (this.switchMode.getValue().equalsIgnoreCase("None")) {
            if (!this.offhand && AutoCrystal.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
                return;
            }
        }
        else if (this.switchMode.getValue().equalsIgnoreCase("Auto")) {
            final int crystal = ItemUtil.findHotbarBlock(ItemEndCrystal.class);
            if (crystal == -1) {
                return;
            }
            ItemUtil.swapItemsOffhand(crystal);
        }
        else if (this.switchMode.getValue().equalsIgnoreCase("Silent") && this.checkCrystal() == -1) {
            return;
        }
        if (maxDamage != 0.5 && this.placeTimer.passed(this.placeDelay.getValue().intValue())) {
            final int old = AutoCrystal.mc.player.inventory.currentItem;
            if (!this.offhand && !this.switchMode.getValue().equalsIgnoreCase("Auto") && (AutoCrystal.mc.player.getHeldItemMainhand().getItem() != Items.GOLDEN_APPLE || !AutoCrystal.mc.player.isHandActive())) {
                ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(ItemEndCrystal.class), false);
            }
            if (AutoCrystal.mc.player.isHandActive()) {
                hand = AutoCrystal.mc.player.getActiveHand();
            }
            if (this.switchMode.getValue().equalsIgnoreCase("Silent")) {
                ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(ItemEndCrystal.class), false);
            }
            if (this.rotate.getValue()) {
                this.rotate(placePos);
            }
            if (placePos == null || (this.offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND) == null) {
                return;
            }
            final EnumFacing facing = EnumFacing.UP;
            AutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(placePos, facing, this.offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            AutoCrystal.mc.playerController.updateController();
            if (this.switchMode.getValue().equalsIgnoreCase("Silent")) {
                ItemUtil.switchToHotbarSlot(old, false);
            }
            this.doHandActive(hand);
            this.placeSet.add(placePos);
            this.renderSet.add((RenderPos) new RenderPos(placePos, this.color.getValue().getAlpha()));
            this.currentBlock = placePos;
            this.currentDamage = maxDamage;
            this.placeTimer.reset();
        }
        else {
            this.rotating = false;
        }
    }
    
    private void dbreak() {
        Entity maxCrystal = null;
        double maxDamage = 0.5;
        for (final Entity crystal : AutoCrystal.mc.world.loadedEntityList) {
            if (!(crystal instanceof EntityEnderCrystal)) {
                continue;
            }
            final float f = AutoCrystal.mc.player.canEntityBeSeen(crystal) ? this.breakRange.getValue().floatValue() : this.breakWallRange.getValue().floatValue();
            final double targetDamage;
            final double selfDamage;
            if (f <= AutoCrystal.mc.player.getDistance(crystal) || ((targetDamage = EntityUtil.calculate(crystal.posX, crystal.posY, crystal.posZ, (EntityLivingBase)this.currentTarget)) < this.minDamage.getValue().intValue() && EntityUtil.getHealth((EntityLivingBase)this.currentTarget) > this.facePlaceHp.getValue().intValue() && !this.lowArmor) || (selfDamage = EntityUtil.calculate(crystal.posX, crystal.posY, crystal.posZ, (EntityLivingBase)AutoCrystal.mc.player)) + 2.0 >= EntityUtil.getHealth((EntityLivingBase)AutoCrystal.mc.player) || selfDamage >= targetDamage) {
                continue;
            }
            if (maxDamage > targetDamage) {
                continue;
            }
            maxCrystal = crystal;
            maxDamage = targetDamage;
        }
        if (maxCrystal != null && this.breakTimer.passed(this.breakDelay.getValue().intValue())) {
            if (maxCrystal.ticksExisted < this.tick.getValue().intValue()) {
                return;
            }
            if (this.rotate.getValue()) {
                this.rotate(maxCrystal);
            }
            AutoCrystal.mc.getConnection().sendPacket((Packet)new CPacketUseEntity(maxCrystal));
            this.breakTimer.reset();
        }
        else {
            this.rotating = false;
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSpawnObject && this.instant.getValue()) {
            final SPacketSpawnObject packet2 = event.getPacket();
            if (packet2.getType() == 51 && this.placeSet.contains(new BlockPos(packet2.getX(), packet2.getY(), packet2.getZ()).down()) && this.predictTimer.passed(this.predictDelay.getValue().intValue())) {
                final AccessorCPacketUseEntity hitPacket = (AccessorCPacketUseEntity)new CPacketUseEntity();
                final int entityId = packet2.getEntityID();
                hitPacket.setEntityId(entityId);
                hitPacket.setAction(CPacketUseEntity.Action.ATTACK);
                AutoCrystal.mc.getConnection().sendPacket((Packet)hitPacket);
                this.predictTimer.reset();
            }
        }
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (this.ticks++ > 20) {
            this.ticks = 0;
            this.placeSet.clear();
            this.renderSet.clear();
        }
        this.offhand = (AutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
        this.currentTarget = EntityUtil.getTarget(this.range.getValue().floatValue());
        if (this.currentTarget == null) {
            return;
        }
        AutoEz.setTarget(this.currentTarget);
        this.lowArmor = ItemUtil.isArmorLow(this.currentTarget, this.armorScale.getValue().intValue());
        if (this.currentTarget == null) {
            this.info = "None";
        }
        else {
            this.info = this.currentTarget.getName();
        }
        this.doAutoCrystal();
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        final List<RenderPos> toRemove = new ArrayList<RenderPos>();
        for (final RenderPos renderPos : this.renderSet) {
            renderPos.update();
            if (renderPos.alpha <= 0.0) {
                toRemove.add(renderPos);
            }
            if (toRemove.contains(renderPos)) {
                continue;
            }
            Renderer3D.drawBoxESP(((boolean)this.upRender.getValue()) ? new BlockPos(renderPos.blockPos.getX(), renderPos.blockPos.getY() + 1, renderPos.blockPos.getZ()) : renderPos.blockPos, this.color.getValue(), this.linewidht.getValue().floatValue(), this.outline.getValue(), this.box.getValue(), (int)Math.round(renderPos.alpha), (int)Math.round(renderPos.outline), this.boxHeight.getValue().floatValue());
        }
        toRemove.forEach(this.renderSet::remove);
    }
    
    public void doAutoCrystal() {
        if (this.logic.getValue().equalsIgnoreCase("BreakPlace")) {
            if (this.breakCrystal.getValue()) {
                this.dbreak();
            }
            if (this.placeCrystal.getValue()) {
                this.place();
            }
        }
        else {
            if (this.placeCrystal.getValue()) {
                this.place();
            }
            if (this.breakCrystal.getValue()) {
                this.dbreak();
            }
        }
    }
    
    public void doHandActive(final EnumHand hand) {
        if (hand != null) {
            AutoCrystal.mc.player.setActiveHand(hand);
        }
    }
    
    private int checkCrystal() {
        for (int i = 0; i < 9; ++i) {
            if (AutoCrystal.mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }
        return -1;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.rotating && this.rotate.getValue()) {
            ((CPacketPlayer) event.getPacket()).getYaw(this.yaw);
            ((CPacketPlayer) event.getPacket()).getPitch(this.pitch);
        }
    }
    
    private void rotate(final BlockPos bp) {
        final float[] angles = RotationManager.calcAngle(AutoCrystal.mc.player.getPositionEyes(AutoCrystal.mc.getRenderPartialTicks()), new Vec3d((double)(bp.getX() + 0.5f), (double)(bp.getY() + 0.5f), (double)(bp.getZ() + 0.5f)));
        this.yaw = angles[0];
        this.pitch = angles[1];
        this.rotating = true;
    }
    
    private void rotate(final Entity e) {
        final float[] angles = RotationManager.calcAngle(AutoCrystal.mc.player.getPositionEyes(AutoCrystal.mc.getRenderPartialTicks()), e.getPositionEyes(AutoCrystal.mc.getRenderPartialTicks()));
        this.yaw = angles[0];
        this.pitch = angles[1];
        this.rotating = true;
    }
    
    public class RenderPos
    {
        BlockPos blockPos;
        double alpha;
        double outline;
        
        public RenderPos(final BlockPos blockPos, final double alpha) {
            this.blockPos = blockPos;
            this.alpha = alpha;
            this.outline = 200.0;
        }
        
        public void update() {
            if (this.alpha <= 0.0 || this.outline <= 0.0) {
                return;
            }
            this.alpha -= Math.min(Math.max(AutoCrystal.this.color.getValue().getAlpha() / 0.9f * Main.getFpsManager().getFrametime(), 0.0f), (float)AutoCrystal.this.color.getValue().getAlpha());
            this.outline -= Math.min(Math.max(262.5f * Main.getFpsManager().getFrametime(), 0.0f), 210.0f);
        }
    }
}
