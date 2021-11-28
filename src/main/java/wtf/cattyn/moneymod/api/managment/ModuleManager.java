// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.managment;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Comparator;
import java.util.Arrays;
import wtf.cattyn.moneymod.client.module.render.NameTags;
import wtf.cattyn.moneymod.client.module.render.NoInterp;
import wtf.cattyn.moneymod.client.module.render.ShulkerPreview;
import wtf.cattyn.moneymod.client.module.render.Swing;
import wtf.cattyn.moneymod.client.module.render.BulletTracer;
import wtf.cattyn.moneymod.client.module.render.CustomFog;
import wtf.cattyn.moneymod.client.module.render.ESP;
import wtf.cattyn.moneymod.client.module.render.ViewModel;
import wtf.cattyn.moneymod.client.module.render.BlockOutline;
import wtf.cattyn.moneymod.client.module.render.PopChams;
import wtf.cattyn.moneymod.client.module.render.Player;
import wtf.cattyn.moneymod.client.module.render.PearlTracker;
import wtf.cattyn.moneymod.client.module.render.CustomCrystal;
import wtf.cattyn.moneymod.client.module.render.Changer;
import wtf.cattyn.moneymod.client.module.movement.Scaffold;
import wtf.cattyn.moneymod.client.module.movement.PhaseWalk;
import wtf.cattyn.moneymod.client.module.movement.NewSpeed;
import wtf.cattyn.moneymod.client.module.movement.Vclip;
import wtf.cattyn.moneymod.client.module.movement.AntiVoid;
import wtf.cattyn.moneymod.client.module.movement.Strafe;
import wtf.cattyn.moneymod.client.module.movement.Step;
import wtf.cattyn.moneymod.client.module.movement.Sprint;
import wtf.cattyn.moneymod.client.module.movement.NoSlow;
import wtf.cattyn.moneymod.client.module.movement.FastFall;
import wtf.cattyn.moneymod.client.module.misc.RecorderMode;
import wtf.cattyn.moneymod.client.module.misc.IRCBackup;
import wtf.cattyn.moneymod.client.module.misc.FastXP;
import wtf.cattyn.moneymod.client.module.misc.Blink;
import wtf.cattyn.moneymod.client.module.misc.AutoFish;
import wtf.cattyn.moneymod.client.module.misc.PlayerDeath;
import wtf.cattyn.moneymod.client.module.misc.InstaMine;
import wtf.cattyn.moneymod.client.module.misc.PacketMine;
import wtf.cattyn.moneymod.client.module.misc.ShulkerCeption;
import wtf.cattyn.moneymod.client.module.misc.AntiPacket;
import wtf.cattyn.moneymod.client.module.misc.EntityChestPlace;
import wtf.cattyn.moneymod.client.module.misc.PingBypass;
import wtf.cattyn.moneymod.client.module.misc.AutoLog;
import wtf.cattyn.moneymod.client.module.misc.AntiAim;
import wtf.cattyn.moneymod.client.module.misc.AntiAfk;
import wtf.cattyn.moneymod.client.module.misc.Refill;
import wtf.cattyn.moneymod.client.module.misc.MoneyMiner;
import wtf.cattyn.moneymod.client.module.misc.AutoEz;
import wtf.cattyn.moneymod.client.module.misc.MCP;
import wtf.cattyn.moneymod.client.module.misc.MCF;
import wtf.cattyn.moneymod.client.module.misc.Velocity;
import wtf.cattyn.moneymod.client.module.misc.PopNotify;
import wtf.cattyn.moneymod.client.module.misc.SoundRemover;
import wtf.cattyn.moneymod.client.module.combat.Regear;
import wtf.cattyn.moneymod.client.module.combat.BowSpam;
import wtf.cattyn.moneymod.client.module.combat.AntiSurround;
import wtf.cattyn.moneymod.client.module.combat.SelfPlace;
import wtf.cattyn.moneymod.client.module.combat.Aura;
import wtf.cattyn.moneymod.client.module.combat.Criticals;
import wtf.cattyn.moneymod.client.module.combat.Resolver;
import wtf.cattyn.moneymod.client.module.combat.SelfHit;
import wtf.cattyn.moneymod.client.module.combat.FeetPlace;
import wtf.cattyn.moneymod.client.module.combat.SilentXP;
import wtf.cattyn.moneymod.client.module.combat.SelfFill;
import wtf.cattyn.moneymod.client.module.combat.AutoTotem;
import wtf.cattyn.moneymod.client.module.combat.AWP;
import wtf.cattyn.moneymod.client.module.combat.AutoCrystal;
import wtf.cattyn.moneymod.client.module.client.IRCGui;
import wtf.cattyn.moneymod.client.module.client.Capes;
import wtf.cattyn.moneymod.client.module.client.CSHud;
import wtf.cattyn.moneymod.client.module.client.MoneyRPC;
import wtf.cattyn.moneymod.client.module.client.Notifications;
import wtf.cattyn.moneymod.client.module.client.HUD;
import wtf.cattyn.moneymod.client.module.client.Global;
import wtf.cattyn.moneymod.client.module.client.ClickGui;
import wtf.cattyn.moneymod.client.module.dev.ChorusLag;
import wtf.cattyn.moneymod.client.module.movement.Warp;
import wtf.cattyn.moneymod.client.module.Module;
import java.util.List;

public class ModuleManager
{
    private final List<Module> modules;
    
    public ModuleManager() {
        (this.modules = Arrays.asList(new Warp(), new ChorusLag(), new ClickGui(), new Global(), new HUD(), new Notifications(), new MoneyRPC(), new CSHud(), new Capes(), new IRCGui(), new AutoCrystal(), new AWP(), new AutoTotem(), new SelfFill(), new SilentXP(), new FeetPlace(), new SelfHit(), new Resolver(),  new Criticals(), new Aura(), new SelfPlace(), new AntiSurround(), new BowSpam(), new Regear(), new SoundRemover(), new PopNotify(), new Velocity(), new MCF(), new MCP(), new AutoEz(), new MoneyMiner(), new Refill(), new AntiAfk(), new AntiAim(), new AutoLog(), new PingBypass(), new EntityChestPlace(), new AntiPacket(), new ShulkerCeption(), new PacketMine(), new InstaMine(), new PlayerDeath(), new AutoFish(), new Blink(), new FastXP(), new IRCBackup(), new RecorderMode(), new FastFall(), new NoSlow(), new Sprint(), new Step(), new Strafe(), new AntiVoid(), new Vclip(), new NewSpeed(), new PhaseWalk(), new Scaffold(), new Changer(), new CustomCrystal(), new PearlTracker(), new Player(), new PopChams(), new BlockOutline(), new ViewModel(), new ESP(), new CustomFog(), new BulletTracer(), new Swing(), new ShulkerPreview(), new NoInterp(), new NameTags())).sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getName));
    }
    
    public List<Module> get() {
        return this.modules;
    }
    
    public Module get(final String name) {
        return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
    
    public Module get(final Class<? extends Module> clazz) {
        return this.modules.stream().filter(module -> module.getClass().equals(clazz)).findAny().orElse(null);
    }

    public List<Module> get(Module.Category category) {
        return (List)this.modules.stream().filter((module) -> {
            return module.getCategory().equals(category);
        }).collect(Collectors.toList());
    }
}
