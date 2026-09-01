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
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register((creativeTab) -> creativeTab.accept(VOID_CRYSTAL));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
                .register((creativeTab) -> creativeTab.accept(VOID_SWORD));
        CreativeModeTabEvents.modifyOutputEvent(
                CreativeModeTabs.TOOLS_AND_UTILITIES
        ).register((creativeTab) -> creativeTab.accept(VOID_PICKAXE));
    }
}