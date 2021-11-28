//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import wtf.cattyn.moneymod.util.impl.ItemUtil;
import net.minecraft.block.BlockChest;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityDonkey;
import org.lwjgl.input.Mouse;
import net.minecraft.util.EnumHand;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "EntityChestPlace", desc = "bypassing the ban on dressing chests", cat = Module.Category.MISC)
public class EntityChestPlace extends Module
{
    public final Setting<Boolean> suol;
    
    public EntityChestPlace() {
        this.suol = this.register("pig bypass", true);
    }
    
    @Override
    public void onTick() {
        if (this.suol.getValue()) {
            if (EntityChestPlace.mc.player.getHeldItem(EnumHand.MAIN_HAND) == null || !Mouse.isButtonDown(1)) {
                return;
            }
            if (EntityChestPlace.mc.objectMouseOver.entityHit instanceof EntityDonkey || EntityChestPlace.mc.objectMouseOver.entityHit instanceof EntityMule || EntityChestPlace.mc.objectMouseOver.entityHit instanceof EntityLlama) {
                EntityChestPlace.mc.playerController.processRightClick((EntityPlayer)EntityChestPlace.mc.player, (World)EntityChestPlace.mc.world, EnumHand.MAIN_HAND);
                ItemUtil.switchToHotbarSlot(ItemUtil.findHotbarBlock(BlockChest.class), false);
                EntityChestPlace.mc.playerController.processRightClick((EntityPlayer)EntityChestPlace.mc.player, (World)EntityChestPlace.mc.world, EnumHand.MAIN_HAND);
            }
        }
    }
}
