package com.ryozaki.voidcraft;
import com.ryozaki.voidcraft.item.ModItems;
import net.fabricmc.api.ModInitializer;
import com.ryozaki.voidcraft.block.ModBlocks;
import com.ryozaki.voidcraft.item.ModItems;
import net.minecraft.resources.Identifier;
import com.ryozaki.voidcraft.component.ModComponents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ryozaki.voidcraft.worldgen.ModWorldGeneration;

public class VoidCraft implements ModInitializer {
	public static final String MOD_ID = "voidcraft";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModComponents.initialize();
		ModItems.initialize();
		ModBlocks.initialize();
		ModWorldGeneration.initialize();
		LOGGER.info("VoidCraft initialized!");
		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
