//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.tokenlogin;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import net.minecraft.client.Minecraft;
import wtf.cattyn.moneymod.mixin.ducks.IMinecraft;
import net.minecraft.util.Session;
import java.util.Iterator;
import wtf.cattyn.moneymod.Main;

public class Manager extends Thread
{
    private State state;
    private String username;
    private String accessToken;
    private String clientToken;
    
    public Manager() {
        this.state = State.LOGGEDIN;
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (this.state == State.LOGGINGIN) {
                boolean good = false;
                boolean uuidfail = false;
                try {
                    if (!this.isTokenValid()) {
                        if (this.clientToken != null && this.clientToken.length() > 0) {
                            this.state = State.ERROR_PRE;
                            if (this.refreshToken()) {
                                Main.getLogger().info("Refreshed access token of " + this.username);
                                good = true;
                            }
                        }
                    }
                    else {
                        good = true;
                    }
                    if (good) {
                        final String uuid = this.getUUID(this.username);
                        if (uuid != null) {
                            this.changeSession(uuid);
                            Account account = null;
                            for (final Account _account : Main.getAltManager().getAccounts()) {
                                if (_account.username.equalsIgnoreCase(this.username)) {
                                    account = _account;
                                    break;
                                }
                            }
                            if (account != null) {
                                Main.getAltManager().getAccounts().remove(account);
                                account.accessToken = this.accessToken;
                                Main.getAltManager().addAccount(account);
                            }
                        }
                        else {
                            good = false;
                            uuidfail = true;
                            this.state = State.ERROR_UUID;
                        }
                    }
                }
                catch (Exception loginexception) {
                    loginexception.printStackTrace();
                }
                this.username = "";
                this.accessToken = "";
                this.clientToken = "";
                if (good) {
                    this.state = State.LOGGEDIN;
                }
                else if (!uuidfail) {
                    this.state = State.ERROR;
                }
            }
            try {
                Thread.sleep(10L);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void changeSession(final String uuid) {
        final Session session = new Session(this.username, uuid, this.accessToken, "mojang");
        ((IMinecraft)Minecraft.getMinecraft()).setClientSession(session);
    }
    
    public String getUUID(final String username) {
        try {
            final StringBuilder sb = new StringBuilder();
            final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            final JsonObject json = new JsonParser().parse(sb.toString()).getAsJsonObject();
            return json.get("id").getAsString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean isTokenValid() {
        try {
            final JsonObject json = new JsonObject();
            json.addProperty("accessToken", this.accessToken);
            final HttpURLConnection connection = (HttpURLConnection)new URL("https://authserver.mojang.com/validate").openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            final OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            final int code = connection.getResponseCode();
            connection.disconnect();
            return code == 204;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean refreshToken() {
        try {
            final JsonObject json = new JsonObject();
            json.addProperty("accessToken", this.accessToken);
            json.addProperty("clientToken", this.clientToken);
            final HttpURLConnection connection = (HttpURLConnection)new URL("https://authserver.mojang.com/refresh").openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            final OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            if (connection.getResponseCode() == 403) {
                return false;
            }
            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            connection.disconnect();
            final JsonObject parsed = new JsonParser().parse(sb.toString()).getAsJsonObject();
            if (parsed.get("error") != null) {
                return false;
            }
            this.accessToken = parsed.get("accessToken").getAsString();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void login(final String username, final String accessToken, final String clientToken) {
        if (username == null || accessToken == null || username.equals("") || accessToken.equals("")) {
            return;
        }
        this.username = username;
        this.accessToken = accessToken;
        this.clientToken = clientToken;
        this.state = State.LOGGINGIN;
    }
    
    public String getManagerState() {
        switch (this.state) {
            case LOGGINGIN: {
                return "Logging in...";
            }
            case LOGGEDIN: {
                return "Currently logged in as " + ChatFormatting.GREEN + this.getPlayerName();
            }
            case ERROR_PRE: {
                return ChatFormatting.YELLOW + "Trying to refresh access token using client token";
            }
            case ERROR_UUID: {
                return ChatFormatting.RED + "Failed to get UUID";
            }
            case ERROR: {
                return ChatFormatting.RED + "Invalid access token";
            }
            default: {
                return ChatFormatting.RED + "You shouldn't be able to see this";
            }
        }
    }
    
    public void resetState() {
        this.state = State.LOGGEDIN;
    }
    
    public String getPlayerName() {
        try {
            return Minecraft.getMinecraft().getSession().getUsername();
        }
        catch (Exception e) {
            return "unknown";
        }
    }
    
    enum State
    {
        LOGGEDIN, 
        LOGGINGIN, 
        ERROR_PRE, 
        ERROR_UUID, 
        ERROR;
    }
}
