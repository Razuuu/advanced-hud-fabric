package io.grayray75.fabric.fpsdisplay.config;

import io.grayray75.fabric.fpsdisplay.FpsDisplayMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class ConfigManager {

    private static FpsDisplayConfig config;

    public static FpsDisplayConfig getConfig() {
        return config;
    }

    private static File getConfigFile() {
        return new File(FabricLoader.getInstance().getConfigDirectory(), FpsDisplayMod.MOD_ID + ".json");
    }

    public static void loadConfig() {
        File file = getConfigFile();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            FpsDisplayConfig parsed = FpsDisplayMod.GSON.fromJson(br, FpsDisplayConfig.class);
            if (parsed != null) {
                config = parsed;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load FPS-Display configuration file; reverting to defaults.");
            e.printStackTrace();
        }

        if (config == null) {
            config = new FpsDisplayConfig();
            saveConfig();
        }
    }

    public static void saveConfig() {
        String jsonString = FpsDisplayMod.GSON.toJson(config);

        try {
            FileWriter fileWriter = new FileWriter(getConfigFile());
            fileWriter.write(jsonString);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Couldn't save FPS-Display configuration file.");
            e.printStackTrace();
        }
    }
}
