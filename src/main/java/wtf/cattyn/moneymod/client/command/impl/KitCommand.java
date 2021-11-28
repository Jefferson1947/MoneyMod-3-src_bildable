//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Jeffe\Desktop\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.client.command.impl;

import java.util.List;
import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import wtf.cattyn.moneymod.client.module.combat.Regear;
import java.io.Reader;
import com.google.gson.JsonArray;
import java.io.FileReader;
import com.google.gson.Gson;
import java.io.FileWriter;
import net.minecraft.item.Item;
import java.util.ArrayList;
import wtf.cattyn.moneymod.util.impl.ChatUtil;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import wtf.cattyn.moneymod.client.command.Command;

public class KitCommand extends Command
{
    public KitCommand() {
        super("kit <create|set|del|list> <name>", new String[] { "kit", "kits", "kitcmd", "regear", "gear" });
    }
    
    public Map<String, Integer> fuckjson(final String a, final int b) {
        final Map<String, Integer> ret = new HashMap<String, Integer>();
        ret.put(a, b);
        return ret;
    }
    
    @Override
    public void exec(String[] str) {
        str = Arrays.copyOfRange(str, 1, str.length);
        int cmd = -1;
        if (str.length < 2) {
            if (str[0].equalsIgnoreCase("list")) {
                cmd = 0;
            }
            else {
                this.printUsage();
            }
        }
        else {
            final String lowerCase = str[0].toLowerCase();
            switch (lowerCase) {
                case "list": {
                    cmd = 0;
                    break;
                }
                case "create": {
                    cmd = 1;
                    break;
                }
                case "set": {
                    cmd = 2;
                    break;
                }
                case "del": {
                    cmd = 3;
                    break;
                }
                default: {
                    this.printUsage();
                    break;
                }
            }
        }
        final File folder = new File("moneymod+3/kits");
        switch (cmd) {
            case 0: {
                ChatUtil.sendMessage(String.format("Found %d kits", folder.listFiles().length));
                for (final File file : folder.listFiles()) {
                    final String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                    ChatUtil.sendMessage(String.format("> %s", name));
                }
                break;
            }
            case 1: {
                final String kit = str[1];
                if (new File(String.format("moneymod+3/kits/%s.json", kit)).exists()) {
                    ChatUtil.sendMessage(String.format("Kit %s already exists!", kit));
                    break;
                }
                final List<Map<String, Integer>> items = new ArrayList<Map<String, Integer>>();
                for (int i = 9; i <= 44; ++i) {
                    if (KitCommand.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                        final int itemid = Item.getIdFromItem(KitCommand.mc.player.inventoryContainer.getSlot(i).getStack().getItem());
                        items.add(this.fuckjson(Integer.toString(i - 9), itemid));
                    }
                }
                try {
                    final FileWriter writer = new FileWriter(String.format("moneymod+3/kits/%s.json", kit));
                    new Gson().toJson((Object)items, (Appendable)writer);
                    writer.close();
                    ChatUtil.sendMessage(String.format("Created %s", kit));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                final String kitname = str[1];
                final File file2 = new File(String.format("moneymod+3/kits/%s.json", kitname));
                if (!file2.exists()) {
                    ChatUtil.sendMessage(String.format("Kit %s not found", kitname));
                    break;
                }
                try {
                    final FileReader reader = new FileReader(file2);
                    final JsonArray arr = (JsonArray)new Gson().fromJson((Reader)reader, (Class)JsonArray.class);
                    reader.close();
                    final Regear mod = (Regear)Main.getModuleManager().get(Regear.class);
                    mod.setCurrentItems(arr);
                    ChatUtil.sendMessage(String.format("Set kit to %s", kitname));
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                break;
            }
            case 3: {
                final String delkit = str[1];
                final File kitfile = new File(String.format("moneymod+3/kits/%s.json", delkit));
                if (!kitfile.exists()) {
                    ChatUtil.sendMessage(String.format("Kit %s not found", delkit));
                    break;
                }
                kitfile.delete();
                ChatUtil.sendMessage(String.format("Deleted kit %s", delkit));
                break;
            }
        }
    }
}
