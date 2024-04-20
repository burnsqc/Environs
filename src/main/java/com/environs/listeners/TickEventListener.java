package com.environs.listeners;

import com.environs.Environs;
import com.environs.capabilities.entity.EnvironsTracker;
import com.environs.network.packets.ClientboundTriggerEnvironsTitleCardPacket;
import com.environs.setup.config.EnvironsConfigClient;
import com.environs.setup.events.EnvironsCapabilities;
import com.environs.util.TextUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

public final class TickEventListener {

	@SubscribeEvent
	public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
		if (event.player instanceof ServerPlayer serverPlayer) {
			EnvironsTracker environsTracker = serverPlayer.getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElse(null);

			if (environsTracker == null) {
				return;
			}

			ServerLevel serverlevel = serverPlayer.serverLevel();
			BlockPos blockpos = BlockPos.containing(serverPlayer.position());

			if (serverlevel.isLoaded(blockpos)) {
				String dimensionName = TextUtil.stringToCapsName(TextUtil.getPath(serverPlayer.level().dimension()));
				String biomeName = TextUtil.stringToCapsName(TextUtil.getPath(serverPlayer.level().getBiome(blockpos)));
				String structureName = "";

				Registry<Structure> registry = serverlevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
				for (Reference<Structure> structure : registry.holders().toList()) {
					if (serverlevel.structureManager().getStructureWithPieceAt(blockpos, structure.get()).isValid()) {
						structureName = TextUtil.stringToCapsName(TextUtil.getPath(structure));
						break;
					}
				}

				if (!dimensionName.equals(environsTracker.getMostRecentDimension())) {
					if (environsTracker.addDimension(dimensionName) || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("every") || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("always")) {
						if (!EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("never")) {
							Environs.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new ClientboundTriggerEnvironsTitleCardPacket("dimension", dimensionName));
						}
					}
				}

				if (!biomeName.equals(environsTracker.getMostRecentBiome())) {
					if (environsTracker.addBiome(biomeName) || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("every") || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("always")) {
						if (!EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("never")) {
							Environs.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new ClientboundTriggerEnvironsTitleCardPacket("biome", biomeName));
						}
					}
				}

				if (!structureName.equals(environsTracker.getMostRecentStructure())) {
					if (environsTracker.addStructure(structureName) || EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals("every") && !structureName.equals("") || EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals("always")) {
						if (!EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals("never")) {
							Environs.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new ClientboundTriggerEnvironsTitleCardPacket("structure", structureName));
						}
					}
				}
			}
		}
	}
}
