// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.managment;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import wtf.cattyn.moneymod.client.module.Module;
import java.util.ArrayList;
import wtf.cattyn.moneymod.api.setting.Setting;
import java.util.List;

public class SettingManager
{
    private final List<Setting<?>> settings;
    
    public SettingManager() {
        this.settings = new ArrayList<Setting<?>>();
    }
    
    public List<Setting<?>> get() {
        return this.settings;
    }
    
    public Setting<?> get(final String name) {
        return this.settings.stream().filter(setting -> setting.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
    
    public Setting<?> get(final String name, final Module module) {
        return this.settings.stream().filter(setting -> setting.getName().equalsIgnoreCase(name) && setting.getParent().equals(module)).findAny().orElse(null);
    }

    public List<Setting<?>> get(Module module) {
        return (List)this.settings.stream().filter((setting) -> {
            return setting.getParent().equals(module);
        }).collect(Collectors.toList());
    }
}
