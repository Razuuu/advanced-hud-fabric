package io.grayray75.fabric.fpsdisplay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.grayray75.fabric.fpsdisplay.config.ConfigManager;
import io.grayray75.fabric.fpsdisplay.config.FpsDisplayConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;

@Environment(EnvType.CLIENT)
public class FpsDisplayMod implements ClientModInitializer {

    public static final String MOD_ID = "fpsdisplay";
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static boolean SHOW_FPS_OVERLAY;

    @Override
    public void onInitializeClient() {
        LogManager.getLogger().info("Initializing FPS-Display Mod");

        ConfigManager.loadConfig();
        FpsDisplayConfig config = ConfigManager.getConfig();
        SHOW_FPS_OVERLAY = config.enabled;

        KeyBinding binding_toggleOverlay = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle FPS overlay", Keyboard.KEY_NONE, "FPS-Display"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (binding_toggleOverlay.wasPressed() && !config.holdKeyToShowFps) {
                config.enabled = !config.enabled;
            }
            if (config.holdKeyToShowFps) {
                SHOW_FPS_OVERLAY = binding_toggleOverlay.isPressed();
            } else {
                SHOW_FPS_OVERLAY = config.enabled;
            }
        });
    }
}
