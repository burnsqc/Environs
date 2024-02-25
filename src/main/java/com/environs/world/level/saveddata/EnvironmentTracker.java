package com.environs.world.level.saveddata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class EnvironmentTracker implements INBTSerializable<CompoundTag> {
	private static Set<String> dimensions = new LinkedHashSet<String>(new ArrayList<String>(Arrays.asList("The Overworld")));
	private static Set<String> biomes = new LinkedHashSet<String>(new ArrayList<String>(Arrays.asList("Plains")));
	private static String mostRecentDimension;
	private static String mostRecentBiome;

	public static boolean addDimension(String dimension) {
		mostRecentDimension = dimension;
		return dimensions.add(dimension);
	}

	public Set<String> getDimensions() {
		return dimensions;
	}

	public static String getMostRecentDimension() {
		return mostRecentDimension;
	}

	public static boolean addBiome(String biome) {
		mostRecentBiome = biome;
		return biomes.add(biome);
	}

	public Set<String> getBiomes() {
		return biomes;
	}

	public static String getMostRecentBiome() {
		return mostRecentBiome;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag compoundTag = new CompoundTag();

		for (String string : getDimensions()) {
			compoundTag.putString("dimension:" + string, string);
		}

		for (String string : getBiomes()) {
			compoundTag.putString("biome" + string, string);
		}

		return compoundTag;
	}

	@Override
	public void deserializeNBT(CompoundTag compoundTag) {
		Set<String> dimensionsTemp = compoundTag.getAllKeys().stream().filter(s -> s.startsWith("dimension")).collect(Collectors.toSet());
		Set<String> biomesTemp = compoundTag.getAllKeys().stream().filter(s -> s.startsWith("biome")).collect(Collectors.toSet());
		dimensionsTemp.forEach(s -> s.replace("dimension:", ""));
		biomesTemp.forEach(s -> s.replace("biome:", ""));
		dimensions.addAll(dimensionsTemp);
		biomes.addAll(biomesTemp);
	}
}
