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

	public static String getPath(ResourceKey<?> resourceKey) {
		return resourceKey.location().getPath().toString();
	}

	public static String getPath(Holder<?> holder) {
		return holder.unwrapKey().get().location().getPath().toString();
	}
}
