package com.environs.setup.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class RareMoonConfigCommon {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec COMMON_SPEC;

	public static final ForgeConfigSpec.ConfigValue<Integer> RARE_MOON_RARITY;

	static {
		BUILDER.comment("RAREMOON COMMON CONFIG\n");

		BUILDER.comment("In single-player, these settings will affect only you.");
		BUILDER.comment("In multi-player, these settings will affect all players on the server.  Please consider your players when making changes.");
		BUILDER.comment("These settings have been set to defaults selected by the Rare Moon development team.\n");

		BUILDER.push("RARE MOON RARITY");
		RARE_MOON_RARITY = BUILDER.comment("Percent chance of a rare moon appearance each night.  Specify a number from 0 to 100 inclusive").defineInRange("Rarity", 50, 0, 100);
		BUILDER.pop();

		COMMON_SPEC = BUILDER.build();
	}
}
