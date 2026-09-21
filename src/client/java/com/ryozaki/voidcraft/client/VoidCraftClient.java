package com.ryozaki.voidcraft.client;
import com.ryozaki.voidcraft.client.VoidArmorTooltip;
import net.fabricmc.api.ClientModInitializer;

public class VoidCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		VoidArmorTooltip.initialize();
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}