package com.environs.util;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

public final class TextUtil {
	public static String stringToCapsName(String string) {
		String words[] = string.split("_");
		String name = "";

		for (String word : words) {
			String first = word.substring(0, 1);
			String afterFirst = word.substring(1);
			name += first.toUpperCase() + afterFirst + " ";
		}

		return name.trim();
	}

	public static String composeTranslatableDimension(ResourceKey<?> resourceKey) {
		return "dimension." + resourceKey.location().getNamespace() + "." + resourceKey.location().getPath();
	}

	public static String composeTranslatableBiome(Holder<?> holder) {
		return "biome." + holder.unwrapKey().get().location().getNamespace() + "." + holder.unwrapKey().get().location().getPath();
	}

	public static String composeTranslatableStructure(Holder<?> holder) {
		return "structure." + holder.unwrapKey().get().location().getNamespace() + "." + holder.unwrapKey().get().location().getPath();
	}
}
