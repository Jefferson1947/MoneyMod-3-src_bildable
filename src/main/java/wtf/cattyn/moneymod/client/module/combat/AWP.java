//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import java.awt.Color;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemBow;
import wtf.cattyn.moneymod.util.impl.Timer;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AWP", desc = "gay exploit do not use doesnt work", cat = Module.Category.COMBAT)
public class AWP extends Module
{
    private boolean hs;
    int tiki;
    private long lastHsTime;
    long percent;
    private final Setting<Boolean> bypass;
    private final Setting<Boolean> autoFire;
    private final Setting<Boolean> renderText;
    private final Setting<Boolean> renderBar;
    private final Setting<Number> renderY;
    private final Setting<Number> timeout;
    private final Setting<Number> spoofs;
    private final Timer timer;
    
    public AWP() {
        this.bypass = this.register("Bypass", true);
        this.autoFire = this.register("AutoFire", true);
        this.renderText = this.register("RenderText", false);
        this.renderBar = this.register("RenderBar", false);
        this.renderY = this.register("YPosRender", 0.0, 0.0, 120.0, 1.0);
        this.timeout = this.register("Time", 3.0, 0.0, 20.0, 1.0);
        this.spoofs = this.register("Spoof", 8.0, 1.0, 30.0, 1.0);
        this.timer = new Timer();
    }
    
    public void onEnable() {
        this.hs = false;
        this.tiki = 0;
        this.lastHsTime = System.currentTimeMillis();
    }
    
    public void onDisable() {
        this.tiki = 0;
    }
    
    @Override
    public void onTick() {
        if (this.autoFire.getValue() && AWP.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow && AWP.mc.player.isHandActive() && AWP.mc.player.getItemInUseMaxCount() >= 4 && this.autoFire.getValue() && this.percent >= 100L) {
            ++this.tiki;
            if (this.tiki >= 12) {
                this.doUseItem();
                this.tiki = 0;
            }
        }
        this.percent = (long)Math.min((System.currentTimeMillis() - this.lastHsTime) / (this.timeout.getValue().doubleValue() * 1000.0) * 100.0, 100.0);
        this.info = this.percent + "%";
    }
    
    public void doUseItem() {
        AWP.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, AWP.mc.player.getHorizontalFacing()));
        AWP.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        AWP.mc.player.stopActiveHand();
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerDigging) {
            final CPacketPlayerDigging packet = event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM) {
                final ItemStack handStack = AWP.mc.player.getHeldItem(EnumHand.MAIN_HAND);
                if (!handStack.isEmpty() && handStack.getItem() != null && handStack.getItem() instanceof ItemBow && System.currentTimeMillis() - this.lastHsTime >= this.timeout.getValue().intValue() * 1000) {
                    this.hs = true;
                    this.lastHsTime = System.currentTimeMillis();
                    AWP.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AWP.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                    for (int index = 0; index < this.spoofs.getValue().intValue() * 10; ++index) {
                        if (this.bypass.getValue()) {
                            AWP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AWP.mc.player.posX, AWP.mc.player.posY + 1.0E-5, AWP.mc.player.posZ, false));
                            AWP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AWP.mc.player.posX, AWP.mc.player.posY - 1.0E-5, AWP.mc.player.posZ, true));
                        }
                        else {
                            AWP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AWP.mc.player.posX, AWP.mc.player.posY - 1.0E-5, AWP.mc.player.posZ, true));
                            AWP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(AWP.mc.player.posX, AWP.mc.player.posY + 1.0E-5, AWP.mc.player.posZ, false));
                        }
                    }
                    this.hs = false;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Text event) {
        GlStateManager.pushMatrix();
        final ScaledResolution sr = new ScaledResolution(AWP.mc);
        if (this.renderText.getValue()) {
            RenderUtil.drawStringWithShadow(String.format("%s/100", this.percent) + "%", (int)(sr.getScaledWidth() / 2.0f - RenderUtil.getStringWidth(String.format("%s/100", this.percent) + "%") / 2.0f), (int)(sr.getScaledHeight() / 2.0f + 10.0f + this.renderY.getValue().intValue()), new Color(170, 170, 170).getRGB());
        }
        if (this.renderBar.getValue()) {
            Renderer2D.drawRect(sr.getScaledWidth() / 2.0f - 21.0f, sr.getScaledHeight() / 2.0f + 20.0f + this.renderY.getValue().intValue(), sr.getScaledWidth() / 2.0f + 23.0f, sr.getScaledHeight() / 2.0f + 25.0f + this.renderY.getValue().intValue(), new Color(0, 0, 0, 140).getRGB());
            Renderer2D.drawRect(sr.getScaledWidth() / 2.0f - 20.0f, sr.getScaledHeight() / 2.0f + 21.0f + this.renderY.getValue().intValue(), sr.getScaledWidth() / 2.0f - 20.0f + this.percent * 0.42f, sr.getScaledHeight() / 2.0f + 24.0f + this.renderY.getValue().intValue(), (this.percent == 100L) ? ColorUtil.getGuiColor().getRGB() : Color.red.getRGB());
        }
        GlStateManager.popMatrix();
    }
}
