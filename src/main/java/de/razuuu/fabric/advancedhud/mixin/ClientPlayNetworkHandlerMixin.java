package de.razuuu.fabric.advancedhud.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.razuuu.fabric.advancedhud.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	// It doesn't send the packet if F3 is closed
	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/DebugHud;shouldShowPacketSizeAndPingCharts()Z"))
	private boolean shouldShowPacketSizeAndPingCharts(DebugHud instance, Operation<Boolean> original) {
		return Utils.shouldRenderHud(MinecraftClient.getInstance()) || original.call(instance);
	}
}
