// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.ui.tokenlogin;

import java.util.Iterator;
import java.io.Writer;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import wtf.cattyn.moneymod.Main;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.util.List;

public class AltManager
{
    private List<Account> accounts;
    
    public AltManager() {
        final File cfg = new File("tokenlogin.json");
        if (!cfg.exists()) {
            try {
                final FileWriter writer = new FileWriter(cfg);
                writer.write("[]");
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            this.accounts = new ArrayList<Account>();
            return;
        }
        try {
            final BufferedReader br = new BufferedReader(new FileReader(cfg));
            final Type type = new TypeToken<List<Account>>() {}.getType();
            this.accounts = (List<Account>)new Gson().fromJson((Reader)br, type);
            if (this.accounts == null) {
                this.accounts = new ArrayList<Account>();
            }
            Main.getLogger().info("Loaded " + this.accounts.size() + " accounts");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (final Writer writer = new FileWriter("tokenlogin.json")) {
            gson.toJson((Object)this.accounts, (Appendable)writer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean addAccount(final Account account) {
        for (final Account _account : this.accounts) {
            if (_account == null) {
                continue;
            }
            if (_account.username.equalsIgnoreCase(account.username)) {
                return false;
            }
        }
        this.accounts.add(account);
        this.save();
        return true;
    }
    
    public boolean removeAccount(final Account account) {
        Account rem = null;
        for (final Account _account : this.accounts) {
            if (_account == null) {
                continue;
            }
            if (_account.username.equalsIgnoreCase(account.username)) {
                rem = _account;
                break;
            }
        }
        if (rem != null) {
            this.accounts.remove(rem);
            this.save();
            return true;
        }
        return false;
    }
    
    public String getLastUsedAccount() {
        long time = 0L;
        String ret = "none";
        for (final Account account : this.accounts) {
            if (account == null) {
                continue;
            }
            if (account.timeused < time) {
                continue;
            }
            time = account.timeused;
            ret = account.username;
        }
        return ret;
    }
    
    public List<Account> getAccounts() {
        return this.accounts;
    }
}
