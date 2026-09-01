package com.ryozaki.voidcraft.event;

import com.ryozaki.voidcraft.component.ModComponents;
import com.ryozaki.voidcraft.item.ModItems;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import com.ryozaki.voidcraft.VoidCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModMiningEvents {

    public static final TagKey<Block> CRYSTAL_BURST_IMMUNE =
            TagKey.create(
                    Registries.BLOCK,
                    Identifier.fromNamespaceAndPath(
                            VoidCraft.MOD_ID,
                            "crystal_burst_immune"
                    )
            );




    public static void initialize() {

        PlayerBlockBreakEvents.AFTER.register(
                (level, player, pos, state, blockEntity) -> {

                    if (!(level instanceof ServerLevel serverLevel)) {
                        return;
                    }

                    if (!(player instanceof ServerPlayer serverPlayer)) {
                        return;
                    }

                    ItemStack pickaxeStack = player.getMainHandItem();

                    // 必须拿着 Void Pickaxe
                    if (!pickaxeStack.is(ModItems.VOID_PICKAXE)) {
                        return;
                    }

                    // 必须已经充能
                    boolean charged = pickaxeStack.getOrDefault(
                            ModComponents.VOID_CHARGED,
                            false
                    );

                    if (!charged) {
                        return;
                    }

                    /*
                     * 非常重要：
                     * 在额外挖 8 个方块之前先取消充能。
                     *
                     * 因为额外方块也会触发 AFTER 事件。
                     * 如果这里不先设 false，
                     * 就可能产生连续递归挖掘。
                     */
                    pickaxeStack.set(
                            ModComponents.VOID_CHARGED,
                            false
                    );

                    Vec3 look = player.getLookAngle();

                    double absX = Math.abs(look.x);
                    double absY = Math.abs(look.y);
                    double absZ = Math.abs(look.z);

                    /*
                     * 根据玩家视线方向决定 3x3 平面。
                     *
                     * 看地面 / 天花板：
                     * X-Z 平面
                     *
                     * 看东西方向的墙：
                     * Y-Z 平面
                     *
                     * 看南北方向的墙：
                     * X-Y 平面
                     */

                    for (int a = -1; a <= 1; a++) {
                        for (int b = -1; b <= 1; b++) {

                            // 中心方块已经被玩家挖掉
                            if (a == 0 && b == 0) {
                                continue;
                            }

                            BlockPos targetPos;

                            if (absY > absX && absY > absZ) {

                                // 玩家主要朝上 / 朝下
                                // 挖水平的 3x3
                                targetPos = pos.offset(a, 0, b);

                            } else if (absX > absZ) {

                                // 玩家主要朝东 / 西
                                // 挖 Y-Z 平面的 3x3
                                targetPos = pos.offset(0, a, b);

                            } else {

                                // 玩家主要朝南 / 北
                                // 挖 X-Y 平面的 3x3
                                targetPos = pos.offset(a, b, 0);
                            }

                            BlockState targetState =
                                    serverLevel.getBlockState(targetPos);

                            // 空气直接跳过
                            if (targetState.isAir()) {
                                continue;
                            }

                            // 防止破坏基岩等不可破坏方块
                            if (targetState.getDestroySpeed(
                                    serverLevel,
                                    targetPos
                            ) < 0.0F) {
                                continue;
                            }
// 特殊方块免疫 Crystal Burst
                            if (targetState.is(CRYSTAL_BURST_IMMUNE)) {
                                continue;
                            }
                            /*
                             * 用服务器玩家自己的 gameMode 去挖。
                             * 这样比直接 setBlock(AIR) 更好：
                             * 会走正常的玩家挖掘流程。
                             */
                            serverPlayer.gameMode.destroyBlock(targetPos);
                        }
                    }

                    // 紫色爆发特效
                    serverLevel.sendParticles(
                            ParticleTypes.WITCH,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            35,
                            1.1,
                            1.1,
                            1.1,
                            0.05
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.PORTAL,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            40,
                            1.0,
                            1.0,
                            1.0,
                            0.08
                    );

                    player.sendOverlayMessage(
                            net.minecraft.network.chat.Component.literal(
                                    "Crystal Burst!"
                            )
                    );
                }
        );
    }
}