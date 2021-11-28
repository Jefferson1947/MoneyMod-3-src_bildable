// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.core;

import java.util.Map;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class Loader implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public Loader() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.moneymod.json");
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        Loader.isObfuscatedEnvironment = (boolean) data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        Loader.isObfuscatedEnvironment = false;
    }
}
