// 
// Decompiled by Procyon v0.5.36
// 

package wtf.cattyn.moneymod.api.managment;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.LinkOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.awt.Color;
import com.google.gson.JsonParser;
import java.nio.file.Paths;

import wtf.cattyn.moneymod.client.module.Module;
import wtf.cattyn.moneymod.Main;
import java.io.File;
import wtf.cattyn.moneymod.util.Globals;

public class ConfigManager extends Thread implements Globals
{
    private static final File mainFolder;
    private static final String modulesFolder;
    private static final String FRIEND = "Friends.json";

    public static File getMainFolder() {
        return ConfigManager.mainFolder;
    }

    public void load() {
        try {
            this.loadModules();
            this.loadFriends();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadModules() throws Exception {
        for (final Module m : Main.getModuleManager().get()) {
            this.loadModule(m);
        }
    }

    private void loadModule(final Module m) throws Exception {
        final Path path = Paths.get(ConfigManager.modulesFolder, m.getName() + ".json");
        if (!path.toFile().exists()) {
            return;
        }
        JsonArray jsonElements;
        final String rawJson = this.loadFile(path.toFile());
        JsonObject jsonObject = (new JsonParser()).parse(rawJson).getAsJsonObject();
        if (jsonObject.get("Enabled") != null && jsonObject.get("KeyBind") != null) {
            if (jsonObject.get("Enabled").getAsBoolean() && !m.isException()) {
                m.setToggled(true);
            }

            m.setKey(jsonObject.get("KeyBind").getAsInt());
        }

        Main.getSettingManager().get(m).forEach((s) -> {
            JsonElement settingObject = jsonObject.get(s.getName());
            if (settingObject != null) {
                switch (s.getType()) {
                    case B: {
                        s.setValue(String.valueOf(settingObject.getAsBoolean()));
                        break;
                    }
                    case N: {
                        if (settingObject.getAsDouble() < s.getMax() && settingObject.getAsDouble() > s.getMin()) {
                            s.setValue(String.valueOf((Boolean)(Object)Double.valueOf(settingObject.getAsDouble())));
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case M: {
                        if (s.getModes().contains(settingObject.getAsString())) {
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    case C: {

                        break;
                    }
                }
            }
        });
    }

    private void loadFriends() throws IOException {
        final Path path = Paths.get(ConfigManager.mainFolder.getAbsolutePath(), "Friends.json");
        if (!path.toFile().exists()) {
            return;
        }
        final String rawJson = this.loadFile(path.toFile());
        final JsonObject jsonObject = new JsonParser().parse(rawJson).getAsJsonObject();
        if (jsonObject.get("Friends") != null) {
            final JsonArray friendObject = jsonObject.get("Friends").getAsJsonArray();
            friendObject.forEach(object -> Main.getFriendManager().get(0).add(object.getAsString()));
        }
    }

    public String loadFile(final File file) throws IOException {
        final FileInputStream stream = new FileInputStream(file.getAbsolutePath());
        final StringBuilder resultStringBuilder = new StringBuilder();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    @Override
    public void run() {
        if (!ConfigManager.mainFolder.exists() && !ConfigManager.mainFolder.mkdirs()) {
            System.out.println("Failed to create config folder");
        }
        if (!new File(ConfigManager.modulesFolder).exists() && !new File(ConfigManager.modulesFolder).mkdirs()) {
            System.out.println("Failed to create modules folder");
        }
        try {
            this.saveModules();
            this.saveFriends();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveModules() throws IOException {
        for (final Module m : Main.getModuleManager().get()) {
            this.saveModule(m);
        }
    }

    private void saveModule(final Module m) throws IOException {
        final Path path = Paths.get(ConfigManager.modulesFolder, m.getName() + ".json");
        this.createFile(path);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("Enabled", (JsonElement)new JsonPrimitive(Boolean.valueOf(m.isToggled())));
        jsonObject.add("KeyBind", (JsonElement)new JsonPrimitive((Number)m.getKey()));
        final JsonObject jsonObject2 = null;
        final JsonArray[] jsonColors = new JsonArray[1];
        final Color[] color = new Color[1];
        Main.getSettingManager().get(m).forEach(s -> {
            switch (s.getType()) {
                case M: {
                    jsonObject2.add(s.getName(), (JsonElement)new JsonPrimitive((String)s.getValue()));
                    break;
                }
                case B: {
                    jsonObject2.add(s.getName(), (JsonElement)new JsonPrimitive((Boolean)s.getValue()));
                    break;
                }
                case N: {
                    jsonObject2.add(s.getName(), (JsonElement)new JsonPrimitive((Number)s.getValue()));
                    break;
                }
                case C: {
                    jsonColors[0] = new JsonArray();
                    color[0] = (Color)s.getValue();
                    jsonColors[0].add((Number) color[0].getRed());
                    jsonColors[0].add((Number) color[0].getGreen());
                    jsonColors[0].add((Number) color[0].getBlue());
                    jsonColors[0].add((Number) color[0].getAlpha());
                    jsonColors[0].add(Boolean.valueOf(s.isRainbow()));
                    jsonObject2.add(s.getName(), (JsonElement) jsonColors[0]);
                    break;
                }
            }
            return;
        });
        Files.write(path, ConfigManager.gson.toJson(new JsonParser().parse(jsonObject.toString())).getBytes(), new OpenOption[0]);
    }

    private void saveFriends() throws IOException {
        final Path path = Paths.get(ConfigManager.mainFolder.getAbsolutePath(), "Friends.json");
        this.createFile(path);
        final JsonObject jsonObject = new JsonObject();
        final JsonArray friends = new JsonArray();
        Main.getFriendManager().get(0).forEach(friends::add);
        jsonObject.add("Friends", (JsonElement)friends);
        Files.write(path, ConfigManager.gson.toJson(new JsonParser().parse(jsonObject.toString())).getBytes(), new OpenOption[0]);
    }

    private void createFile(final Path path) {
        if (Files.exists(path, new LinkOption[0])) {
            new File(path.normalize().toString()).delete();
        }
        try {
            Files.createFile(path, (FileAttribute<?>[])new FileAttribute[0]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        mainFolder = new File("moneymod+3");
        modulesFolder = ConfigManager.mainFolder.getAbsolutePath() + "/modules";
    }
}
