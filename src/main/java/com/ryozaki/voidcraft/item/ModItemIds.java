package com.ryozaki.voidcraft.item;

import com.ryozaki.voidcraft.VoidCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {

    public static final ResourceKey<Item> VOID_CRYSTAL = create("void_crystal");
    public static final ResourceKey<Item> VOID_SWORD = create("void_sword");
    public static ResourceKey<Item> create(String name) {
        return ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(VoidCraft.MOD_ID, name)
        );
    }
}