// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.client;

import wtf.cattyn.moneymod.util.impl.capes.CapeEnum;
import java.util.Arrays;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "Capes", cat = Module.Category.CLIENT)
public class Capes extends Module
{
    public final Setting<String> cape;
    public final Setting<Boolean> override;
    
    public Capes() {
        this.cape = this.register("Cape", "Pepsi", Arrays.asList("Pepsi", "idk", "nigger", "squidgame", "pigcape", "1488", "capedosia"));
        this.override = this.register("OverrideCapes", false);
    }
    
    public CapeEnum getCape() {
        for (final CapeEnum c : CapeEnum.values()) {
            if (c.getCapeName().equalsIgnoreCase(this.cape.getValue())) {
                return c;
            }
        }
        return null;
    }
}
