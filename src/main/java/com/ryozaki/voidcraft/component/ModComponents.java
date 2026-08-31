package com.ryozaki.voidcraft.component;

import com.mojang.serialization.Codec;
import com.ryozaki.voidcraft.VoidCraft;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class ModComponents {

    public static final DataComponentType<Boolean> VOID_CHARGED =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    Identifier.fromNamespaceAndPath(
                            VoidCraft.MOD_ID,
                            "void_charged"
                    ),
                    DataComponentType.<Boolean>builder()
                            .persistent(Codec.BOOL)
                            .build()
            );

    public static void initialize() {
        VoidCraft.LOGGER.info("Registering VoidCraft data components");
    }
}