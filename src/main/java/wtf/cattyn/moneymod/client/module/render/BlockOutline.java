//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.BlockPos;
import wtf.cattyn.moneymod.util.impl.render.Renderer3D;
import net.minecraft.world.World;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "BlockOutline", cat = Module.Category.RENDER)
public class BlockOutline extends Module
{
    public final Setting<Number> lineWidth;
    public final Setting<Color> color;
    
    public BlockOutline() {
        this.lineWidth = this.register("Width", 1.0, 0.1, 2.5, 0.1);
        this.color = this.register("Color", new Color(255, 255, 255), false);
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        final RayTraceResult ray = BlockOutline.mc.objectMouseOver;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos positionRender = ray.getBlockPos();
            Renderer3D.drawBoxESP(BlockOutline.mc.world.getBlockState(positionRender).getSelectedBoundingBox((World)BlockOutline.mc.world, positionRender), this.color.getValue(), this.lineWidth.getValue().floatValue(), true, false, 0, this.color.getValue().getAlpha());
        }
    }
}
