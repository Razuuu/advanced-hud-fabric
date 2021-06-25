package io.grayray75.fabric.fpsdisplay.mixin;

import io.grayray75.fabric.fpsdisplay.FpsDisplayMod;
import io.grayray75.fabric.fpsdisplay.config.ConfigManager;
import io.grayray75.fabric.fpsdisplay.config.FpsDisplayConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("TAIL"), method = "render")
    public void render(float tickDelta, CallbackInfo info) {
        FpsDisplayConfig config = ConfigManager.getConfig();

        if (!client.options.debugEnabled && config.enabled && config.textAlpha > 3 && FpsDisplayMod.SHOW_FPS_OVERLAY) {

            String displayString = ((MinecraftClientMixin) client).getCurrentFPS() + " FPS";
            int textPosX = config.offsetLeft;
            int textPosY = config.offsetTop;

            Window window = new Window(client);
            double guiScale = window.method_2469();
            if (guiScale > 0) {
                textPosX /= guiScale;
                textPosY /= guiScale;
            }

            // Prevent FPS-Display to render outside screenspace
            int maxTextPosX = window.getScaledWidth() - client.textRenderer.getStringWidth(displayString);
            int maxTextPosY = window.getScaledHeight() - client.textRenderer.fontHeight;
            textPosX = Math.min(textPosX, maxTextPosX);
            textPosY = Math.min(textPosY, maxTextPosY);

            int textColor = ((config.textAlpha & 0xFF) << 24) | config.textColor;

            if (config.drawWithShadows) {
                client.textRenderer.drawWithShadow(displayString, textPosX, textPosY, textColor);
            } else {
                client.textRenderer.draw(displayString, textPosX, textPosY, textColor);
            }
        }
    }
}
