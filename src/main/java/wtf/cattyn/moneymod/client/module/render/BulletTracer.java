//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import wtf.cattyn.moneymod.Main;
import java.util.ArrayList;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.entity.projectile.EntityArrow;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicReference;

import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Bullets", desc = "Draws arrow's trajectory", cat = Module.Category.RENDER)
public class BulletTracer extends Module
{
    private final Setting<Number> aliveTime;
    private final Setting<Number> thickness;
    private final Setting<Boolean> fade;
    private final Setting<Color> color;
    private final List<Bullet> bullets;
    
    public BulletTracer() {
        this.aliveTime = this.register("Time", 5.0, 0.0, 20.0, 1.0);
        this.thickness = this.register("Thickness", 1.0, 0.10000000149011612, 2.0, 0.1);
        this.fade = this.register("Fade", true);
        this.color = this.register("Color", new Color(255, 255, 255), false);
        this.bullets = new CopyOnWriteArrayList<Bullet>();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        this.info = String.valueOf(this.bullets.size());
        this.bullets.forEach(bullet -> {
            if (bullet.time <= 0.0) {
                if (!this.fade.getValue()) {
                    this.bullets.remove(bullet);
                }
                else {
                    bullet.update();
                }
            }
            bullet.time -= 0.05;
            return;
        });
        final Bullet bullet2;
        final Object o;

    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(this.thickness.getValue().floatValue());
        AtomicInteger i = new AtomicInteger();
        AtomicReference<List<Vec3d>> pos = null;
        this.bullets.stream().filter(bullet -> bullet.vec3d.size() > 2).forEach(bullet -> {
            GL11.glBegin(1);
            for (i.set(1); i.get() < bullet.vec3d.size(); i.incrementAndGet()) {
                GL11.glColor4d((double)(this.color.getValue().getRed() / 255.0f), (double)(this.color.getValue().getGreen() / 255.0f), (double)(this.color.getValue().getBlue() / 255.0f), (double)(bullet.alpha / 255.0f));
                pos.set(bullet.vec3d);
                GL11.glVertex3d(pos.get().get(i.get()).x - BulletTracer.mc.getRenderManager().viewerPosX, pos.get().get(i.get()).y - BulletTracer.mc.getRenderManager().viewerPosY, pos.get().get(i.get()).z - BulletTracer.mc.getRenderManager().viewerPosZ);
                GL11.glVertex3d(pos.get().get(i.get() - 1).x - BulletTracer.mc.getRenderManager().viewerPosX, pos.get().get(i.get() - 1).y - BulletTracer.mc.getRenderManager().viewerPosY, pos.get().get(i.get() - 1).z - BulletTracer.mc.getRenderManager().viewerPosZ);
            }
            GL11.glEnd();
            return;
        });
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    private Bullet getByEntity(final EntityArrow entityArrow) {
        return this.bullets.stream().filter(e -> e.entity == entityArrow).findFirst().orElse(null);
    }
    
    class Bullet
    {
        int alpha;
        EntityArrow entity;
        double time;
        List<Vec3d> vec3d;
        
        Bullet(final EntityArrow entity, final double time, final Vec3d vec3d) {
            this.entity = entity;
            this.time = time;
            this.alpha = 255;
            this.vec3d = new ArrayList<Vec3d>();
            vec3d.add(vec3d);
        }
        
        void update() {
            if (this.alpha <= 0) {
                BulletTracer.this.bullets.remove(this);
            }
            else {
                this.alpha -= (int)Math.min(Math.max(283.33334f * Main.getFpsManager().getFrametime(), 0.0f), 255.0f);
            }
        }
    }
}
