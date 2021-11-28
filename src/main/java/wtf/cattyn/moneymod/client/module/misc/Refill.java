//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import java.util.Map;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Refill", desc = "Refills items from item containers", cat = Module.Category.MISC)
public class Refill extends Module
{
    private final Setting<Number> count;
    int delay;
    private int delayStep;
    
    public Refill() {
        this.count = this.register("Count", 32.0, 1.0, 64.0, 1.0);
        this.delay = 1;
        this.delayStep = 0;
    }
    
    private Map<Integer, ItemStack> getInventory() {
        return this.getInventorySlots(9, 35);
    }
    
    private Map<Integer, ItemStack> getHotbar() {
        return this.getInventorySlots(36, 44);
    }
    
    private Map<Integer, ItemStack> getInventorySlots(int current, final int last) {
        final HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)Refill.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (Refill.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delayStep < this.delay) {
            ++this.delayStep;
            return;
        }
        this.delayStep = 0;
        final Pair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        final int inventorySlot = slots.getKey();
        final int hotbarSlot = slots.getValue();
        Refill.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)Refill.mc.player);
        Refill.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)Refill.mc.player);
        Refill.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)Refill.mc.player);
    }
    
    private Pair<Integer, Integer> findReplenishableHotbarSlot() {
        Pair<Integer, Integer> returnPair = null;
        for (final Map.Entry<Integer, ItemStack> hotbarSlot : this.getHotbar().entrySet()) {
            final ItemStack stack = hotbarSlot.getValue();
            if (!stack.isEmpty() && stack.getItem() != Items.AIR && stack.isStackable() && stack.getCount() < stack.getMaxStackSize() && stack.getCount() <= this.count.getValue().intValue()) {
                final int inventorySlot;
                if ((inventorySlot = this.findCompatibleInventorySlot(stack)) == -1) {
                    continue;
                }
                returnPair = new Pair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
            }
        }
        return returnPair;
    }
    
    private int findCompatibleInventorySlot(final ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (final Map.Entry<Integer, ItemStack> entry : this.getInventory().entrySet()) {
            final ItemStack inventoryStack = entry.getValue();
            if (!inventoryStack.isEmpty() && inventoryStack.getItem() != Items.AIR && this.isCompatibleStacks(hotbarStack, inventoryStack)) {
                final int currentStackSize;
                if (smallestStackSize <= (currentStackSize = ((ItemStack)Refill.mc.player.inventoryContainer.getInventory().get((int)entry.getKey())).getCount())) {
                    continue;
                }
                smallestStackSize = currentStackSize;
                inventorySlot = entry.getKey();
            }
        }
        return inventorySlot;
    }
    
    private boolean isCompatibleStacks(final ItemStack stack1, final ItemStack stack2) {
        if (!stack1.getItem().equals(stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            final Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            final Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.getMaterial(block1.getBlockState().getBaseState()).equals(block2.getMaterial(block2.getBlockState().getBaseState()))) {
                return false;
            }
        }
        return this.stackEqualExact(stack1, stack2) && stack1.getItemDamage() == stack2.getItemDamage();
    }
    
    private boolean stackEqualExact(final ItemStack stack1, final ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
    
    private static class Pair<K, V>
    {
        final K key;
        final V value;
        
        public Pair(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
        
        public V getValue() {
            return this.value;
        }
        
        public K getKey() {
            return this.key;
        }
    }
}
