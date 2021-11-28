//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.util.impl.capes;

import net.minecraft.util.ResourceLocation;
import java.util.Iterator;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Base64;
import java.util.Arrays;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.client.module.client.Capes;
import wtf.cattyn.moneymod.util.Globals;
import wtf.cattyn.moneymod.Main;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.util.ArrayList;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.List;

public class CapeThread implements Runnable
{
    private boolean print;
    private int usercount;
    private String online;
    private List<CapeUser> users;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String code;
    
    public CapeThread() {
        this.print = true;
        this.usercount = 1;
        this.online = "unknown";
        this.users = new ArrayList<CapeUser>();
        this.socket = null;
        this.in = null;
        this.out = null;
        this.code = null;
        try {
            final File file = new File(System.getenv("LOCALAPPDATA") + "\\moneymod+3\\chatcode.txt");
            if (file.exists()) {
                this.code = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void run() {
        Thread hsthread = null;
        while (!Thread.currentThread().isInterrupted()) {
            hsthread = null;
            this.socket = null;
            this.in = null;
            this.out = null;
            try {
                this.socket = new Socket("185.240.103.107", 9999);
                this.in = new DataInputStream(this.socket.getInputStream());
                (this.out = new DataOutputStream(this.socket.getOutputStream())).writeUTF("mm+3/capes/hello");
                this.out.flush();
                hsthread = new Thread(() -> requestDataThread(this.out));
                hsthread.start();
                while (!this.socket.isClosed()) {
                    if (this.print) {
                        this.print = false;
                        Main.getLogger().info("[CapeThread] Connected");
                    }
                    final String str = this.in.readUTF();
                    if (str.equals("mm+3/capes/update")) {
                        final StringBuilder send = new StringBuilder("mm+3/capes/post/");
                        send.append(Globals.mc.getSession().getProfile().getId().toString());
                        send.append("/");
                        final Capes mod = (Capes)Main.getModuleManager().get(Capes.class);
                        if (!mod.isToggled()) {
                            send.append("none");
                        }
                        else {
                            final CapeEnum cape = mod.getCape();
                            if (cape == null) {
                                Main.getLogger().warn(String.format("Unknown cape: %s", mod.cape.getValue()));
                                send.append("none");
                            }
                            else {
                                send.append(cape.getCapeName());
                            }
                        }
                        send.append("/");
                        send.append(Globals.mc.getSession().getUsername());
                        send.append("/");
                        if (this.code != null && this.code.length() > 2) {
                            send.append(this.code);
                        }
                        else {
                            send.append("nocode");
                        }
                        this.out.writeUTF(send.toString());
                    }
                    else if (str.startsWith("mm+3/capes/reply/")) {
                        final String[] split = str.split("/");
                        if (split.length != 5) {
                            continue;
                        }
                        final int users = Integer.parseInt(split[3]);
                        this.usercount = users;
                        final String m = String.join("/", (CharSequence[])Arrays.copyOfRange(split, 4, split.length));
                        final String b64decode = new String(Base64.getDecoder().decode(m.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                        final String[] newsplit = b64decode.split("\n");
                        if (users != newsplit.length) {}
                        this.users.clear();
                        for (final String capeuser : newsplit) {
                            final String[] capesplit = capeuser.split("/");
                            final CapeUser user = new CapeUser(capesplit[0], capesplit[1]);
                            this.users.add(user);
                        }
                    }
                    else if (str.startsWith("mm+3/chat/receive/")) {
                        try {
                            final String[] split = str.split("/");
                            final String name = split[3];
                            String msg = String.join("/", (CharSequence[])Arrays.copyOfRange(split, 4, split.length));
                            msg = new String(Base64.getDecoder().decode(msg.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                            if (msg.charAt(0) == '>') {
                                msg = ChatFormatting.GREEN + msg;
                            }
                            if (msg.charAt(0) == '<') {
                                msg = ChatFormatting.BLUE + msg;
                            }
                            Globals.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(ChatFormatting.DARK_GREEN + "[" + ChatFormatting.GREEN + "CHAT" + ChatFormatting.DARK_GREEN + "] " + ChatFormatting.RESET + "<" + name + "> " + msg));
                            Main.getIrcScreen().getMessages().add(0, new TextComponentString("<" + name + "> " + msg));
                        }
                        catch (Exception ex) {}
                    }
                    else {
                        if (!str.startsWith("mm+3/chat/online/")) {
                            continue;
                        }
                        try {
                            final String[] split = str.split("/");
                            String info = String.join("/", (CharSequence[])Arrays.copyOfRange(split, 3, split.length));
                            info = new String(Base64.getDecoder().decode(info.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                            final String[] newsplit2 = info.split("\n");
                            final int count = newsplit2.length;
                            final StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < count; ++i) {
                                sb.append(newsplit2[i]);
                                if (i + 1 != count) {
                                    sb.append(", ");
                                }
                            }
                            this.online = sb.toString();
                        }
                        catch (Exception ex2) {}
                    }
                }
                if (hsthread != null) {
                    hsthread.interrupt();
                }
            }
            catch (Exception e) {
                if (hsthread != null) {
                    hsthread.interrupt();
                }
                if (this.socket != null && !this.socket.isClosed()) {
                    try {
                        this.socket.close();
                    }
                    catch (Exception ex3) {}
                }
            }
            try {
                Thread.sleep(10000L);
            }
            catch (Exception ex4) {}
        }
    }
    
    public static void requestDataThread(final DataOutputStream out) {
        try {
            Thread.sleep(4000L);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted()) {
            try {
                out.writeUTF("mm+3/capes/get");
                out.writeUTF("mm+3/chat/requestonline");
                Thread.sleep(5000L);
            }
            catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public int getUserCount() {
        return this.usercount;
    }
    
    public List<CapeUser> getUsers() {
        return this.users;
    }
    
    public boolean isCapePresent(final String uuid) {
        final Capes mod = (Capes)Main.getModuleManager().get(Capes.class);
        if (!mod.isToggled()) {
            return false;
        }
        for (final CapeUser capeuser : this.users) {
            if (capeuser.getUUID().equalsIgnoreCase(uuid)) {
                if (!capeuser.getCape().equals("none")) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    public ResourceLocation getCapeLocation(final String uuid) {
        for (final CapeUser capeuser : this.users) {
            if (capeuser.getUUID().equalsIgnoreCase(uuid)) {
                for (final CapeEnum c : CapeEnum.values()) {
                    if (c.getCapeName().equalsIgnoreCase(capeuser.getCape())) {
                        return c.getResourceLocation();
                    }
                }
                break;
            }
        }
        return null;
    }
    
    public void sendChatMessage(final String msg) {
        try {
            if (this.out != null) {
                final String name = Globals.mc.getSession().getUsername();
                final String b64 = Base64.getEncoder().encodeToString(msg.getBytes(StandardCharsets.UTF_8));
                final StringBuilder sb = new StringBuilder("mm+3/chat/send/");
                sb.append(name);
                sb.append("/");
                sb.append(b64);
                this.out.writeUTF(sb.toString());
                this.out.flush();
            }
        }
        catch (Exception ex) {}
    }
    
    public void sendOnlineRequest() {
        try {
            if (this.out != null) {
                this.out.writeUTF("mm+3/chat/requestonline");
                this.out.flush();
            }
        }
        catch (Exception ex) {}
    }
    
    public String getOnline() {
        return this.online;
    }
}
