package com.ryozaki.voidcraft.item;

import com.ryozaki.voidcraft.VoidCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {

    public static final ResourceKey<Item> VOID_CRYSTAL = create("void_crystal");
    public static final ResourceKey<Item> VOID_SWORD = create("void_sword");
    public static final ResourceKey<Item> VOID_PICKAXE = create("void_pickaxe");
    public static final ResourceKey<Item> VOID_HELMET =
            create("void_helmet");
    public static final ResourceKey<Item> VOID_CHESTPLATE =
            create("void_chestplate");
    public static final ResourceKey<Item> VOID_LEGGINGS =
            create("void_leggings");
    public static final ResourceKey<Item> VOID_BOOTS =
            create("void_boots");
    public static ResourceKey<Item> create(String name) {
        return ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(VoidCraft.MOD_ID, name)
        );
    }
}