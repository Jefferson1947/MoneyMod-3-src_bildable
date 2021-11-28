//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.module.client;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import wtf.cattyn.moneymod.mixin.mixins.AccessorSPacketChat;
import java.util.Date;
import net.minecraft.network.play.server.SPacketChat;
import wtf.cattyn.moneymod.api.event.PacketEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import wtf.cattyn.moneymod.util.Globals;
import wtf.cattyn.moneymod.util.impl.font.RenderUtil;
import wtf.cattyn.moneymod.util.impl.render.ColorUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import wtf.cattyn.moneymod.Main;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.awt.Color;
import wtf.cattyn.moneymod.api.setting.Setting;
import wtf.cattyn.moneymod.client.module.Module;

@Module.Manifest(name = "HUD", cat = Module.Category.CLIENT)
public class HUD extends Module
{
    private final Setting<Color> color;
    private final Setting<Number> offsets;
    private final Setting<String> gradient;
    private final Setting<Number> gradientDiff;
    private final Setting<Boolean> timeStamps;
    private final Setting<Boolean> watermark;
    private final Setting<Boolean> build;
    private final Setting<Boolean> ping;
    private final Setting<Boolean> fps;
    private final Setting<Boolean> arraylist;
    private final Setting<Boolean> sortDown;
    public final Setting<String> infoMode;
    private final Setting<Boolean> worlexMode;
    private final Setting<Boolean> coords;
    private final Setting<Boolean> potions;
    private final List<String> niggers;
    private final HashMap<String, String> names;
    
    public HUD() {
        this.color = this.register("Color", new Color(0, 255, 0), false);
        this.offsets = this.register("Offset", 1.0, 0.0, 10.0, 1.0);
        this.gradient = this.register("Gradient", "None", Arrays.asList("None", "Simple", "Sideway"));
        this.gradientDiff = this.register("Difference", 200.0, 0.0, 1000.0, 1.0);
        this.timeStamps = this.register("Time", false);
        this.watermark = this.register("Watermark", true);
        this.build = this.register("WatermarkBuild", true);
        this.ping = this.register("Ping", true);
        this.fps = this.register("FPS", true);
        this.arraylist = this.register("ArrayList", true);
        this.sortDown = this.register("SortDown", false);
        this.infoMode = this.register("Info", "Simple", Arrays.asList("Simple", "Default", "Comment"));
        this.worlexMode = this.register("WorlexMode", false);
        this.coords = this.register("Coords", false);
        this.potions = this.register("Pots", false);
        this.niggers = Arrays.asList("ok !", "xo !", "* slatt _ !", "$$$", ">", ".", "^!", "**+");
        this.names = new HashMap<String, String>();
    }
    
    @Override
    protected void onEnable() {
        Main.getModuleManager().get().forEach(m -> this.names.putIfAbsent(m.getName(), this.carti(m.getName())));
    }
    
    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Text event) {
        int offset = 1;
        final ScaledResolution sr = new ScaledResolution(HUD.mc);
        if (this.coords.getValue()) {
            String coordinatiki = null;
            if (HUD.mc.player.dimension == -1) {
                coordinatiki = "" + ChatFormatting.GRAY + (int)HUD.mc.player.posX + " " + ChatFormatting.WHITE + "[" + (int)HUD.mc.player.posX * 8 + "]" + ChatFormatting.DARK_GRAY + "," + ChatFormatting.GRAY + " " + (int)HUD.mc.player.posY + ChatFormatting.DARK_GRAY + ", " + ChatFormatting.GRAY + (int)HUD.mc.player.posZ + " " + ChatFormatting.WHITE + "[" + (int)HUD.mc.player.posZ * 8 + "]";
            }
            else if (HUD.mc.player.dimension == 0) {
                coordinatiki = "" + ChatFormatting.GRAY + (int)HUD.mc.player.posX + " " + ChatFormatting.WHITE + "[" + (int)HUD.mc.player.posX / 8 + "]" + ChatFormatting.DARK_GRAY + "," + ChatFormatting.GRAY + " " + (int)HUD.mc.player.posY + ChatFormatting.DARK_GRAY + ", " + ChatFormatting.GRAY + (int)HUD.mc.player.posZ + " " + ChatFormatting.WHITE + "[" + (int)HUD.mc.player.posZ / 8 + "]";
            }
            RenderUtil.drawStringWithShadow(coordinatiki, 2, HUD.mc.ingameGUI.getChatGUI().getChatOpen() ? (sr.getScaledHeight() - 23) : (sr.getScaledHeight() - 11), ColorUtil.injectBrightness(this.color.getValue(), Main.getPulseManager().getDifference(offset * 2) / 255.0f).getRGB());
        }
        if (this.watermark.getValue()) {
            RenderUtil.drawStringWithShadow("MoneyMod+3 v550", 1, 1 + offset, ColorUtil.injectBrightness(this.color.getValue(), Main.getPulseManager().getDifference(offset * 2) / 255.0f).getRGB());
            offset += RenderUtil.getFontHeight() + this.offsets.getValue().intValue();
        }
        if (this.ping.getValue()) {
            RenderUtil.drawStringWithShadow(String.format("ping %s", Globals.getPlayerPing()), 1, 1 + offset, ColorUtil.injectBrightness(this.color.getValue(), Main.getPulseManager().getDifference(offset * 2) / 255.0f).getRGB());
            offset += RenderUtil.getFontHeight() + this.offsets.getValue().intValue();
        }
        if (this.fps.getValue()) {
            RenderUtil.drawStringWithShadow(String.format("fps %s", Main.getFpsManager().getFPS()), 1, 1 + offset, ColorUtil.injectBrightness(this.color.getValue(), Main.getPulseManager().getDifference(offset * 2) / 255.0f).getRGB());
            offset += RenderUtil.getFontHeight() + this.offsets.getValue().intValue();
        }
        if (this.arraylist.getValue()) {
            if ((Boolean) this.arraylist.getValue()) {
                AtomicInteger y = new AtomicInteger();
                AtomicInteger count = new AtomicInteger();
                Main.getModuleManager().get().stream().sorted(Comparator.comparing((module) -> {
                    return -RenderUtil.getStringWidth(((Boolean) this.worlexMode.getValue() ? (String) this.names.get(module.getName()) : module.getName()) + module.getInfo());
                })).filter((m) -> {
                    return m.isToggled() && (Boolean) m.drawn.getValue();
                }).forEach((m) -> {
                    float[] hsb = Color.RGBtoHSB(((Color) this.color.getValue()).getRed(), ((Color) this.color.getValue()).getGreen(), ((Color) this.color.getValue()).getBlue(), (float[]) null);
                    String name = (Boolean) this.worlexMode.getValue() ? (String) this.names.get(m.getName()) : m.getName();
                    if (((String) this.gradient.getValue()).equalsIgnoreCase("None")) {
                        RenderUtil.drawStringWithShadow(name + m.getInfo(), sr.getScaledWidth() - RenderUtil.getStringWidth(name + m.getInfo()), (Boolean) this.sortDown.getValue() ? sr.getScaledHeight() - y.get() - 9 : y.get(), ColorUtil.injectBrightness((Color) this.color.getValue(), (float) Main.getPulseManager().getDifference(y.get() * 2) / 255.0F).getRGB());
                    } else if (((String) this.gradient.getValue()).equalsIgnoreCase("Simple")) {
                        RenderUtil.drawStringWithShadow(name + m.getInfo(), sr.getScaledWidth() - RenderUtil.getStringWidth(name + m.getInfo()), (Boolean) this.sortDown.getValue() ? sr.getScaledHeight() - y.get() - 9 : y.get(), ColorUtil.rainbowColor(count.get() * ((Number) this.gradientDiff.getValue()).intValue(), hsb[1], (float) Main.getPulseManager().getDifference(y.get() * 2) / 255.0F).getRGB());
                    } else {
                        int update = sr.getScaledWidth() - RenderUtil.getStringWidth(name + m.getInfo());

                        for (int j = 0; j < name.length() + m.getInfo().length(); ++j) {
                            String c = (name + m.getInfo()).charAt(j) + "";
                            RenderUtil.drawStringWithShadow(c, update, (Boolean) this.sortDown.getValue() ? sr.getScaledHeight() - y.get() - 9 : y.get(), ColorUtil.rainbowColor(j * ((Number) this.gradientDiff.getValue()).intValue(), hsb[1], (float) Main.getPulseManager().getDifference(y.get() * 2) / 255.0F).getRGB());
                            update += RenderUtil.getStringWidth(c);
                        }
                    }

                    count.getAndIncrement();
                });
            }
        }
        if (this.potions.getValue()) {

        }
    }
    
    @SubscribeEvent
    public final void onPacketReceive(final PacketEvent.Receive event) {
        if (this.timeStamps.getValue() && event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = event.getPacket();
            final Date date = new Date();
            final AccessorSPacketChat chatPacket = event.getPacket();
            boolean add = false;
            if (date.getMinutes() <= 9) {
                add = true;
            }
            final String time = "<" + date.getHours() + ":" + (add ? "0" : "") + date.getMinutes() + "> ";
            chatPacket.setChatComponent((ITextComponent)new TextComponentString("§$" + time + ChatFormatting.RESET + packet.getChatComponent().getFormattedText()));
        }
    }
    
    private String carti(final String text) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String string : text.split(" ")) {
            for (final char chr : string.toCharArray()) {
                final String s = String.valueOf(chr);
                stringBuilder.append(HUD.random.nextBoolean() ? s.toUpperCase() : s.toLowerCase());
            }
            stringBuilder.append(" ").append(this.niggers.get(HUD.random.nextInt(this.niggers.size() - 1))).append(" ");
        }
        return stringBuilder.toString().replaceAll("the", "da").replace("o", "0").replaceAll("e", "3");
    }
}
