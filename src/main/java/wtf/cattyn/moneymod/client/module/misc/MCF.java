//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.misc;

import net.minecraft.entity.Entity;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.gui.inventory.GuiContainer;
import wtf.cattyn.moneymod.client.ui.click.Screen;
import org.lwjgl.input.Mouse;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "MCF", desc = "Middle Click Friends", cat = Module.Category.MISC)
public class MCF extends Module
{
    boolean clicked;
    
    public MCF() {
        this.clicked = false;
    }
    
    @Override
    public void onTick() {
        final RayTraceResult result = MCF.mc.objectMouseOver;
        if (Mouse.isButtonDown(2)) {
            if (!this.clicked) {
                if (MCF.mc.currentScreen instanceof Screen || MCF.mc.currentScreen instanceof GuiContainer) {
                    return;
                }
                final Entity entity;
                if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
                    if (Main.getFriendManager().is(entity.getName())) {
                        Main.getFriendManager().del(entity.getName());
                        ChatUtil.sendMessage(ChatFormatting.RED + entity.getName() + ChatFormatting.RED + " has been unfriended.");
                    }
                    else {
                        Main.getFriendManager().add(entity.getName());
                        ChatUtil.sendMessage(ChatFormatting.RED + entity.getName() + ChatFormatting.GREEN + " has been friended.");
                    }
                }
                this.clicked = true;
            }
            this.clicked = true;
        }
        else {
            this.clicked = false;
        }
    }
}
