package com.ryozaki.voidcraft.item;

import com.ryozaki.voidcraft.component.ModComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class VoidPickaxeItem extends Item {

    public VoidPickaxeItem(Properties properties) {
        super(properties);
    }

    // 充能后显示附魔闪光
    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getOrDefault(
                ModComponents.VOID_CHARGED,
                false
        ) || super.isFoil(stack);
    }

    // 显示充能状态
    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            TooltipDisplay displayComponent,
            Consumer<Component> textConsumer,
            TooltipFlag type
    ) {

        boolean charged = stack.getOrDefault(
                ModComponents.VOID_CHARGED,
                false
        );

        if (charged) {
            textConsumer.accept(
                    Component.literal("Crystal Charge: READY")
                            .withStyle(ChatFormatting.LIGHT_PURPLE)
            );
        } else {
            textConsumer.accept(
                    Component.literal("Crystal Charge: EMPTY")
                            .withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }

    // Shift + 右键充能
    @Override
    public InteractionResult use(
            Level level,
            Player player,
            InteractionHand hand
    ) {

        ItemStack pickaxeStack = player.getItemInHand(hand);

        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (player.isShiftKeyDown()) {

            ItemStack offhandStack = player.getOffhandItem();

            boolean charged = pickaxeStack.getOrDefault(
                    ModComponents.VOID_CHARGED,
                    false
            );

            // 已经充能
            if (charged) {

                if (!level.isClientSide()) {
                    player.sendOverlayMessage(
                            Component.literal(
                                    "Void Pickaxe is already charged!"
                            )
                    );
                }

                return InteractionResult.SUCCESS;
            }

            // 副手有 Void Crystal
            if (offhandStack.is(ModItems.VOID_CRYSTAL)) {

                if (!level.isClientSide()) {

                    pickaxeStack.set(
                            ModComponents.VOID_CHARGED,
                            true
                    );

                    // Survival 消耗一个水晶
                    // Creative 不消耗
                    if (!player.getAbilities().instabuild) {
                        offhandStack.shrink(1);
                    }

                    player.sendOverlayMessage(
                            Component.literal(
                                    "Void Pickaxe Charged!"
                            )
                    );
                }

                return InteractionResult.SUCCESS;
            }

            // 没有水晶
            if (!level.isClientSide()) {
                player.sendOverlayMessage(
                        Component.literal(
                                "Put a Void Crystal in your offhand."
                        )
                );
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}