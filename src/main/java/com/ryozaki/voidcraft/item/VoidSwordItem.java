package com.ryozaki.voidcraft.item;

import com.ryozaki.voidcraft.component.ModComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.PowerParticleOption;
public class VoidSwordItem extends Item {

    public VoidSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(
            Level level,
            Player player,
            InteractionHand hand
    ) {

        ItemStack swordStack = player.getItemInHand(hand);

        // 只处理主手的 Void Sword
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        // Shift + 右键 = 尝试充能
        if (player.isShiftKeyDown()) {

            ItemStack offhandStack = player.getOffhandItem();

            // 检查剑是否已经充能
            boolean charged = swordStack.getOrDefault(
                    ModComponents.VOID_CHARGED,
                    false
            );

            // 已经充能
            if (charged) {
                if (!level.isClientSide()) {
                    player.sendOverlayMessage(
                            Component.literal("Void Sword is already charged!")
                    );
                }

                return InteractionResult.SUCCESS;
            }

            // 检查副手是不是 Void Crystal
            if (offhandStack.is(ModItems.VOID_CRYSTAL)) {

                if (!level.isClientSide()) {

                    // 设置剑为已充能
                    swordStack.set(
                            ModComponents.VOID_CHARGED,
                            true
                    );

                    // 生存模式消耗 1 个 Void Crystal
                    if (!player.getAbilities().instabuild) {
                        offhandStack.shrink(1);
                    }

                    player.sendOverlayMessage(
                            Component.literal("Void Sword Charged!")
                    );
                }

                return InteractionResult.SUCCESS;
            }

            // 副手没有 Void Crystal
            if (!level.isClientSide()) {
                player.sendOverlayMessage(
                        Component.literal(
                                "Put a Void Crystal in your offhand."
                        )
                );
            }

            return InteractionResult.SUCCESS;
        }

        // 普通右键 = 如果已充能，则释放 Crystal Wave
        boolean charged = swordStack.getOrDefault(
                ModComponents.VOID_CHARGED,
                false
        );

        if (charged) {

            if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {

                Vec3 start = player.getEyePosition();
                Vec3 direction = player.getLookAngle().normalize();

                // 暂时让水晶波飞 12 格
                for (double distance = 1.0; distance <= 12.0; distance += 0.20) {

                    Vec3 particlePos = start.add(
                            direction.scale(distance)
                    );

                    // 主体：大量紫色 Portal 粒子
                    serverLevel.sendParticles(
                            ParticleTypes.PORTAL,
                            particlePos.x,
                            particlePos.y,
                            particlePos.z,
                            5,
                            0.18,
                            0.18,
                            0.18,
                            0.02
                    );

                    // 第二层：紫色魔法粒子
                    serverLevel.sendParticles(
                            ParticleTypes.WITCH,
                            particlePos.x,
                            particlePos.y,
                            particlePos.z,
                            3,
                            0.14,
                            0.14,
                            0.14,
                            0.01
                    );

                    // 第三层：紫红色能量雾
                    serverLevel.sendParticles(
                            PowerParticleOption.create(
                                    ParticleTypes.DRAGON_BREATH,
                                    1.0F
                            ),
                            particlePos.x,
                            particlePos.y,
                            particlePos.z,
                            2,
                            0.10,
                            0.10,
                            0.10,
                            0.01
                    );
                }
                Vec3 endPos = start.add(direction.scale(12.0));

                serverLevel.sendParticles(
                        PowerParticleOption.create(
                                ParticleTypes.DRAGON_BREATH,
                                1.0F
                        ),
                        endPos.x,
                        endPos.y,
                        endPos.z,
                        20,
                        0.45,
                        0.45,
                        0.45,
                        0.03
                );

                serverLevel.sendParticles(
                        ParticleTypes.WITCH,
                        endPos.x,
                        endPos.y,
                        endPos.z,
                        15,
                        0.35,
                        0.35,
                        0.35,
                        0.02
                );
                // 发射后消耗充能
                swordStack.set(
                        ModComponents.VOID_CHARGED,
                        false
                );

                player.sendOverlayMessage(
                        Component.literal("Crystal Wave Released!")
                );
            }

            return InteractionResult.SUCCESS;
        }

// 没有充能时普通右键什么也不做
        return InteractionResult.PASS;
    }
}