package com.daqem.knot.client.render.item;

import com.daqem.knot.KnotMod;
import com.daqem.knot.api.client.render.item.ItemOverride;
import com.daqem.knot.api.client.render.item.ItemOverrideCondition;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemOverrideManager extends SimplePreparableReloadListener<List<ItemOverride>> {

    private final Map<Item, List<ItemOverride>> overrides = new ConcurrentHashMap<>();

    @Override
    protected @NotNull List<ItemOverride> prepare(ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        List<ItemOverride> parsedOverrides = new ArrayList<>();

        Map<Identifier, Resource> resourceMap = resourceManager.listResources("knot/item_overrides",
                (resourceLocation) -> resourceLocation.getPath().endsWith(".json"));

        for (Map.Entry<Identifier, Resource> entry : resourceMap.entrySet()) {
            Identifier location = entry.getKey();
            try (BufferedReader reader = entry.getValue().openAsReader()) {
                JsonElement jsonElement = GsonHelper.parse(reader);
                ItemOverride override = ItemOverride.CODEC.parse(JsonOps.INSTANCE, jsonElement).getOrThrow();
                parsedOverrides.add(override);
            } catch (Exception e) {
                KnotMod.API.error("Parsing error loading item override {}", location, e);
            }
        }

        return parsedOverrides;
    }

    @Override
    protected void apply(@NotNull List<ItemOverride> parsedOverrides, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        this.overrides.clear();

        for (ItemOverride override : parsedOverrides) {
            for (Holder<Item> itemHolder : override.items()) {
                this.overrides.computeIfAbsent(itemHolder.value(), k -> new ArrayList<>()).add(override);
            }
        }

        this.overrides.values().forEach(list -> list.sort(Comparator.comparingInt(ItemOverride::priority).reversed()));

        KnotMod.API.info("Loaded {} item overrides", parsedOverrides.size());
    }

    @Nullable
    public Identifier getOverrideModel(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        List<ItemOverride> itemOverrides = this.overrides.get(stack.getItem());

        if (itemOverrides != null) {
            for (ItemOverride override : itemOverrides) {
                boolean match = true;
                for (ItemOverrideCondition condition : override.conditions()) {
                    if (!condition.test(stack, level, entity, seed, displayContext)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    return override.model();
                }
            }
        }

        return null;
    }
}