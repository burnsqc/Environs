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

	public static final ForgeConfigSpec.ConfigValue<String> POSITION_VERTICAL;
	public static final ForgeConfigSpec.ConfigValue<String> POSITION_HORIZONTAL;

	static {
		BUILDER.comment("ENVIRONS CLIENT CONFIG\n");

		BUILDER.comment("In single-player, these settings will affect only you.");
		BUILDER.comment("In multi-player, these settings will still affect only you.");
		BUILDER.comment("These settings have been set to defaults selected by the Environs development team.\n");

		BUILDER.push("TRIGGERS");
		DIMENSION_TITLE_CARDS = BUILDER.comment("first - Display a title card for a dimension on first discovery.\nalways - Display a title card for a dimension on every entrance.\nnever - Never display dimension title cards.").defineInList("Dimension Title Cards", "always", Arrays.asList("first", "always", "never"));
		BIOME_TITLE_CARDS = BUILDER.comment("first - Display a title card for a biome on first discovery.\nalways - Display a title card for a biome on every entrance.\nnever - Never display biome title cards.").defineInList("Biome Title Cards", "first", Arrays.asList("first", "always", "never"));
		BUILDER.pop();

		BUILDER.push("POSITION");
		POSITION_VERTICAL = BUILDER.comment("top - Display title cards at top of screen.\ncenter - Display title cards at center of screen.\nbottom - Display title cards at bottom of screen.").defineInList("Vertical", "top", Arrays.asList("top", "center", "bottom"));
		POSITION_HORIZONTAL = BUILDER.comment("left - Display title cards at left of screen.\ncenter - Display title cards at center of screen.\nright - Display title cards at right of screen.").defineInList("Horizontal", "center", Arrays.asList("left", "center", "right"));
		BUILDER.pop();

		CLIENT_SPEC = BUILDER.build();
	}
}
