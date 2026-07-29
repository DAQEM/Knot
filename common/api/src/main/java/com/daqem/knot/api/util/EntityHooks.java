package com.daqem.knot.api.util;

import com.daqem.knot.api.Logger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntitySpawnRequest;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public interface EntityHooks {

    /**
     * Generates loot for an entity as if it was killed by a player using a specific weapon.
     *
     * @param level  The server level.
     * @param entity The entity to generate loot for.
     * @param player The player simulating the kill.
     * @param weapon The weapon used (determines looting level).
     * @return A list of generated item stacks.
     */
    static List<ItemStack> generateLoot(ServerLevel level, Entity entity, Player player, ItemStack weapon) {
        ResourceKey<LootTable> lootTableKey = entity.getType().getDefaultLootTable().get();
        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(lootTableKey);

        Holder<Enchantment> lootingEnchantment = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .get(Enchantments.LOOTING)
                .orElseThrow(() -> new IllegalStateException("Looting enchantment not found"));

        int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(lootingEnchantment, weapon);
        ItemStack simulatedWeapon = new ItemStack(Items.DIAMOND_SWORD);

        if (lootingLevel > 0) {
            simulatedWeapon.enchant(lootingEnchantment, lootingLevel);
        }

        // Emulate player holding the weapon
        ItemStack previousMainHand = player.getMainHandItem();
        player.setItemInHand(InteractionHand.MAIN_HAND, simulatedWeapon);

        Holder<DamageType> damageTypeHolder = level.registryAccess()
                .lookupOrThrow(Registries.DAMAGE_TYPE)
                .get(DamageTypes.PLAYER_ATTACK)
                .orElseThrow(() -> new IllegalStateException("Damage type not found"));
        DamageSource damageSource = new DamageSource(damageTypeHolder, player);

        LootParams lootParams = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                .withParameter(LootContextParams.ATTACKING_ENTITY, player)
                .withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, player)
                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .create(LootContextParamSets.ENTITY);

        List<ItemStack> loot = lootTable.getRandomItems(lootParams);

        // Restore player's original item
        player.setItemInHand(InteractionHand.MAIN_HAND, previousMainHand);

        return loot;
    }

    /**
     * Serializes an entity to a CompoundTag safely, excluding volatile data like Fire and HurtTime.
     *
     * @param entity The entity to serialize.
     * @return The serialized CompoundTag.
     */
    static CompoundTag serializeEntity(Entity entity) {
        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(entity.problemPath(), Logger.LOGGER)) {
            TagValueOutput mobData = TagValueOutput.createWithContext(scopedCollector, entity.registryAccess());
            entity.saveWithoutId(mobData);

            mobData.store("Rotation", Vec2.CODEC, new Vec2(entity.getYRot(), entity.getXRot()));
            mobData.discard("Fire");
            mobData.discard("HurtTime");

            return mobData.buildResult();
        }
    }

    /**
     * Deserializes an entity from a CompoundTag and an Identifier at a specific position.
     *
     * @param mobData  The serialized entity data.
     * @param entityId The identifier of the entity type.
     * @param level    The level to spawn the entity in.
     * @param pos      The position to spawn the entity at.
     * @return The deserialized Entity, or null if it could not be created.
     */
    static Entity deserializeEntity(CompoundTag mobData, Identifier entityId, Level level, BlockPos pos) {
        CompoundTag dataCopy = mobData.copy();

        ListTag mobPos = new ListTag();
        mobPos.add(DoubleTag.valueOf(pos.getX() + 0.5));
        mobPos.add(DoubleTag.valueOf(pos.getY()));
        mobPos.add(DoubleTag.valueOf(pos.getZ() + 0.5));
        dataCopy.put("Pos", mobPos);
        dataCopy.putString("id", entityId.toString());

        return EntityType.loadEntityRecursive(dataCopy, level, new EntitySpawnRequest(EntitySpawnReason.MOB_SUMMONED, true), e -> e);
    }
}