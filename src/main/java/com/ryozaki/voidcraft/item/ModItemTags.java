package com.ryozaki.voidcraft.item;

import com.ryozaki.voidcraft.VoidCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final TagKey<Item> REPAIRS_VOID_TOOLS =
            TagKey.create(
                    BuiltInRegistries.ITEM.key(),
                    Identifier.fromNamespaceAndPath(
                            VoidCraft.MOD_ID,
                            "repairs_void_tools"
                    )
            );
}