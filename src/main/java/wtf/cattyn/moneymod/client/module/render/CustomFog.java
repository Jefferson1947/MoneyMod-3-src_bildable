// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "CustomFog", desc = "name?", cat = Module.Category.RENDER)
public class CustomFog extends Module
{
    public final Setting<Color> color;
    
    public CustomFog() {
        this.color = this.register("Color", new Color(0, 255, 224), false);
    }
    
    @SubscribeEvent
    public void fog(final EntityViewRenderEvent.FogColors event) {
        event.setRed(this.color.getValue().getRed() / 255.0f);
        event.setGreen(this.color.getValue().getGreen() / 255.0f);
        event.setBlue(this.color.getValue().getBlue() / 255.0f);
    }
}
