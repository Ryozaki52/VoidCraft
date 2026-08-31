package com.ryozaki.voidcraft.block;

import com.ryozaki.voidcraft.VoidCraft;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

public class ModBlockItemIds {

    public static final BlockItemId VOID_CRYSTAL_BLOCK =
            create("void_crystal_block");
    public static final BlockItemId VOID_ORE =
            create("void_ore");
    public static final BlockItemId DEEPSLATE_VOID_ORE =
            create("deepslate_void_ore");
    private static BlockItemId create(String name) {
        Identifier id =
                Identifier.fromNamespaceAndPath(VoidCraft.MOD_ID, name);

        return BlockItemId.create(id, id);
    }

}
