//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import wtf.cattyn.moneymod.util.impl.EntityUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.Main;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import wtf.cattyn.moneymod.util.impl.render.Renderer2D;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import wtf.cattyn.moneymod.util.impl.MathUtil;
import java.util.Iterator;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.api.event.TotemPopEvent;
import java.util.HashMap;
import wtf.cattyn.moneymod.mixin.mixins.AccessorRenderManager;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "NameTags", desc = "Render namtags above players", cat = Module.Category.RENDER)
public class NameTags extends Module
{
    private final Setting<Number> range;
    private final Setting<Number> size;
    private final Setting<Boolean> self;
    private final Setting<Boolean> fill;
    private final Setting<Boolean> outline;
    private final Setting<Number> thickness;
    private final Setting<Color> fillColor;
    private final Setting<Color> outlineColor;
    private final Setting<Color> textColor;
    private final Setting<Boolean> name;
    private final Setting<Boolean> friend;
    private final Setting<Boolean> ping;
    private final Setting<Boolean> health;
    private final Setting<Boolean> healthColor;
    private final Setting<Boolean> gamemode;
    private final Setting<Boolean> totem;
    private final Setting<Boolean> items;
    private final Setting<Boolean> mainhand;
    private final Setting<Boolean> offhand;
    private final Setting<Boolean> armor;
    private final Setting<Boolean> armorDura;
    AccessorRenderManager renderManager;
    HashMap<String, Integer> totemPops;
    
    public NameTags() {
        this.range = this.register("Range", 80.0, 8.0, 160.0, 1.0);
        this.size = this.register("Size", 0.5, 0.1, 1.0, 0.1);
        this.self = this.register("Self", false);
        this.fill = this.register("Fill", true);
        this.outline = this.register("Outline", true);
        this.thickness = this.register("Thickness", 1.0, 0.1, 3.0, 1.0);
        this.fillColor = this.register("Fill Color", new Color(1426063360, true), false);
        this.outlineColor = this.register("Outline Color", new Color(0, 0, 0, 255), false);
        this.textColor = this.register("Text Color", new Color(255, 255, 255, 255), false);
        this.name = this.register("Name", true);
        this.friend = this.register("Friend", true);
        this.ping = this.register("Ping", true);
        this.health = this.register("Health", true);
        this.healthColor = this.register("Colored Health", true);
        this.gamemode = this.register("Gamemode", false);
        this.totem = this.register("Totems", true);
        this.items = this.register("Items", true);
        this.mainhand = this.register("MainHand", true);
        this.offhand = this.register("Offhand", true);
        this.armor = this.register("Armor", false);
        this.armorDura = this.register("Armor Dura", true);
        this.renderManager = (AccessorRenderManager)NameTags.mc.getRenderManager();
        this.totemPops = new HashMap<String, Integer>();
    }
    
    @SubscribeEvent
    public void onTotemPop(final TotemPopEvent event) {
        final String name = event.getEntityPlayerSP().getName();
        if (this.totemPops.get(name) == null) {
            this.totemPops.putIfAbsent(name, 1);
            return;
        }
        this.totemPops.replace(name, this.totemPops.get(name) + 1);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        if (this.nullCheck()) {
            this.totemPops.clear();
            return;
        }
        NameTags.mc.world.loadedEntityList.forEach(e -> {
            if (e instanceof EntityPlayer && this.totemPops.containsKey(((Entity)e).getName()) && (mc.player.getHealth() <= 0.0f || ((Entity)e).isDead || NameTags.mc.player.getDistance((Entity)e) >= this.range.getValue().doubleValue())) {
                this.totemPops.remove(((Entity)e).getName());
            }
        });
    }
    
    @SubscribeEvent
    public void onRender3D(final RenderWorldLastEvent event) {
        for (final Entity player : NameTags.mc.world.loadedEntityList) {
            if (player instanceof EntityPlayer && !player.isDead && ((EntityPlayer)player).getHealth() > 0.0f) {
                if (NameTags.mc.player.getDistance(player) > this.range.getValue().doubleValue()) {
                    continue;
                }
                if (player == NameTags.mc.player) {
                    if (!this.self.getValue()) {
                        continue;
                    }
                    this.renderNameTage((EntityPlayer)player);
                }
                else {
                    this.renderNameTage((EntityPlayer)player);
                }
            }
        }
    }
    
    private void renderNameTage(final EntityPlayer player) {
        if (NameTags.mc.getRenderViewEntity() == null) {
            return;
        }
        final double x = MathUtil.interpolate(player.lastTickPosX, player.posX, NameTags.mc.getRenderPartialTicks()) - this.renderManager.getRenderPosX();
        final double y = MathUtil.interpolate(player.lastTickPosY, player.posY, NameTags.mc.getRenderPartialTicks()) - this.renderManager.getRenderPosY() + (player.isSneaking() ? 0.5 : 0.7);
        final double z = MathUtil.interpolate(player.lastTickPosZ, player.posZ, NameTags.mc.getRenderPartialTicks()) - this.renderManager.getRenderPosZ();
        final double delta = NameTags.mc.getRenderPartialTicks();
        final Entity localPlayer = NameTags.mc.getRenderViewEntity();
        final double originalPositionX = localPlayer.posX;
        final double originalPositionY = localPlayer.posY;
        final double originalPositionZ = localPlayer.posZ;
        localPlayer.posX = MathUtil.interpolate(localPlayer.prevPosX, localPlayer.posX, delta);
        localPlayer.posY = MathUtil.interpolate(localPlayer.prevPosY, localPlayer.posY, delta);
        localPlayer.posZ = MathUtil.interpolate(localPlayer.prevPosZ, localPlayer.posZ, delta);
        final String tag = this.getTagString(player);
        final double distance = localPlayer.getDistance(x + NameTags.mc.getRenderManager().viewerPosX, y + NameTags.mc.getRenderManager().viewerPosY, z + NameTags.mc.getRenderManager().viewerPosZ);
        final int width = RenderUtil.getStringWidth(tag) >> 1;
        double scale = (float)(((distance / 5.0 <= 2.0) ? 2.0 : (distance / 5.0 * (this.size.getValue().floatValue() + 1.0f))) * 2.5 * (this.size.getValue().floatValue() / 100.0f));
        if (distance <= 8.0) {
            scale = 0.0245;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate(x, y + 1.399999976158142, z);
        GlStateManager.rotate(-NameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(NameTags.mc.getRenderManager().playerViewX, (NameTags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GL11.glDepthRange(0.0, 0.1);
        if (this.fill.getValue()) {
            Renderer2D.drawRect((float)(-width - 2), (float)(-(NameTags.mc.fontRenderer.FONT_HEIGHT + 1)), width + 2.0f, 1.5f, this.fillColor.getValue().getRGB());
        }
        if (this.outline.getValue()) {
            Renderer2D.drawOutline(-width - 2, -(NameTags.mc.fontRenderer.FONT_HEIGHT + 1), width + 2.0f, 1.5, this.thickness.getValue().floatValue(), this.outlineColor.getValue().getRGB());
        }
        if (this.items.getValue()) {
            GlStateManager.pushMatrix();
            int xOffset = -8;
            for (int i = 0; i < 4; ++i) {
                xOffset -= 8;
            }
            final ItemStack renderOffhand = player.getHeldItemOffhand().copy();
            xOffset -= 8;
            if (this.offhand.getValue()) {
                this.renderItemStack(renderOffhand, xOffset);
                this.renderDurabilityLabel(renderOffhand, xOffset, -50);
            }
            xOffset += 16;
            for (int j = 0; j < 4; ++j) {
                if (this.armor.getValue()) {
                    this.renderItemStack(((ItemStack)player.inventory.armorInventory.get(j)).copy(), xOffset);
                }
                if (this.armorDura.getValue()) {
                    this.renderDurabilityLabel(((ItemStack)player.inventory.armorInventory.get(j)).copy(), xOffset, ((boolean)this.armor.getValue()) ? -50 : -21);
                }
                xOffset += 16;
            }
            if (this.mainhand.getValue()) {
                this.renderItemStack(player.getHeldItemMainhand().copy(), xOffset);
                this.renderDurabilityLabel(player.getHeldItemMainhand().copy(), xOffset, -50);
            }
            GlStateManager.popMatrix();
        }
        RenderUtil.drawStringWithShadow(tag, (float)(-width), -8.0f, this.textColor.getValue().getRGB());
        localPlayer.posX = originalPositionX;
        localPlayer.posY = originalPositionY;
        localPlayer.posZ = originalPositionZ;
        GL11.glDepthRange(0.0, 1.0);
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderItemStack(final ItemStack stack, final int x) {
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        NameTags.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        NameTags.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -27);
        NameTags.mc.getRenderItem().renderItemOverlays(NameTags.mc.fontRenderer, stack, x, -27);
        NameTags.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        this.renderEnchantmentLabel(stack, x, -27);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    private void renderEnchantmentLabel(final ItemStack stack, final int x, final int y) {
        int enchantmentY = y - 8;
        final NBTTagList enchants = stack.getEnchantmentTagList();
        for (int index = 0; index < enchants.tagCount(); ++index) {
            final short id = enchants.getCompoundTagAt(index).getShort("id");
            final short level = enchants.getCompoundTagAt(index).getShort("lvl");
            final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
            if (enc != null && !enc.getName().contains("fall")) {
                if (enc.getName().contains("all") || enc.getName().contains("explosion")) {
                    RenderUtil.drawStringWithShadow(enc.isCurse() ? (TextFormatting.RED + enc.getTranslatedName((int)level).substring(11).substring(0, 1).toLowerCase()) : (enc.getTranslatedName((int)level).substring(0, 1).toLowerCase() + level), (float)(x * 2), (float)enchantmentY, -1);
                    enchantmentY -= 8;
                }
            }
        }
    }
    
    private void renderDurabilityLabel(final ItemStack stack, final int x, final int y) {
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) {
            final float green = (stack.getMaxDamage() - (float)stack.getItemDamage()) / stack.getMaxDamage();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            RenderUtil.drawStringWithShadow(dmg + "%", x * 2 + 4, y - 10, new Color((int)(red * 255.0f), (int)(green * 255.0f), 0).getRGB());
        }
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    private String getTagString(final EntityPlayer player) {
        final StringBuilder sb = new StringBuilder();
        if (Main.getFriendManager().is(player.getName()) && this.friend.getValue()) {
            sb.append(ChatFormatting.AQUA);
        }
        if (this.ping.getValue()) {
            try {
                final NetworkPlayerInfo npi = NameTags.mc.player.connection.getPlayerInfo(player.getGameProfile().getId());
                sb.append(npi.getResponseTime());
            }
            catch (Exception e) {
                sb.append("0");
            }
            sb.append("ms");
        }
        if (this.name.getValue()) {
            sb.append(" ").append(player.getName());
        }
        if (this.health.getValue()) {
            if (this.healthColor.getValue()) {
                sb.append(this.getHealthColor(EntityUtil.getHealth((EntityLivingBase)player)));
            }
            sb.append(" ").append((int)EntityUtil.getHealth((EntityLivingBase)player)).append(ChatFormatting.RESET);
        }
        if (Main.getFriendManager().is(player.getName()) && this.friend.getValue()) {
            sb.append(ChatFormatting.AQUA);
        }
        if (this.gamemode.getValue()) {
            sb.append(" [");
            try {
                final String sus = this.getShortName(NameTags.mc.player.connection.getPlayerInfo(player.getGameProfile().getId()).getGameType().getName());
                sb.append(sus);
            }
            catch (Exception ignored) {
                sb.append("S");
            }
            sb.append("]");
        }
        if (this.totem.getValue()) {
            this.totemPops.putIfAbsent(player.getName(), 0);
            if (this.totemPops.get(player.getName()) != 0) {
                sb.append(" -").append(this.totemPops.get(player.getName()));
            }
        }
        return sb.toString();
    }
    
    private ChatFormatting getHealthColor(final double health) {
        if (health >= 20.0) {
            return ChatFormatting.GREEN;
        }
        if (health >= 16.0) {
            return ChatFormatting.DARK_GREEN;
        }
        if (health >= 10.0) {
            return ChatFormatting.GOLD;
        }
        if (health >= 4.0) {
            return ChatFormatting.RED;
        }
        return ChatFormatting.DARK_RED;
    }
    
    private String getShortName(final String gameType) {
        if (gameType.equalsIgnoreCase("survival")) {
            return "S";
        }
        if (gameType.equalsIgnoreCase("creative")) {
            return "C";
        }
        if (gameType.equalsIgnoreCase("adventure")) {
            return "A";
        }
        if (gameType.equalsIgnoreCase("spectator")) {
            return "SP";
        }
        return "NONE";
    }
}
