//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "PearlTracker", desc = "Draws pearl's trajectory", cat = Module.Category.RENDER)
public class PearlTracker extends Module
{
    private static PearlTracker INSTANCE;
    private final Setting<Boolean> render;
    private final Setting<Number> thick;
    private final Setting<Number> aliveTime;
    private final Setting<Color> color;
    private final HashMap<UUID, List<Vec3d>> poses;
    private final HashMap<UUID, Double> time;
    int rdelay;
    
    public PearlTracker() {
        this.render = this.register("Render", false);
        this.thick = this.register("Thick", 3.0, 0.1, 10.0, 0.1);
        this.aliveTime = this.register("Time", 5.0, 0.0, 20.0, 1.0);
        this.color = this.register("Color", new Color(255, 255, 255), false);
        this.poses = new HashMap<UUID, List<Vec3d>>();
        this.time = new HashMap<UUID, Double>();
        this.rdelay = 120;
        PearlTracker.INSTANCE = this;
    }
    
    public static PearlTracker getInstance() {
        return PearlTracker.INSTANCE;
    }
    
    @Override
    public void onTick() {
        UUID toRemove = null;
        for (final UUID uuid : this.time.keySet()) {
            if (this.time.get(uuid) <= 0.0) {
                this.poses.remove(uuid);
                toRemove = uuid;
            }
            else {
                this.time.replace(uuid, this.time.get(uuid) - 0.05);
            }
        }
        if (toRemove != null) {
            this.time.remove(toRemove);
        }
        for (final Entity e : PearlTracker.mc.world.getLoadedEntityList()) {
            if (!(e instanceof EntityEnderPearl)) {
                continue;
            }
            if (!this.poses.containsKey(e.getUniqueID())) {
                this.poses.put(e.getUniqueID(), new ArrayList<Vec3d>(Collections.singletonList(e.getPositionVector())));
                this.time.put(e.getUniqueID(), this.aliveTime.getValue().doubleValue());
            }
            else {
                this.time.replace(e.getUniqueID(), this.aliveTime.getValue().doubleValue());
                final List<Vec3d> v = this.poses.get(e.getUniqueID());
                v.add(e.getPositionVector());
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (!this.render.getValue() && !this.poses.isEmpty()) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(this.thick.getValue().floatValue());
        for (final UUID uuid : this.poses.keySet()) {
            if (this.poses.get(uuid).size() <= 2) {
                continue;
            }
            int delay = 0;
            GL11.glBegin(1);
            for (int i = 1; i < this.poses.get(uuid).size(); ++i) {
                delay += this.rdelay;
                final Color c = this.color.isRainbow() ? ColorUtil.releasedDynamicRainbow(delay, this.color.getValue().getAlpha()) : this.color.getValue();
                GL11.glColor4d((double)(c.getRed() / 255.0f), (double)(c.getGreen() / 255.0f), (double)(c.getBlue() / 255.0f), (double)(c.getAlpha() / 255.0f));
                final List<Vec3d> pos = this.poses.get(uuid);
                GL11.glVertex3d(pos.get(i).x - PearlTracker.mc.getRenderManager().viewerPosX, pos.get(i).y - PearlTracker.mc.getRenderManager().viewerPosY, pos.get(i).z - PearlTracker.mc.getRenderManager().viewerPosZ);
                GL11.glVertex3d(pos.get(i - 1).x - PearlTracker.mc.getRenderManager().viewerPosX, pos.get(i - 1).y - PearlTracker.mc.getRenderManager().viewerPosY, pos.get(i - 1).z - PearlTracker.mc.getRenderManager().viewerPosZ);
            }
            GL11.glEnd();
        }
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
