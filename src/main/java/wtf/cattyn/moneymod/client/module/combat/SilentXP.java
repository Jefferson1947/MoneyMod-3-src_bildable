//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.client.gui.inventory.GuiContainer;
import wtf.cattyn.moneymod.client.ui.click.Screen;
import org.lwjgl.input.Mouse;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "SilentXP", cat = Module.Category.COMBAT)
public class SilentXP extends Module
{
    private final Setting<Boolean> middleclick;
    private final Setting<Boolean> aim;
    
    public SilentXP() {
        this.middleclick = this.register("MiddleClick", true);
        this.aim = this.register("Aim", false);
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (this.middleclick.getValue()) {
            if (Mouse.isButtonDown(2)) {
                this.xp();
            }
        }
        else {
            this.xp();
        }
    }
    
    private void xp() {
        if (SilentXP.mc.currentScreen instanceof Screen || SilentXP.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        final int oldslot = SilentXP.mc.player.inventory.currentItem;
        final float angle = SilentXP.mc.player.rotationPitch;
        ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(ItemExpBottle.class), false);
        if (this.aim.getValue()) {
            SilentXP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(SilentXP.mc.player.rotationYaw, 90.0f, SilentXP.mc.player.onGround));
        }
        SilentXP.mc.playerController.processRightClick((EntityPlayer)SilentXP.mc.player, (World)SilentXP.mc.world, EnumHand.MAIN_HAND);
        SilentXP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(SilentXP.mc.player.rotationYaw, angle, SilentXP.mc.player.onGround));
        ItemUtil.switchToHotbarSlot(oldslot, false);
    }
}
