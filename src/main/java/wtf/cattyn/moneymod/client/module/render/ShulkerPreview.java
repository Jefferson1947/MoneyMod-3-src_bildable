// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import net.minecraft.item.Item;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "ShulkerPreview", desc = "Show shulker contents when you hover over them", cat = Module.Category.RENDER)
public class ShulkerPreview extends Module
{
    private boolean check;
    private Item item;
    
    public ShulkerPreview() {
        this.check = false;
        this.item = null;
    }
    
    public void setState(final boolean state) {
        this.check = state;
    }
    
    public boolean check() {
        return this.check;
    }
    
    public void setItem(final Item item) {
        this.item = item;
    }
    
    public Item getItem() {
        return this.item;
    }
}
