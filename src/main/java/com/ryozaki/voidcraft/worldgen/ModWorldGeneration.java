package com.ryozaki.voidcraft.worldgen;

import com.ryozaki.voidcraft.VoidCraft;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModWorldGeneration {

    public static final ResourceKey<PlacedFeature> VOID_ORE_PLACED_KEY =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    Identifier.fromNamespaceAndPath(
                            VoidCraft.MOD_ID,
                            "void_ore_placed"
                    )
            );

    public static void initialize() {

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                VOID_ORE_PLACED_KEY
        );

        VoidCraft.LOGGER.info("VoidCraft world generation initialized.");
    }
}