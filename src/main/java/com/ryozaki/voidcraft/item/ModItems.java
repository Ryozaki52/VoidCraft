package com.ryozaki.voidcraft.item;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import com.ryozaki.voidcraft.component.ModComponents;
import java.util.function.Function;
import net.minecraft.world.item.equipment.ArmorType;





public class ModItems {

    public static final ToolMaterial VOID_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            1800,
            8.0F,
            3.0F,
            18,
            ModItemTags.REPAIRS_VOID_TOOLS
    );

    public static final Item VOID_CRYSTAL = register(
            ModItemIds.VOID_CRYSTAL,
            Item::new,
            new Item.Properties()
    );
    public static final Item VOID_SWORD = register(
            ModItemIds.VOID_SWORD,
            VoidSwordItem::new,
            new Item.Properties()
                    .sword(
                            VOID_TOOL_MATERIAL,
                            4.0F,
                            -2.4F
                    )
                    .component(
                            ModComponents.VOID_CHARGED,
                            false
                    )
    );
    public static final Item VOID_PICKAXE = register(
            ModItemIds.VOID_PICKAXE,
            VoidPickaxeItem::new,
            new Item.Properties()
                    .pickaxe(
                            VOID_TOOL_MATERIAL,
                            1.0F,
                            -2.8F
                    )
                    .component(
                            ModComponents.VOID_CHARGED,
                            false
                    )
    );
    public static final Item VOID_HELMET = register(
            ModItemIds.VOID_HELMET,
            Item::new,
            new Item.Properties()
                    .humanoidArmor(
                            VoidArmorMaterial.INSTANCE,
                            ArmorType.HELMET
                    )
                    .durability(
                            ArmorType.HELMET.getDurability(
                                    VoidArmorMaterial.BASE_DURABILITY
                            )
                    )
    );
    public static final Item VOID_CHESTPLATE = register(
            ModItemIds.VOID_CHESTPLATE,
            Item::new,
            new Item.Properties()
                    .humanoidArmor(
                            VoidArmorMaterial.INSTANCE,
                            ArmorType.CHESTPLATE
                    )
                    .durability(
                            ArmorType.CHESTPLATE.getDurability(
                                    VoidArmorMaterial.BASE_DURABILITY
                            )
                    )
    );
    public static final Item VOID_LEGGINGS = register(
            ModItemIds.VOID_LEGGINGS,
            Item::new,
            new Item.Properties()
                    .humanoidArmor(
                            VoidArmorMaterial.INSTANCE,
                            ArmorType.LEGGINGS
                    )
                    .durability(
                            ArmorType.LEGGINGS.getDurability(
                                    VoidArmorMaterial.BASE_DURABILITY
                            )
                    )
    );
    public static final Item VOID_BOOTS = register(
            ModItemIds.VOID_BOOTS,
            Item::new,
            new Item.Properties()
                    .humanoidArmor(
                            VoidArmorMaterial.INSTANCE,
                            ArmorType.BOOTS
                    )
                    .durability(
                            ArmorType.BOOTS.getDurability(
                                    VoidArmorMaterial.BASE_DURABILITY
                            )
                    )
    );
    public static Item register(
            ResourceKey<Item> itemKey,
            Function<Item.Properties, Item> itemFactory,
            Item.Properties settings
    ) {
        Item item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {

        // Void Crystal
        CreativeModeTabEvents.modifyOutputEvent(
                CreativeModeTabs.INGREDIENTS
        ).register((creativeTab) -> {
            creativeTab.accept(VOID_CRYSTAL);
        });

        // Void Sword + Void Armor
        CreativeModeTabEvents.modifyOutputEvent(
                CreativeModeTabs.COMBAT
        ).register((creativeTab) -> {
            creativeTab.accept(VOID_SWORD);
            creativeTab.accept(VOID_HELMET);
            creativeTab.accept(VOID_CHESTPLATE);
            creativeTab.accept(VOID_LEGGINGS);
            creativeTab.accept(VOID_BOOTS);
        });


        // Void Pickaxe
        CreativeModeTabEvents.modifyOutputEvent(
                CreativeModeTabs.TOOLS_AND_UTILITIES
        ).register((creativeTab) -> {
            creativeTab.accept(VOID_PICKAXE);
        });
    }





    }
