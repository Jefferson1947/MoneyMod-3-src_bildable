//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.util.Globals;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import com.mojang.authlib.GameProfile;
import wtf.cattyn.moneymod.api.event.TotemPopEvent;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "PopChams", desc = "Draws player's chams on totem pop", cat = Module.Category.RENDER)
public class PopChams extends Module
{
    private final Setting<Color> color;
    private final Setting<Boolean> self;
    private final Setting<Number> maxOffset;
    private final Setting<Number> speed;
    public final CopyOnWriteArrayList<Person> popList;
    
    public PopChams() {
        this.color = this.register("Color", new Color(255, 255, 255), false);
        this.self = this.register("Self", false);
        this.maxOffset = this.register("MaxOffset", 6.0, 0.0, 15.0, 1.0);
        this.speed = this.register("Speed", 1.0, 0.1, 5.0, 0.1);
        this.popList = new CopyOnWriteArrayList<Person>();
    }
    
    @SubscribeEvent
    public void onPop(final TotemPopEvent event) {
        if (!this.self.getValue() && event.getEntityPlayerSP() == PopChams.mc.player) {
            return;
        }
        final EntityPlayer entity = new EntityPlayer(PopChams.mc.world, new GameProfile(event.getEntityPlayerSP().getUniqueID(), event.getEntityPlayerSP().getName())) {
            public boolean isSpectator() {
                return false;
            }
            
            public boolean isCreative() {
                return false;
            }
        };
        entity.copyLocationAndAnglesFrom((Entity)event.getEntityPlayerSP());
        this.popList.add(new Person(entity));
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        this.info = String.valueOf(this.popList.size());
        GL11.glBlendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(1.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        this.popList.forEach(person -> {
            person.update(this.popList);
            person.modelPlayer.bipedLeftLegwear.showModel = false;
            person.modelPlayer.bipedRightLegwear.showModel = false;
            person.modelPlayer.bipedLeftArmwear.showModel = false;
            person.modelPlayer.bipedRightArmwear.showModel = false;
            person.modelPlayer.bipedBodyWear.showModel = false;
            person.modelPlayer.bipedHead.showModel = false;
            person.modelPlayer.bipedHeadwear.showModel = true;
            GlStateManager.color(this.color.getValue().getRed() / 255.0f, this.color.getValue().getGreen() / 255.0f, this.color.getValue().getBlue() / 255.0f, (float)person.alpha / 255.0f);
            GL11.glPolygonMode(1032, 6914);
            this.renderEntity((EntityLivingBase)person.player, (ModelBase)person.modelPlayer, person.player.limbSwing, person.player.limbSwingAmount, (float)person.player.ticksExisted, person.player.rotationYawHead, person.player.rotationPitch, 1.0f);
            GL11.glPolygonMode(1032, 6913);
            this.renderEntity((EntityLivingBase)person.player, (ModelBase)person.modelPlayer, person.player.limbSwing, person.player.limbSwingAmount, (float)person.player.ticksExisted, person.player.rotationYawHead, person.player.rotationPitch, 1.0f);
            GL11.glPolygonMode(1032, 6914);
            return;
        });
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }
    
    private void renderEntity(final EntityLivingBase entity, final ModelBase modelBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (modelBase instanceof ModelPlayer) {
            final ModelPlayer modelPlayer = (ModelPlayer)modelBase;
            modelPlayer.bipedBodyWear.showModel = false;
            modelPlayer.bipedLeftLegwear.showModel = false;
            modelPlayer.bipedRightLegwear.showModel = false;
            modelPlayer.bipedLeftArmwear.showModel = false;
            modelPlayer.bipedRightArmwear.showModel = false;
            modelPlayer.bipedHeadwear.showModel = true;
            modelPlayer.bipedHead.showModel = false;
        }
        final float partialTicks = PopChams.mc.getRenderPartialTicks();
        final double x = entity.posX - PopChams.mc.getRenderManager().viewerPosX;
        double y = entity.posY - PopChams.mc.getRenderManager().viewerPosY;
        final double z = entity.posZ - PopChams.mc.getRenderManager().viewerPosZ;
        GlStateManager.pushMatrix();
        if (entity.isSneaking()) {
            y -= 0.125;
        }
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
        final float f4 = this.prepareScale(entity, scale);
        final float yaw = entity.rotationYawHead;
        GlStateManager.enableAlpha();
        modelBase.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        modelBase.setRotationAngles(limbSwing, limbSwingAmount, 0.0f, yaw, entity.rotationPitch, f4, (Entity)entity);
        modelBase.render((Entity)entity, limbSwing, limbSwingAmount, 0.0f, yaw, entity.rotationPitch, f4);
        GlStateManager.popMatrix();
    }
    
    private float prepareScale(final EntityLivingBase entity, final float scale) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        final double widthX = entity.getRenderBoundingBox().maxX - entity.getRenderBoundingBox().minX;
        final double widthZ = entity.getRenderBoundingBox().maxZ - entity.getRenderBoundingBox().minZ;
        GlStateManager.scale(scale + widthX, (double)(scale * entity.height), scale + widthZ);
        final float f = 0.0625f;
        GlStateManager.translate(0.0f, -1.501f, 0.0f);
        return f;
    }
    
    private class Person
    {
        private double alpha;
        private double ticks;
        private final EntityPlayer player;
        private final ModelPlayer modelPlayer;
        
        public Person(final EntityPlayer player) {
            this.player = player;
            this.modelPlayer = new ModelPlayer(0.0f, false);
            this.alpha = 180.0;
            this.ticks = 0.0;
        }
        
        public void update(final CopyOnWriteArrayList<Person> arrayList) {
            ++this.ticks;
            if (this.alpha <= 0.0) {
                arrayList.remove(this);
                Globals.mc.world.removeEntity((Entity)this.player);
                return;
            }
            this.alpha -= 180.0 / PopChams.this.speed.getValue().doubleValue() * Main.getFpsManager().getFrametime();
            final EntityPlayer player = this.player;
            player.posY += PopChams.this.maxOffset.getValue().doubleValue() / PopChams.this.speed.getValue().doubleValue() * Main.getFpsManager().getFrametime();
        }
    }
}
