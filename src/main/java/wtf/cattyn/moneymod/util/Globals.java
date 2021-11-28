//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util;

import org.apache.logging.log4j.LogManager;
import java.util.Objects;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.apache.logging.log4j.Logger;
import java.util.Random;
import net.minecraft.client.Minecraft;
import com.google.gson.Gson;

public interface Globals
{
    public static final Gson gson = new Gson();
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String WATERMARK = "MoneyMod+3";
    public static final String NAME = "moneymod+3";
    public static final String BUILD = "550";
    public static final String F_CUSTOM = "§$";
    public static final Random random = new Random();
    public static final Logger l = LogManager.getLogger(" $ ");
    
    static int getPlayerPing() {
        try {
            return Objects.requireNonNull(Globals.mc.getConnection()).getPlayerInfo(Globals.mc.getConnection().getGameProfile().getId()).getResponseTime();
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    default String getPlayerName() {
        return Globals.mc.getConnection().getGameProfile().getName();
    }
    
    static void printLoadedMessage() {
        Globals.l.info("                                                                                    $$\\          $$$$$$\\ ");
        Globals.l.info("                                                                                    $$ |   $$\\  $$ ___$$\\ ");
        Globals.l.info("$$$$$$\\$$$$\\   $$$$$$\\  $$$$$$$\\   $$$$$$\\  $$\\   $$\\ $$$$$$\\$$$$\\   $$$$$$\\   $$$$$$$ |   $$ | \\_/   $$ |");
        Globals.l.info("$$  _$$  _$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ $$ |  $$ |$$  _$$  _$$\\ $$  __$$\\ $$  __$$ |$$$$$$$$\\ $$$$$ / ");
        Globals.l.info("$$ / $$ / $$ |$$ /  $$ |$$ |  $$ |$$$$$$$$ |$$ |  $$ |$$ / $$ / $$ |$$ /  $$ |$$ /  $$ |\\__$$  __|\\___$$\\ ");
        Globals.l.info("$$ | $$ | $$ |$$ |  $$ |$$ |  $$ |$$   ____|$$ |  $$ |$$ | $$ | $$ |$$ |  $$ |$$ |  $$ |   $$ | $$\\   $$ |");
        Globals.l.info("$$ | $$ | $$ |\\$$$$$$  |$$ |  $$ |\\$$$$$$$\\ \\$$$$$$$ |$$ | $$ | $$ |\\$$$$$$  |\\$$$$$$$ |   \\__| \\$$$$$$  |");
        Globals.l.info("\\__| \\__| \\__| \\______/ \\__|  \\__| \\_______| \\____$$ |\\__| \\__| \\__| \\______/  \\_______|         \\______/ ");
        Globals.l.info("                                            $$\\   $$ |                                                    ");
        Globals.l.info("                                            \\$$$$$$  |                                                    ");
        Globals.l.info("                                             \\______/                                                     ");
    }
}
