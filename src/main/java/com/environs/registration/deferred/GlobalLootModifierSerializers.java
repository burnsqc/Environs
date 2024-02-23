package com.environs.registration.deferred;

import com.environs.Environs;
import com.environs.listeners.BlueMoonLootModifier;
import com.environs.listeners.FortuneMoonLootModifier;
import com.environs.listeners.HarvestMoonLootModifier;
import com.mojang.serialization.Codec;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class GlobalLootModifierSerializers {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Environs.MOD_ID);

	public static void init() {
		GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(Environs.MOD_EVENT_BUS);
	}

	public static final RegistryObject<Codec<BlueMoonLootModifier>> BLUE_MOON_LOOT_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("gameplay/fishing/blue_moon_loot_modifier", BlueMoonLootModifier.CODEC);
	public static final RegistryObject<Codec<FortuneMoonLootModifier>> FORTUNE_MOON_LOOT_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("blocks/fortune_moon_loot_modifier", FortuneMoonLootModifier.CODEC);
	public static final RegistryObject<Codec<HarvestMoonLootModifier>> HARVEST_MOON_LOOT_MODIFIER = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("blocks/harvest_moon_loot_modifier", HarvestMoonLootModifier.CODEC);
}
