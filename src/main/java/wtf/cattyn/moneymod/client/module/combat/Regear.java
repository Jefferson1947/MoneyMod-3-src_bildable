//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.util.Iterator;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import wtf.cattyn.moneymod.mixin.mixins.AccessorMixinGuiChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.gui.GuiScreen;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import wtf.cattyn.moneymod.Main;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Regear", desc = "Automatically get items out of shulker boxes and put them in specific spots in your inventory", cat = Module.Category.COMBAT)
public class Regear extends Module
{
    public final Setting<Number> delay;
    public final Setting<Boolean> shulkers;
    public final Setting<Boolean> chests;
    public final Setting<Boolean> enderchests;
    private Map<Integer, Integer> items;
    private int timer;
    private boolean print;
    
    public Regear() {
        this.delay = this.register("Delay", 5.0, 0.0, 20.0, 1.0);
        this.shulkers = this.register("Shulkers", true);
        this.chests = this.register("Chests", false);
        this.enderchests = this.register("EnderChests", false);
        this.items = new HashMap<Integer, Integer>();
        this.timer = 0;
        this.print = true;
        final File folder = new File("moneymod+3/kits/");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    public void onEnable() {
        if (this.items.isEmpty() && this.print && Regear.mc.player != null && Regear.mc.world != null) {
            final String cmdprefix = Main.getCommandManager().getPrefix() + "kit";
            ChatUtil.sendMessage(String.format("Please create a kit using %s create <name> to create a kit with your current inventory, then use %s set <name> to select it, then turn this module back on.", cmdprefix, cmdprefix));
            this.print = false;
        }
        this.timer = 0;
    }
    
    public void onDisable() {
        this.print = true;
        this.timer = 0;
    }
    
    private boolean checkGuiScreen(final GuiScreen screen) {
        if (screen == null) {
            return false;
        }
        if (screen instanceof GuiShulkerBox) {
            return this.shulkers.getValue();
        }
        if (!(screen instanceof GuiChest)) {
            return false;
        }
        if (((AccessorMixinGuiChest)screen).getLowerInventory().getName().equalsIgnoreCase("Ender Chest")) {
            return this.enderchests.getValue();
        }
        return this.chests.getValue();
    }
    
    public void setCurrentItems(final JsonArray array) {
        this.items.clear();
        final Iterator it = array.iterator();
        while (it.hasNext()) {

        }
    }
    
    private boolean checkSlot(final int n, final int n2) {
        return Regear.mc.player.inventoryContainer.getSlot(n + 9).getStack().getItem() == Item.getItemById(n2);
    }
    
    private boolean checkItem(final ItemStack stack) {
        final int n = Item.getIdFromItem(stack.getItem());
        for (final Map.Entry entry : this.items.entrySet()) {


        }
        return false;
    }
    
    private int rhi(final ItemStack stack) {
        final int n = Item.getIdFromItem(stack.getItem());
        for (final Map.Entry entry : this.items.entrySet()) {

        }
        return -999;
    }
    
    private boolean checkSlots(final ItemStack stack) {
        final Iterator it = this.items.values().iterator();
        while (it.hasNext()) {
            if (Item.getByNameOrId(String.valueOf(it.next())) == stack.getItem()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onTick() {
        if (!this.items.isEmpty() && this.checkGuiScreen(Regear.mc.currentScreen)) {
            int n = 0;
            ++this.timer;
            while (n < Regear.mc.player.openContainer.inventorySlots.size() - 36) {
                final ItemStack stack = Regear.mc.player.openContainer.getSlot(n).getStack();
                if (!stack.isEmpty() && this.checkItem(stack) && this.checkSlots(stack)) {
                    final int n2 = this.rhi(stack) + (Regear.mc.player.openContainer.inventorySlots.size() - 36);
                    if (this.timer >= this.delay.getValue().intValue()) {
                        Regear.mc.playerController.windowClick(Regear.mc.player.openContainer.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)Regear.mc.player);
                        Regear.mc.playerController.windowClick(Regear.mc.player.openContainer.windowId, n2, 0, ClickType.PICKUP, (EntityPlayer)Regear.mc.player);
                        Regear.mc.playerController.windowClick(Regear.mc.player.openContainer.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)Regear.mc.player);
                        this.timer = 0;
                    }
                }
                ++n;
            }
        }
    }
}
