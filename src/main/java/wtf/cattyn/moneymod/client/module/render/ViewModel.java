// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.render;

import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "ViewModel", desc = "Changes ur view model", cat = Module.Category.RENDER)
public class ViewModel extends Module
{
    private static ViewModel INSTANCE;
    public final Setting<Number> posX;
    public final Setting<Number> posY;
    public final Setting<Number> posZ;
    public final Setting<Number> scX;
    public final Setting<Number> scY;
    public final Setting<Number> scZ;
    
    public ViewModel() {
        this.posX = this.register("PosX", 0.0, -6.0, 6.0, 0.1);
        this.posY = this.register("PosY", 0.2, -3.0, 3.0, 0.1);
        this.posZ = this.register("PosZ", -1.11, -5.0, 5.0, 0.1);
        this.scX = this.register("ScX", 8.7, 7.0, 10.0, 0.1);
        this.scY = this.register("ScY", 8.6, 7.0, 10.0, 0.1);
        this.scZ = this.register("ScZ", 9.3, 7.0, 16.0, 0.1);
        ViewModel.INSTANCE = this;
    }
    
    public static ViewModel getInstance() {
        return ViewModel.INSTANCE;
    }
}
