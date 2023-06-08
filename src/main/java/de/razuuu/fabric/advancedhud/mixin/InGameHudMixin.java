package de.razuuu.fabric.advancedhud.mixin;

import de.razuuu.fabric.advancedhud.AdvancedHudMod;
import de.razuuu.fabric.advancedhud.Utils;
import de.razuuu.fabric.advancedhud.config.AdvancedHudConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(at = @At("TAIL"), method = "render")
    public void render(DrawContext context, float tickDelta, CallbackInfo info) throws Exception {
        MinecraftClient client = MinecraftClient.getInstance();
        AdvancedHudConfig config = AdvancedHudMod.CONFIG;

        if (!client.options.debugEnabled && config.enabled && config.textAlpha > 3 && AdvancedHudMod.SHOW_HUD_OVERLAY && client.player != null) {
            double guiScale = client.getWindow().getScaleFactor();

            List<String> textLines = List.of(
                    "FPS: " + ((MinecraftClientMixin) client).getCurrentFPS(),
                    "Coordinates: " + Math.round(client.player.getX()) + " " + Math.round(client.player.getY()) + " " + Math.round(client.player.getZ()),
                    "Ping: " + Utils.getLocalPing()
            );

            // Prevent Advanced-HUD to render outside screenspace
            int maxTextPosX = client.getWindow().getScaledWidth() - client.textRenderer.getWidth(this.getLongestString(textLines));
            int maxTextPosY = client.getWindow().getScaledHeight() - client.textRenderer.fontHeight;
            int textPosX = Math.min(Math.round(config.offsetLeft / (float) guiScale), maxTextPosX);
            int textPosY = Math.min(Math.round(config.offsetTop / (float) guiScale), maxTextPosY);

            int textColor = ((config.textAlpha & 0xFF) << 24) | config.textColor;

            for (int i = 0; i < textLines.size(); i++) {
                String line = textLines.get(i);
                int linePosY = textPosY + i*(client.textRenderer.fontHeight + config.textSpacing);
                context.drawText(client.textRenderer, line, textPosX, linePosY, textColor, config.drawWithShadows);
            }
        }
    }

    private String getLongestString(List<String> textLines) {
        return textLines
                .stream()
                .reduce("",
                        (longestText, text) -> longestText.length() < text.length() ? text : longestText
                );
    }
}
