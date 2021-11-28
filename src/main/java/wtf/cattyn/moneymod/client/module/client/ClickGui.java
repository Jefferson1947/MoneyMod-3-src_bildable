//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.client;

import wtf.cattyn.moneymod.util.Globals;
import net.minecraft.client.gui.GuiScreen;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "ClickGui", cat = Module.Category.CLIENT, key = 210)
public class ClickGui extends Module
{
    public final Setting<Boolean> modulecounter;
    public final Setting<Boolean> descriptions;
    public final Setting<Boolean> blur;
    public final Setting<Boolean> customfont;
    public final Setting<Boolean> bounding;
    public final Setting<Boolean> glow;
    private final Setting<Boolean> otc;
    public final Setting<Number> scrollSpeedd;
    public static float size;
    public String niggerz;
    String[] message;
    
    public ClickGui() {
        this.modulecounter = this.register("M-Counter", true);
        this.descriptions = this.register("Descriptions", true);
        this.blur = this.register("Blur", false);
        this.customfont = this.register("CustomFont", true);
        this.bounding = this.register("Boundiong", true);
        this.glow = this.register("Glow", true);
        this.otc = this.register("OTC", false);
        this.scrollSpeedd = this.register("ScrollSpeed", 2.0, 1.0, 15.0, 1.0);
        this.message = new String[] { "Iq niggers - 15 to 20 points, on average, lower than that of White Mans", "Differences between Negro and White children increase with age, the difference in performance being greatest in colleges and high schools in the United States. ", "The result of interracial marriages tends to have a lower IQ than the White parent. ", "Niggers commit murder thirteen times more often than Whites; violence and robbery ten times more often. This is the information given by the FBI. The reports have varied somewhat from year to year but give a fairly accurate picture of the past decade. ", "According to the U.S. Department of Justice, 1 in 4 black men between the ages of 20 and 29 is currently in prison or on probation.", "Approximately 50% of all black men have been arrested and charged with a serious felony during their lifetime. ", "A Black man is 56 times more likely to attack a White man than vice versa.", "In New York City, any White is 300 times MORE likely to be attacked by the Black Brigade than the Black Brigade by the White Brigade. ", "46% percent of urban blacks between the ages of 16 and 62 refuse to work, preferring to live on welfare. ", "More than 66% of Negro children are born out of wedlock. Per capita, that number is ten times that of Whites. ", "More than 35% of all black people in U.S. cities regularly take drugs or hard alcohol. ", "90% of American children infected with AIDS are black or Latino. ", "From Army as many Blacks as Whites are expelled from the U.S. Army. ", "An African dentist can tell the difference between a Negro tooth and a White man at once. ", "I HATE NIGGERS" };
    }
    
    @Override
    protected void onEnable() {
        ClickGui.size = 0.0f;
        if (ClickGui.mc.currentScreen != Main.getScreen()) {
            if (this.otc.getValue()) {
                ClickGui.mc.displayGuiScreen((GuiScreen)Main.getOtcScreen());
            }
            else {
                ClickGui.mc.displayGuiScreen((GuiScreen)Main.getScreen());
            }
        }
        this.niggerz = this.message[Globals.random.nextInt(this.message.length)];
        this.setToggled(false);
    }
    
    public static void update() {
        ClickGui.size = Math.min(ClickGui.size + 3.3333333f * Main.getFpsManager().getFrametime(), 1.0f);
    }
    
    static {
        ClickGui.size = 0.0f;
    }
}
