package com.environs.config;

import com.environs.Environs;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class EnvironsConfigClient {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec CLIENT_SPEC;

	public static final ForgeConfigSpec.EnumValue<Trigger> DIMENSION_TITLE_CARDS;
	public static final ForgeConfigSpec.EnumValue<Trigger> BIOME_TITLE_CARDS;
	public static final ForgeConfigSpec.EnumValue<Trigger> STRUCTURE_TITLE_CARDS;

	public static final ForgeConfigSpec.EnumValue<NameSize> DIMENSION_SIZE;
	public static final ForgeConfigSpec.EnumValue<NameSize> BIOME_SIZE;
	public static final ForgeConfigSpec.EnumValue<NameSize> STRUCTURE_SIZE;

	public static final ForgeConfigSpec.EnumValue<PositionVertical> POSITION_VERTICAL;
	public static final ForgeConfigSpec.EnumValue<PositionHorizontal> POSITION_HORIZONTAL;

	public static final ForgeConfigSpec.ConfigValue<Integer> DIMENSION_COLOR;
	public static final ForgeConfigSpec.ConfigValue<Integer> BIOME_COLOR;
	public static final ForgeConfigSpec.ConfigValue<Integer> STRUCTURE_COLOR;

	public static final ForgeConfigSpec.ConfigValue<Boolean> UNDERLINE;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SHADOW;

	public static final ForgeConfigSpec.EnumValue<BackdropStyle> BACKDROP_STYLE;
	public static final ForgeConfigSpec.ConfigValue<Integer> BACKDROP_COLOR;

	public static final ForgeConfigSpec.EnumValue<AudioAlert> AUDIO_ALERT;

	static {
		BUILDER.comment("ENVIRONS CLIENT CONFIG\n");

		BUILDER.comment("In single-player, these settings will affect only you.");
		BUILDER.comment("In multi-player, these settings will still affect only you.");
		BUILDER.comment("These settings have been set to defaults selected by the Environs development team.\n");

		BUILDER.push("TRIGGERS");
		DIMENSION_TITLE_CARDS = BUILDER.defineEnum("Dimension", Trigger.FIRST);
		BIOME_TITLE_CARDS = BUILDER.defineEnum("Biome", Trigger.FIRST);
		STRUCTURE_TITLE_CARDS = BUILDER.defineEnum("Structure", Trigger.FIRST);
		BUILDER.pop();

		BUILDER.push("SIZE");
		DIMENSION_SIZE = BUILDER.defineEnum("Dimension", NameSize.LARGE);
		BIOME_SIZE = BUILDER.defineEnum("Biome", NameSize.MEDIUM);
		STRUCTURE_SIZE = BUILDER.defineEnum("Structure", NameSize.SMALL);
		BUILDER.pop();

		BUILDER.push("POSITION");
		POSITION_VERTICAL = BUILDER.defineEnum("Vertical", PositionVertical.MIDDLE);
		POSITION_HORIZONTAL = BUILDER.defineEnum("Horizontal", PositionHorizontal.CENTER);
		BUILDER.pop();

		BUILDER.push("COLOR");
		DIMENSION_COLOR = BUILDER.comment("hex or RGBA color code").defineInRange("Dimension", 0xAAFFFF, 0, 16777215);
		BIOME_COLOR = BUILDER.defineInRange("Biome", 0xAAFFAA, 0, 16777215);
		STRUCTURE_COLOR = BUILDER.defineInRange("Structure", 0xFFFFAA, 0, 16777215);
		BUILDER.pop();

		BUILDER.push("STYLE");
		UNDERLINE = BUILDER.define("Underline", true);
		SHADOW = BUILDER.define("Shadow", true);
		BUILDER.pop();

		BUILDER.push("BACKDROP");
		BACKDROP_STYLE = BUILDER.defineEnum("Style", BackdropStyle.ROUND);
		BACKDROP_COLOR = BUILDER.defineInRange("Color", 0x000000, 0, 16777215);
		BUILDER.pop();

		BUILDER.push("AUDIO");
		AUDIO_ALERT = BUILDER.defineEnum("Alert", AudioAlert.DISCOVER);
		BUILDER.pop();

		CLIENT_SPEC = BUILDER.build();
	}

	public enum Trigger {
		FIRST(0), EVERY(0), ALWAYS(1), NEVER(0);

		private final int alphaAdd;

		private Trigger(int alphaAdd) {
			this.alphaAdd = alphaAdd;
		}

		public int getAlphaAdd() {
			return this.alphaAdd;
		}
	}

	public enum NameSize {
		SMALL(1.5F), MEDIUM(2.0F), LARGE(2.5F);

		private final Float size;

		private NameSize(Float size) {
			this.size = size;
		}

		public Float getSize() {
			return this.size;
		}
	}

	public enum PositionVertical {
		TOP, MIDDLE, BOTTOM;
	}

	public enum PositionHorizontal {
		LEFT, CENTER, RIGHT;
	}

	public enum BackdropStyle {
		NONE(new ResourceLocation("textures/misc/white.png"), 0.0F), ROUND(new ResourceLocation(Environs.MOD_ID, "textures/misc/round.png"), 1.0F), SOLID(new ResourceLocation("textures/misc/white.png"), 0.5F), VIGNETTE(new ResourceLocation(Environs.MOD_ID, "textures/misc/vignette.png"), 1.0F);

		private final ResourceLocation texture;
		private final float clamp;

		private BackdropStyle(ResourceLocation texture, float clamp) {
			this.texture = texture;
			this.clamp = clamp;
		}

		public ResourceLocation getTexture() {
			return this.texture;
		}

		public float getClamp() {
			return this.clamp;
		}
	}

	public enum AudioAlert {
		BELL, CHIME, DISCOVER, NONE;
	}
}
