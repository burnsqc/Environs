package com.environs.setup.config;

import java.util.Arrays;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class EnvironsConfigClient {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec CLIENT_SPEC;

	public static final ForgeConfigSpec.ConfigValue<String> DIMENSION_TITLE_CARDS;
	public static final ForgeConfigSpec.ConfigValue<String> BIOME_TITLE_CARDS;

	static {
		BUILDER.comment("ENVIRONS CLIENT CONFIG\n");

		BUILDER.comment("In single-player, these settings will affect only you.");
		BUILDER.comment("In multi-player, these settings will still affect only you.");
		BUILDER.comment("These settings have been set to defaults selected by the Environs development team.\n");

		BUILDER.push("ENVIRONMENT TITLE CARDS");
		DIMENSION_TITLE_CARDS = BUILDER.comment("first - Display a title card for a dimension on first discovery.\nalways - Display a title card for a dimension on every entrance.\nnever - Never display dimension title cards.").defineInList("Dimension Title Cards", "always", Arrays.asList("first", "always", "never"));
		BIOME_TITLE_CARDS = BUILDER.comment("first - Display a title card for a biome on first discovery.\nalways - Display a title card for a biome on every entrance.\nnever - Never display biome title cards.").defineInList("Biome Title Cards", "first", Arrays.asList("first", "always", "never"));
		BUILDER.pop();

		CLIENT_SPEC = BUILDER.build();
	}
}
