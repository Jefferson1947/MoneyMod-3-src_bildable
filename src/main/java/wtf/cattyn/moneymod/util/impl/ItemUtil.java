//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemBlock;
import net.minecraft.inventory.ClickType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.EnumRarity;
import net.minecraft.init.Items;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import java.util.Map;
import wtf.cattyn.moneymod.util.Globals;

public class ItemUtil implements Globals
{
    public static Map<ItemStack, Integer> getHotbarItems() {
        final Map<ItemStack, Integer> items = new HashMap<ItemStack, Integer>();
        for (int j = 0; j < 9; ++j) {
            items.put(ItemUtil.mc.player.inventory.getStackInSlot(j), j);
        }
        return items;
    }
    
    public static int getGappleSlot(final boolean crapple) {
        if (Items.GOLDEN_APPLE == ItemUtil.mc.player.getHeldItemOffhand().getItem() && crapple == ItemUtil.mc.player.getHeldItemOffhand().getRarity().equals((Object)EnumRarity.RARE)) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final ItemStack item = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (crapple == item.getRarity().equals((Object)EnumRarity.RARE) && item.getItem() == Items.GOLDEN_APPLE) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
    
    public static int getItemSlot(final Item input) {
        if (input == ItemUtil.mc.player.getHeldItemOffhand().getItem()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final Item item = ItemUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isArmorLow(final EntityPlayer player, final int durability) {
        for (int i = 0; i < 4; ++i) {
            if (getDamageInPercent((ItemStack)player.inventory.armorInventory.get(i)) < durability) {
                return true;
            }
        }
        return false;
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        final float green = (stack.getMaxDamage() - (float)stack.getItemDamage()) / stack.getMaxDamage();
        final float red = 1.0f - green;
        return (float)(100 - (int)(red * 100.0f));
    }
    
    public static void swapItemsOffhand(final int slot) {
        if (slot == -1) {
            return;
        }
        ItemUtil.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.updateController();
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    if (clazz.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static int switchToHotbarSlot(final int slot, final boolean silent) {
        if (ItemUtil.mc.player.inventory.currentItem == slot || slot < 0 || slot > 8) {
            return slot;
        }
        ItemUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        if (!silent) {
            ItemUtil.mc.player.inventory.currentItem = slot;
        }
        ItemUtil.mc.playerController.updateController();
        return slot;
    }
    
    public static int findHotbarBlock(final Block... blockIn) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock ) {
                return i;
            }
        }
        return -1;
    }
}
