package com.environs.capabilities.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public final class EnvironsTracker implements INBTSerializable<CompoundTag> {
	private Set<String> dimensions = Collections.emptySet();
	private Set<String> biomes = Collections.emptySet();
	private Set<String> structures = Collections.emptySet();
	private String mostRecentDimension;
	private String mostRecentBiome;
	private String mostRecentStructure;

	public Set<String> getDimensions() {
		return this.dimensions;
	}

	public Set<String> getBiomes() {
		return this.biomes;
	}

	public Set<String> getStructures() {
		return this.structures;
	}

	public String getMostRecentDimension() {
		return this.mostRecentDimension;
	}

	public String getMostRecentBiome() {
		return this.mostRecentBiome;
	}

	public String getMostRecentStructure() {
		return this.mostRecentStructure;
	}

	public boolean addDimension(String dimension) {
		this.mostRecentDimension = dimension;
		if (this.dimensions.isEmpty()) {
			this.dimensions = new LinkedHashSet<String>(new ArrayList<String>(Arrays.asList(dimension)));
			return true;
		}
		return this.dimensions.add(dimension);
	}

	public boolean addBiome(String biome) {
		this.mostRecentBiome = biome;
		if (this.biomes.isEmpty()) {
			this.biomes = new LinkedHashSet<String>(new ArrayList<String>(Arrays.asList(biome)));
			return true;
		}
		return this.biomes.add(biome);
	}

	public boolean addStructure(String structure) {
		this.mostRecentStructure = structure;
		if (structure.equals("")) {
			return false;
		}
		if (this.structures.isEmpty()) {
			this.structures = new LinkedHashSet<String>(new ArrayList<String>(Arrays.asList(structure)));
			return true;
		}
		return this.structures.add(structure);
	}

	public void setMostRecentDimension(String dimension) {
		this.mostRecentDimension = dimension;
	}

	public void setMostRecentBiome(String biome) {
		this.mostRecentBiome = biome;
	}

	public void setMostRecentStructure(String structure) {
		this.mostRecentStructure = structure;
	}

	public void purgeDimensions() {
		this.dimensions = Collections.emptySet();
		this.mostRecentDimension = "";
	}

	public void purgeBiomes() {
		this.biomes = Collections.emptySet();
		this.mostRecentBiome = "";
	}

	public void purgeStructures() {
		this.structures = Collections.emptySet();
		this.mostRecentStructure = "";
	}

	public void cloneDimensions(Set<String> dimensions) {
		this.dimensions = dimensions;
	}

	public void cloneBiomes(Set<String> biomes) {
		this.biomes = biomes;
	}

	public void cloneStructures(Set<String> structures) {
		this.structures = structures;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag compoundTag = new CompoundTag();

		if (getDimensions() != null) {
			for (String string : getDimensions()) {
				compoundTag.putString("dimension: " + string, string);
			}
		}

		if (getBiomes() != null) {
			for (String string : getBiomes()) {
				compoundTag.putString("biome: " + string, string);
			}
		}

		if (getStructures() != null) {
			for (String string : getStructures()) {
				compoundTag.putString("structure: " + string, string);
			}
		}

		return compoundTag;
	}

	@Override
	public void deserializeNBT(CompoundTag compoundTag) {
		Set<String> dimensionsTemp = compoundTag.getAllKeys().stream().filter(s -> s.startsWith("dimension: ")).collect(Collectors.toSet());
		Set<String> biomesTemp = compoundTag.getAllKeys().stream().filter(s -> s.startsWith("biome: ")).collect(Collectors.toSet());
		Set<String> structuresTemp = compoundTag.getAllKeys().stream().filter(s -> s.startsWith("structure: ")).collect(Collectors.toSet());
		Set<String> dimensions2 = Collections.emptySet();
		Set<String> biomes2 = Collections.emptySet();
		Set<String> structures2 = Collections.emptySet();

		for (String string : dimensionsTemp) {
			if (dimensions2.isEmpty()) {
				dimensions2 = new LinkedHashSet<String>(new ArrayList<String>(Arrays.asList(compoundTag.getString(string))));
			} else {
				dimensions2.add(compoundTag.getString(string));
			}
		}

		for (String string : biomesTemp) {
			if (biomes2.isEmpty()) {
				biomes2 = new LinkedHashSet<String>(new ArrayList<String>(Arrays.asList(compoundTag.getString(string))));
			} else {
				biomes2.add(compoundTag.getString(string));
			}
		}

		for (String string : structuresTemp) {
			if (structures2.isEmpty()) {
				structures2 = new LinkedHashSet<String>(new ArrayList<String>(Arrays.asList(compoundTag.getString(string))));
			} else {
				structures2.add(compoundTag.getString(string));
			}
		}

		dimensions = new LinkedHashSet<String>(new ArrayList<String>(dimensions2));
		biomes = new LinkedHashSet<String>(new ArrayList<String>(biomes2));
		structures = new LinkedHashSet<String>(new ArrayList<String>(structures2));
	}
}
