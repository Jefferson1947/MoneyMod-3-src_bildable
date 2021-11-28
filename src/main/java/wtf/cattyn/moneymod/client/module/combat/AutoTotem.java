//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.combat;

import net.minecraft.item.Item;
import net.minecraft.init.Items;
import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "AutoTotem", desc = "Puts items in your AutoTotem", cat = Module.Category.COMBAT)
public class AutoTotem extends Module
{
    private final Setting<String> item;
    public final Setting<Number> hpcrystal;
    private final Setting<Boolean> rightClickGapple;
    private final Setting<Boolean> crapple;
    
    public AutoTotem() {
        this.item = this.register("Item", "Crystal", Arrays.asList("Crystal", "Gapple", "Totem"));
        this.hpcrystal = this.register("Health", 15.0, 1.0, 36.0, 1.0);
        this.rightClickGapple = this.register("Right Click Gapple", false);
        this.crapple = this.register("Crapple", false);
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        this.info = this.getStringFromItem(AutoTotem.mc.player.getHeldItemOffhand().getItem());
        final float hp = AutoTotem.mc.player.getHealth() + AutoTotem.mc.player.getAbsorptionAmount();
        if (hp > this.hpcrystal.getValue().intValue()) {
            final Item heldItem = AutoTotem.mc.player.getHeldItemMainhand().getItem();
            if (this.rightClickGapple.getValue() && AutoTotem.mc.gameSettings.keyBindUseItem.isKeyDown() && (heldItem instanceof ItemSword || heldItem instanceof ItemAxe) && AutoTotem.mc.currentScreen == null) {
                if (!this.item.getValue().equalsIgnoreCase("Gapple")) {
                    ItemUtil.swapItemsOffhand(this.getSlot("Gapple"));
                }
            }
            else {
                ItemUtil.swapItemsOffhand(this.getSlot(this.item.getValue()));
            }
        }
        else {
            ItemUtil.swapItemsOffhand(ItemUtil.getItemSlot(Items.TOTEM_OF_UNDYING));
        }
    }
    
    private String getStringFromItem(final Item item) {
        if (item == Items.END_CRYSTAL) {
            return "Crystal";
        }
        if (item == Items.TOTEM_OF_UNDYING) {
            return "Totem";
        }
        if (item == Items.GOLDEN_APPLE) {
            return "Gapple";
        }
        return "";
    }
    
    private int getSlot(final String string) {
        switch (string) {
            case "Crystal": {
                return ItemUtil.getItemSlot(Items.END_CRYSTAL);
            }
            case "Gapple": {
                return ItemUtil.getGappleSlot(this.crapple.getValue());
            }
            default: {
                return ItemUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
            }
        }
    }
}
