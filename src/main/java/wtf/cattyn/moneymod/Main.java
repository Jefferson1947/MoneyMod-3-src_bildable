// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod;

import wtf.cattyn.moneymod.client.module.Module;
import net.minecraft.client.Minecraft;
import wtf.cattyn.moneymod.util.Globals;
import wtf.cattyn.moneymod.api.core.Handler;
import net.minecraftforge.common.MinecraftForge;
import java.awt.Font;
import org.lwjgl.opengl.Display;
import wtf.cattyn.moneymod.client.ui.fuck.OTCScreen;
import wtf.cattyn.moneymod.client.ui.irc.IrcScreen;
import wtf.cattyn.moneymod.util.impl.capes.CapeThread;
import wtf.cattyn.moneymod.api.managment.CommandManager;
import wtf.cattyn.moneymod.util.impl.font.CFontRenderer;
import org.apache.logging.log4j.Logger;
import wtf.cattyn.moneymod.client.ui.tokenlogin.AltManager;
import wtf.cattyn.moneymod.client.ui.tokenlogin.Manager;
import wtf.cattyn.moneymod.client.ui.glslmenu.Shaders;
import wtf.cattyn.moneymod.client.ui.click.Screen;
import wtf.cattyn.moneymod.api.managment.RotationManager;
import wtf.cattyn.moneymod.api.managment.PulseManager;
import wtf.cattyn.moneymod.api.managment.FPSManager;
import wtf.cattyn.moneymod.api.managment.ConfigManager;
import wtf.cattyn.moneymod.api.managment.FriendManager;
import wtf.cattyn.moneymod.api.managment.SettingManager;
import wtf.cattyn.moneymod.api.managment.ModuleManager;

public class Main
{
    private static ModuleManager moduleManager;
    private static SettingManager settingManager;
    private static FriendManager friendManager;
    private static ConfigManager configManager;
    private static FPSManager fpsManager;
    private static PulseManager pulseManager;
    private static RotationManager rotationManager;
    private static Screen screen;
    public static Shaders shaders;
    private static Manager manager;
    private static AltManager altmanager;
    private static Logger logger;
    private static CFontRenderer fontrenderer;
    private static CFontRenderer fontrendererSmall;
    private static CommandManager commandManager;
    private static CapeThread capeThread;
    private static IrcScreen ircScreen;
    private static OTCScreen otcScreen;
    public static float TIMER_VALUE;
    private static Thread artificialTick;
    
    public static void init() {
        Display.setTitle("MoneyMod+3");
        try {
            Font verdanapro = Font.createFont(0, Main.class.getResourceAsStream("/fonts/VerdanaPro-Regular.ttf"));
            verdanapro = verdanapro.deriveFont(18.0f);
            Font verdanaprosmall = Font.createFont(0, Main.class.getResourceAsStream("/fonts/VerdanaPro-Regular.ttf"));
            verdanaprosmall = verdanaprosmall.deriveFont(14.0f);
            Main.fontrendererSmall = new CFontRenderer(verdanaprosmall, true, true);
            Main.fontrenderer = new CFontRenderer(verdanapro, true, true);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Main.capeThread = new CapeThread();
        final Thread capethread = new Thread(Main.capeThread);
        capethread.setName("moneymodplus3-cape-thread");
        capethread.start();
        Main.shaders = new Shaders();
        Main.settingManager = new SettingManager();
        Main.friendManager = new FriendManager();
        Main.moduleManager = new ModuleManager();
        Main.fpsManager = new FPSManager();
        Main.screen = new Screen();
        Main.otcScreen = new OTCScreen();
        Main.commandManager = new CommandManager();
        Main.configManager = new ConfigManager();
        Main.pulseManager = new PulseManager();
        Main.manager.start();
        Main.altmanager = new AltManager();
        Main.configManager.load();
        Main.rotationManager = new RotationManager();
        Main.ircScreen = new IrcScreen();
        MinecraftForge.EVENT_BUS.register((Object)new Handler());
        Runtime.getRuntime().addShutdownHook(Main.configManager);
        Main.artificialTick.start();
        Globals.printLoadedMessage();
    }
    
    public static ModuleManager getModuleManager() {
        return Main.moduleManager;
    }
    
    public static SettingManager getSettingManager() {
        return Main.settingManager;
    }
    
    public static FriendManager getFriendManager() {
        return Main.friendManager;
    }
    
    public static PulseManager getPulseManager() {
        return Main.pulseManager;
    }
    
    public static ConfigManager getConfigManager() {
        return Main.configManager;
    }
    
    public static FPSManager getFpsManager() {
        return Main.fpsManager;
    }
    
    public static Screen getScreen() {
        return Main.screen;
    }
    
    public static Manager getManager() {
        return Main.manager;
    }
    
    public static AltManager getAltManager() {
        return Main.altmanager;
    }
    
    public static RotationManager getRotationManager() {
        return Main.rotationManager;
    }
    
    public static Logger getLogger() {
        return Main.logger;
    }
    
    public static CFontRenderer getFontRenderer() {
        return Main.fontrenderer;
    }
    
    public static CommandManager getCommandManager() {
        return Main.commandManager;
    }
    
    public static CapeThread getCapeThread() {
        return Main.capeThread;
    }
    
    public static IrcScreen getIrcScreen() {
        return Main.ircScreen;
    }
    
    public static OTCScreen getOtcScreen() {
        return Main.otcScreen;
    }
    
    public static CFontRenderer getFontRendererS() {
        return Main.fontrendererSmall;
    }
    

}
