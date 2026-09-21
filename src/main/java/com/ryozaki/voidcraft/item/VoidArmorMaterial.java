package com.ryozaki.voidcraft.item;

import com.ryozaki.voidcraft.VoidCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class VoidArmorMaterial {

    public static final int BASE_DURABILITY = 35;

    public static final ResourceKey<EquipmentAsset> VOID_ARMOR_MATERIAL_KEY =
            ResourceKey.create(
                    EquipmentAssets.ROOT_ID,
                    Identifier.fromNamespaceAndPath(
                            VoidCraft.MOD_ID,
                            "void"
                    )
            );

    public static final TagKey<Item> REPAIRS_VOID_ARMOR =
            TagKey.create(
                    BuiltInRegistries.ITEM.key(),
                    Identifier.fromNamespaceAndPath(
                            VoidCraft.MOD_ID,
                            "repairs_void_armor"
                    )
            );

    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
            BASE_DURABILITY,

            Map.of(
                    ArmorType.HELMET, 3,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),

            18,
            SoundEvents.ARMOR_EQUIP_DIAMOND,

            2.5F,
            0.05F,

            REPAIRS_VOID_ARMOR,
            VOID_ARMOR_MATERIAL_KEY
    );
}