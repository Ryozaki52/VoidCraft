
package com.ryozaki.voidcraft.armor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import com.ryozaki.voidcraft.item.ModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.server.level.ServerPlayer;

public class ModArmorEffects {

    // 检查玩家是否穿齐四件 Void Armor
    public static boolean hasFullVoidArmor(ServerPlayer player) {

        return player.getItemBySlot(EquipmentSlot.HEAD)
                .is(ModItems.VOID_HELMET)

                && player.getItemBySlot(EquipmentSlot.CHEST)
                .is(ModItems.VOID_CHESTPLATE)

                && player.getItemBySlot(EquipmentSlot.LEGS)
                .is(ModItems.VOID_LEGGINGS)

                && player.getItemBySlot(EquipmentSlot.FEET)
                .is(ModItems.VOID_BOOTS);
    }



    public static void initialize() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            // 每20 Tick检查一次
            if (server.getTickCount() % 20 != 0) {
                return;
            }

            for (ServerPlayer player :
                    server.getPlayerList().getPlayers()) {

                if (hasFullVoidArmor(player)) {

                    // 保留 Speed I
                    player.addEffect(
                            new MobEffectInstance(
                                    MobEffects.SPEED,
                                    30,
                                    0,
                                    true,
                                    false,
                                    true
                            )
                    );

                    if (player.level() instanceof ServerLevel serverLevel) {

                        // 紫色 Portal 粒子
                        serverLevel.sendParticles(
                                ParticleTypes.PORTAL,
                                player.getX(),
                                player.getY() + 1.0,
                                player.getZ(),
                                8,
                                0.45,
                                0.65,
                                0.45,
                                0.02
                        );

                        // 紫色 Witch 粒子
                        serverLevel.sendParticles(
                                ParticleTypes.WITCH,
                                player.getX(),
                                player.getY() + 1.0,
                                player.getZ(),
                                5,
                                0.4,
                                0.6,
                                0.4,
                                0.01
                        );
                    }
                }
            }
        });
    }
    }
