//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import wtf.cattyn.moneymod.Main;
import java.util.function.Predicate;
import wtf.cattyn.moneymod.util.impl.render.Renderer3D;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.mixin.mixins.AccessorCPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import java.util.Comparator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemSword;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Arrays;
import net.minecraft.entity.Entity;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Knife-Bot", cat = Module.Category.COMBAT)
public class Aura extends Module
{
    private final Setting<String> mode;
    private final Setting<String> hitbox;
    private final Setting<Number> range;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> client;
    private final Setting<Boolean> render;
    private final Setting<Color> color;
    float yaw;
    float pitch;
    boolean rotating;
    Entity target;
    
    public Aura() {
        this.mode = this.register("Mode", "None", Arrays.asList("None", "Switch", "Only"));
        this.hitbox = this.register("Hitbox", "Head", Arrays.asList("Head", "Body", "Legs"));
        this.range = this.register("Range", 4.2, 0.0, 6.0, 0.1);
        this.rotate = this.register("Rotate", false);
        this.client = this.register("ClientRotations", false);
        this.render = this.register("Render", false);
        this.color = this.register("Color", new Color(255, 255, 255, 120), false);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    @Override
    protected void onEnable() {
        this.target = null;
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        this.info = this.mode.getValue();
        this.target = this.findTarget(EntityPlayer.class::isInstance);
        if (this.target != null) {
            final String s = this.mode.getValue();
            switch (s) {
                case "Switch": {
                    ItemUtil.getHotbarItems().keySet().stream().filter(e -> e.getItem() instanceof ItemSword).max(Comparator.comparing(e -> ((ItemSword)e.getItem()).getAttackDamage() + EnchantmentHelper.getModifierForCreature(e, EnumCreatureAttribute.UNDEFINED))).ifPresent(bestSword -> ItemUtil.switchToHotbarSlot(ItemUtil.getHotbarItems().get(bestSword), false));
                    break;
                }
                case "Only": {
                    if (!(Aura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                        return;
                    }
                    break;
                }
            }
            this.attack();
        }
        else {
            this.rotating = false;
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.rotating && this.rotate.getValue() && !this.client.getValue()) {

        }
    }
    
    @SubscribeEvent
    public void Render3D(final RenderWorldLastEvent event) {
        if (this.target != null && this.render.getValue()) {
            if (this.mode.getValue().equalsIgnoreCase("Only")) {
                if (!(Aura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                    return;
                }
                Renderer3D.drawBoxESP(new AxisAlignedBB(this.target.posX - 0.30000001192092896, this.target.posY, this.target.posZ - 0.30000001192092896, this.target.posX + 0.30000001192092896, this.target.posY + 1.899999976158142, this.target.posZ + 0.30000001192092896), this.color.getValue(), 1.0f, true, true, this.color.getValue().getAlpha(), 255);
            }
            else {
                Renderer3D.drawBoxESP(new AxisAlignedBB(this.target.posX - 0.30000001192092896, this.target.posY, this.target.posZ - 0.30000001192092896, this.target.posX + 0.30000001192092896, this.target.posY + 1.899999976158142, this.target.posZ + 0.30000001192092896), this.color.getValue(), 1.0f, true, true, this.color.getValue().getAlpha(), 255);
            }
        }
    }
    
    private Entity findTarget(final Predicate<Entity> predicate) {
        return (Entity)Aura.mc.world.loadedEntityList.stream().filter(e -> !Main.getFriendManager().is(e.getName()) && e != Aura.mc.player && Aura.mc.player.getPositionVector().add(0.0, (double)Aura.mc.player.eyeHeight, 0.0).distanceTo(e.getPositionVector().add(0.0, e.height / 2.0, 0.0)) <= this.range.getValue().doubleValue()).filter(predicate).min(Comparator.comparing(e -> Aura.mc.player.getDistanceSq(e))).orElse(null);
    }
    
    private void attack() {
        if (Aura.mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
            if (this.rotate.getValue()) {
                this.rotating = true;
                this.yaw = Main.getRotationManager().look(this.target, false, false)[0];
                float substract = 0.0f;
                final String s = this.hitbox.getValue();
                switch (s) {
                    case "Body": {
                        substract = 20.0f;
                        break;
                    }
                    case "Legs": {
                        substract = 40.0f;
                        break;
                    }
                }
                this.pitch = Main.getRotationManager().look(this.target, false, false)[1] + substract;
                if (this.client.getValue()) {
                    Aura.mc.player.rotationYaw = this.yaw;
                    Aura.mc.player.rotationPitch = this.pitch;
                }
            }
            Aura.mc.playerController.attackEntity((EntityPlayer)Aura.mc.player, this.target);
            Aura.mc.player.swingArm(EnumHand.MAIN_HAND);
            Aura.mc.player.resetCooldown();
        }
    }
}
