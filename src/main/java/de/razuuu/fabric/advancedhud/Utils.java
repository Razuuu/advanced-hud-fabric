package de.razuuu.fabric.advancedhud;

import de.razuuu.fabric.advancedhud.config.AdvancedHudConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.profiler.MultiValueDebugSampleLogImpl;

public class Utils {

    public static int getLocalPing() {
        MinecraftClient mc = MinecraftClient.getInstance();

        MultiValueDebugSampleLogImpl pingLog = mc.inGameHud.getDebugHud().getPingLog();

        // 20 should give somewhat the average ping but should still update quickly for lag spikes
        int sampleSize = Math.min(pingLog.getLength(), 20);
        long totalPing = 0;
        for (int i = 0; i < sampleSize; i++) {
            totalPing += pingLog.get(i);
        }
        // if it's 0 something maybe went wrong
        // don't want to divide by 0 that's dumb
        if (totalPing > 0 && sampleSize > 1) {
            return Math.round(totalPing / (float) sampleSize);
        }

        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (networkHandler == null) {
            return -1;
        }

        PlayerListEntry localPlayer = networkHandler.getPlayerListEntry(networkHandler.getProfile().getId());
        if (localPlayer == null) {
            return -1;
        }

        return localPlayer.getLatency();
    }

    public static boolean shouldRenderHud(MinecraftClient client) {
        AdvancedHudConfig config = AdvancedHudMod.CONFIG;
        return !client.options.hudHidden && config.enabled && config.textAlpha > 3 && AdvancedHudMod.SHOW_HUD_OVERLAY && client.player != null;
    }
}
