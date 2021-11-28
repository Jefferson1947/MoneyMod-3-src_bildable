//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.client.gui.inventory.GuiContainer;
import wtf.cattyn.moneymod.client.ui.click.Screen;
import org.lwjgl.input.Mouse;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "MCP", desc = "Middle Click Pearl", cat = Module.Category.MISC)
public class MCP extends Module
{
    @Override
    public void onTick() {
        final int oldslot = MCP.mc.player.inventory.currentItem;
        if (Mouse.isButtonDown(2)) {
            if (MCP.mc.currentScreen instanceof Screen || MCP.mc.currentScreen instanceof GuiContainer) {
                return;
            }
            ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(ItemEnderPearl.class), false);
            MCP.mc.playerController.processRightClick((EntityPlayer)MCP.mc.player, (World)MCP.mc.world, EnumHand.MAIN_HAND);
            ItemUtil.switchToHotbarSlot(oldslot, false);
        }
    }
}
