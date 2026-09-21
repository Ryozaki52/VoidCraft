package com.ryozaki.voidcraft.item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.Item.TooltipContext;
import java.util.function.Consumer;
import java.util.List;
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
import com.ryozaki.voidcraft.armor.ModArmorEffects;
import net.minecraft.server.level.ServerPlayer;
public class VoidSwordItem extends Item {

    public VoidSwordItem(Properties properties) {
        super(properties);
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getOrDefault(ModComponents.VOID_CHARGED, false)
                || super.isFoil(stack);
    }
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


                Vec3 end = start.add(direction.scale(12.0));

// 做一个覆盖整条水晶波的检测区域
                AABB searchBox = player.getBoundingBox()
                        .expandTowards(direction.scale(12.0))
                        .inflate(1.0);

// 找到范围内所有活着的生物
                List<LivingEntity> targets = serverLevel.getEntitiesOfClass(
                        LivingEntity.class,
                        searchBox,
                        entity -> entity != player
                                && entity.isAlive()
                                && !entity.isSpectator()
                );

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

                LivingEntity closestTarget = null;
                double closestDistance = Double.MAX_VALUE;

                for (LivingEntity target : targets) {

                    // 把实体 hitbox 稍微扩大，让水晶波更容易命中
                    AABB hitbox = target.getBoundingBox().inflate(0.75);

                    // 检查从玩家眼睛到 12 格终点的直线是否穿过实体
                    if (hitbox.clip(start, end).isPresent()) {

                        double distance = player.distanceToSqr(target);

                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestTarget = target;
                        }
                    }
                }
                if (closestTarget != null) {


                    float waveDamage = 8.0F;

// 穿齐 Void Armor 时，水晶波伤害提高到 10
                    if (player instanceof ServerPlayer serverPlayer
                            && ModArmorEffects.hasFullVoidArmor(serverPlayer)) {

                        waveDamage = 10.0F;
                    }

// 对命中的敌人造成伤害
                    closestTarget.hurtServer(
                            serverLevel,
                            serverLevel.damageSources().playerAttack(player),
                            waveDamage
                    );

                    Vec3 hitPos = closestTarget.position().add(
                            0,
                            closestTarget.getBbHeight() * 0.5,
                            0
                    );

                    // 命中时爆出更多紫色粒子
                    serverLevel.sendParticles(
                            ParticleTypes.WITCH,
                            hitPos.x,
                            hitPos.y,
                            hitPos.z,
                            25,
                            0.45,
                            0.45,
                            0.45,
                            0.03
                    );

                    serverLevel.sendParticles(
                            PowerParticleOption.create(
                                    ParticleTypes.DRAGON_BREATH,
                                    1.0F
                            ),
                            hitPos.x,
                            hitPos.y,
                            hitPos.z,
                            20,
                            0.35,
                            0.35,
                            0.35,
                            0.03
                    );
                }
            }

            return InteractionResult.SUCCESS;
        }

// 没有充能时普通右键什么也不做
        return InteractionResult.PASS;
    }
}