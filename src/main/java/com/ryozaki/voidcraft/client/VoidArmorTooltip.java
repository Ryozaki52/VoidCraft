
package com.ryozaki.voidcraft.client;

import com.ryozaki.voidcraft.item.ModItems;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class VoidArmorTooltip {

    public static void initialize() {

        ItemTooltipCallback.EVENT.register(
                (stack, context, type, tooltip) -> {

                    // 只给四件 Void Armor 添加说明
                    boolean isVoidArmor =
                            stack.is(ModItems.VOID_HELMET)
                                    || stack.is(ModItems.VOID_CHESTPLATE)
                                    || stack.is(ModItems.VOID_LEGGINGS)
                                    || stack.is(ModItems.VOID_BOOTS);

                    if (!isVoidArmor) {
                        return;
                    }

                    tooltip.add(
                            Component.literal(
                                    "Crystal Resonance - Full Set"
                            ).withStyle(ChatFormatting.LIGHT_PURPLE)
                    );

                    tooltip.add(
                            Component.literal(
                                    "Requires all 4 Void Armor pieces"
                            ).withStyle(ChatFormatting.GRAY)
                    );

                    tooltip.add(
                            Component.literal("Full Set Bonus:")
                                    .withStyle(ChatFormatting.AQUA)
                    );

                    tooltip.add(
                            Component.literal("Speed I")
                                    .withStyle(ChatFormatting.GREEN)
                    );

                    tooltip.add(
                            Component.literal(
                                    "Crystal Wave Damage: 8 -> 10"
                            ).withStyle(ChatFormatting.LIGHT_PURPLE)
                    );
                });
    }
}