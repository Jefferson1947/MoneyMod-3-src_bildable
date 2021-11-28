//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;
import java.awt.Color;
import java.util.List;
import net.minecraftforge.fml.common.eventhandler.Event;
import wtf.cattyn.moneymod.api.event.ModuleEvent;
import net.minecraftforge.common.MinecraftForge;
import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.client.HUD;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.util.Globals;

public abstract class Module implements Globals
{
    private String name;
    private String desc;
    protected String info;
    private Category category;
    private boolean toggled;
    private boolean exception;
    private int key;
    public Setting<Boolean> drawn;
    
    public Module() {
        this.info = "";
        final Manifest manifest = this.getClass().getAnnotation(Manifest.class);
        this.name = manifest.name();
        this.category = manifest.cat();
        this.desc = manifest.desc();
        this.key = manifest.key();
        this.exception = manifest.exception();
        this.drawn = this.register("Drawn", true);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getInfo() {
        final HUD m = (HUD)Main.getModuleManager().get(HUD.class);
        if (m != null) {
            final String s = m.infoMode.getValue();
            switch (s) {
                case "Simple": {
                    final String format = String.format(" %s%s", ChatFormatting.GRAY, this.info);
                    break;
                }
                case "Default": {
                    final String format = String.format(" %s[%s%s%s]", ChatFormatting.GRAY, ChatFormatting.RESET, this.info, ChatFormatting.GRAY);
                    break;
                }
                case "Comment": {
                    final String format = String.format(" %s//%s", ChatFormatting.GRAY, this.info);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + m.infoMode.getValue());
                }
            }
            String format = null;
            return this.info.equalsIgnoreCase("") ? "" : format;
        }
        return this.info.equalsIgnoreCase("") ? "" : String.format(" %s[%s%s%s]", ChatFormatting.GRAY, ChatFormatting.RESET, this.info, ChatFormatting.GRAY);
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public boolean isException() {
        return this.exception;
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
        if (toggled) {
            this.enable();
        }
        else {
            this.disable();
        }
    }
    
    public void toggle() {
        this.setToggled(!this.toggled);
    }
    
    public void enable() {
        MinecraftForge.EVENT_BUS.post((Event)new ModuleEvent(this, ModuleEvent.Type.ENABLE));
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.onEnable();
    }
    
    public void disable() {
        MinecraftForge.EVENT_BUS.post((Event)new ModuleEvent(this, ModuleEvent.Type.DISABLE));
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        this.onDisable();
    }
    
    protected Setting<Boolean> register(final String name, final boolean value) {
        final Setting<Boolean> setting = new Setting<Boolean>(name, this, value);
        Main.getSettingManager().get().add(setting);
        return setting;
    }
    
    protected Setting<Number> register(final String name, final double value, final double min, final double max, final double inc) {
        final Setting<Number> setting = new Setting<Number>(name, this, value, min, max, inc);
        Main.getSettingManager().get().add(setting);
        return setting;
    }
    
    protected Setting<String> register(final String name, final String value, final List<String> modes) {
        final Setting<String> setting = new Setting<String>(name, this, value, modes);
        Main.getSettingManager().get().add(setting);
        return setting;
    }
    
    protected Setting<Color> register(final String name, final Color value, final boolean rainbow) {
        final Setting<Color> setting = new Setting<Color>(name, this, value, rainbow);
        Main.getSettingManager().get().add(setting);
        return setting;
    }
    
    public void onTick() {
    }
    
    public void onArtificialTick() {
    }
    
    protected void onEnable() {
    }
    
    protected void onDisable() {
    }
    
    protected boolean nullCheck() {
        return Module.mc.world == null || Module.mc.player == null;
    }
    
    public enum Category
    {
        COMBAT("Combat"), 
        MOVEMENT("Movement"), 
        RENDER("Render"), 
        MISC("Misc"), 
        CLIENT("Client");
        
        private final String name;
        
        private Category(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Manifest {
        String name();
        
        Category cat();
        
        String desc() default "";
        
        int key() default 0;
        
        boolean exception() default false;
    }
}
