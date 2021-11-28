// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod;

import org.apache.commons.codec.digest.DigestUtils;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class MoneyCord
{
    private static final DiscordRichPresence discordRichPresence;
    private static final DiscordRPC discordRPC;
    
    public static void start() {
        final DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        eventHandlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + var1 + ", var2: " + var2));
        final String discordID = "893061839628419092";
        MoneyCord.discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);
        MoneyCord.discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        MoneyCord.discordRichPresence.largeImageKey = "monemod";
        MoneyCord.discordRichPresence.details = "build: " + DigestUtils.md2Hex("550").substring(1, 4);
        MoneyCord.discordRichPresence.joinSecret = "MTI4NzM0OjFpMmhuZToxMjMxMjM= ";
        MoneyCord.discordRichPresence.partyId = "ae488379-351d-4a4f-ad32-2b9b01c91657";
        MoneyCord.discordRichPresence.state = null;
        MoneyCord.discordRPC.Discord_UpdatePresence(MoneyCord.discordRichPresence);
    }
    
    public static void stop() {
        MoneyCord.discordRPC.Discord_Shutdown();
        MoneyCord.discordRPC.Discord_ClearPresence();
    }
    
    static {
        discordRichPresence = new DiscordRichPresence();
        discordRPC = DiscordRPC.INSTANCE;
    }
}
