package com.environs.setup.config;

import java.util.Arrays;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class EnvironsConfigClient {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec CLIENT_SPEC;

	public static final ForgeConfigSpec.ConfigValue<String> DIMENSION_TITLE_CARDS;
	public static final ForgeConfigSpec.ConfigValue<String> BIOME_TITLE_CARDS;
	public static final ForgeConfigSpec.ConfigValue<String> STRUCTURE_TITLE_CARDS;

	public static final ForgeConfigSpec.ConfigValue<String> POSITION_VERTICAL;
	public static final ForgeConfigSpec.ConfigValue<String> POSITION_HORIZONTAL;

	public static final ForgeConfigSpec.ConfigValue<Integer> DIMENSION_COLOR;
	public static final ForgeConfigSpec.ConfigValue<Integer> BIOME_COLOR;
	public static final ForgeConfigSpec.ConfigValue<Integer> STRUCTURE_COLOR;

	public static final ForgeConfigSpec.ConfigValue<Boolean> UNDERLINE;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SHADOW;

	static {
		BUILDER.comment("ENVIRONS CLIENT CONFIG\n");

		BUILDER.comment("In single-player, these settings will affect only you.");
		BUILDER.comment("In multi-player, these settings will still affect only you.");
		BUILDER.comment("These settings have been set to defaults selected by the Environs development team.\n");

		BUILDER.push("TRIGGERS");
		DIMENSION_TITLE_CARDS = BUILDER.comment("first - Display a title card on first discovery.\nevery - Display a title card on every entrance.\nnever - Never display title cards.\nalways - Always display title cards.").defineInList("Dimension Title Cards", "first", Arrays.asList("first", "every", "never", "always"));
		BIOME_TITLE_CARDS = BUILDER.defineInList("Biome Title Cards", "first", Arrays.asList("first", "every", "never", "always"));
		STRUCTURE_TITLE_CARDS = BUILDER.defineInList("Structure Title Cards", "first", Arrays.asList("first", "every", "never", "always"));
		BUILDER.pop();

		BUILDER.push("POSITION");
		POSITION_VERTICAL = BUILDER.comment("top - Display title cards at top of screen.\ncenter - Display title cards at center of screen.\nbottom - Display title cards at bottom of screen.").defineInList("Vertical", "center", Arrays.asList("top", "center", "bottom"));
		POSITION_HORIZONTAL = BUILDER.comment("left - Display title cards at left of screen.\ncenter - Display title cards at center of screen.\nright - Display title cards at right of screen.").defineInList("Horizontal", "center", Arrays.asList("left", "center", "right"));
		BUILDER.pop();

		BUILDER.push("COLOR");
		DIMENSION_COLOR = BUILDER.comment("hex or RGBA color code").defineInRange("Dimension", 0xAAFFFF, 0, 16777215);
		BIOME_COLOR = BUILDER.defineInRange("Biome", 0xAAFFAA, 0, 16777215);
		STRUCTURE_COLOR = BUILDER.defineInRange("Structure", 0xFFFFAA, 0, 16777215);
		BUILDER.pop();

		BUILDER.push("STYLE");
		UNDERLINE = BUILDER.comment("show title cards with underline").define("Underline", true);
		SHADOW = BUILDER.comment("show title cards with shadow").define("Shadow", true);
		BUILDER.pop();

		CLIENT_SPEC = BUILDER.build();
	}
}
