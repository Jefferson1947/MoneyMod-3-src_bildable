//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.impl.EntityUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.util.impl.render.Renderer3D;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.entity.Entity;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Resolver", cat = Module.Category.COMBAT)
public class Resolver extends Module
{
    private final Setting<Number> range;
    private final Setting<Boolean> packetLook;
    private final Setting<Boolean> render;
    private final Setting<Color> color;
    private final Setting<Boolean> predict;
    Entity entity;
    
    public Resolver() {
        this.range = this.register("Range", 40.0, 1.0, 100.0, 0.1);
        this.packetLook = this.register("PacketLook", true);
        this.render = this.register("Render", true);
        this.color = this.register("Color", new Color(255, 255, 255, 120), false);
        this.predict = this.register("Predict", true);
    }
    
    @SubscribeEvent
    public void Render3D(final RenderWorldLastEvent event) {
        if (this.entity != null && this.render.getValue()) {
            Renderer3D.drawBoxESP(new AxisAlignedBB(this.entity.posX - 0.30000001192092896, this.entity.posY, this.entity.posZ - 0.30000001192092896, this.entity.posX + 0.30000001192092896, this.entity.posY + 1.899999976158142, this.entity.posZ + 0.30000001192092896), this.color.getValue(), 1.0f, true, true, this.color.getValue().getAlpha(), 255);
        }
    }
    
    @Override
    public void onTick() {
        this.entity = (Entity)EntityUtil.getTarget(this.range.getValue().floatValue());
        if (this.entity == null) {
            return;
        }
        if (this.predict.getValue() && Resolver.mc.player.isHandActive()) {
            Main.getRotationManager().look(this.entity, this.packetLook.getValue());
        }
        if (this.entity == null) {
            this.info = String.format("%s%s", ChatFormatting.RED, "None");
        }
        else {
            this.info = String.format("%s%s", ChatFormatting.GREEN, this.entity.getName());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPacketSend(final PacketEvent.Send event) {
        if (!this.predict.getValue() && event.getPacket() instanceof CPacketPlayerDigging) {
            final CPacketPlayerDigging packet = event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM) {
                final ItemStack handStack = Resolver.mc.player.getHeldItem(EnumHand.MAIN_HAND);
                if (!handStack.isEmpty() && handStack.getItem() != null && handStack.getItem() instanceof ItemBow && this.entity != null) {
                    Main.getRotationManager().look(this.entity, this.packetLook.getValue());
                }
            }
        }
    }
}
