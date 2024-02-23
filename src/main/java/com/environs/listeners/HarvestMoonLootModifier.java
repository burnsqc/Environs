package com.environs.listeners;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.environs.world.level.saveddata.RareMoonOverworldExtension;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class HarvestMoonLootModifier extends LootModifier {
	public static final Supplier<Codec<HarvestMoonLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, HarvestMoonLootModifier::new)));

	public HarvestMoonLootModifier(final LootItemCondition[] conditions) {
		super(conditions);
	}

	@Nonnull
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (context.getLevel().dimensionTypeId() == BuiltinDimensionTypes.OVERWORLD) {
			RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(context.getLevel().getServer());
			if (data.getMoon() == 3) {
				for (ItemStack loot : generatedLoot) {
					if (loot.getItem() == Items.WHEAT || loot.getItem() == Items.WHEAT_SEEDS || loot.getItem() == Items.CARROT || loot.getItem() == Items.POTATO || loot.getItem() == Items.BEETROOT || loot.getItem() == Items.BEETROOT_SEEDS || loot.getItem() == Items.MELON_SEEDS || loot.getItem() == Items.PUMPKIN_SEEDS || loot.getItem() == Items.TORCHFLOWER_SEEDS || loot.getItem() == Items.COCOA_BEANS || loot.getItem() == Items.PITCHER_POD) {
						loot.setCount(loot.getCount() * 2);
					}
				}
			}
		}
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}
