package com.environs.listeners;

import com.environs.Environs;
import com.environs.capabilities.entity.EnvironsTracker;
import com.environs.config.EnvironsConfigClient;
import com.environs.network.packets.clientbound.TriggerEnvironsTitleCardPacket;
import com.environs.setup.events.EnvironsCapabilities;
import com.environs.util.TextUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Position;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE)
public final class TickEventListener {

	@SubscribeEvent
	public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
		if (event.player instanceof ServerPlayer serverPlayer) {
			EnvironsTracker environsTracker = serverPlayer.getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElse(null);

			if (environsTracker == null) {
				return;
			}

			ServerLevel serverlevel = serverPlayer.getLevel();
			BlockPos blockpos = containing(serverPlayer.position());

			if (serverlevel.isLoaded(blockpos)) {
				String dimensionName = TextUtil.composeTranslatableDimension(serverPlayer.level.dimension());
				String biomeName = TextUtil.composeTranslatableBiome(serverPlayer.level.getBiome(blockpos));
				String structureName = "";

				Registry<Structure> registry = serverlevel.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
				for (Reference<Structure> structure : registry.holders().toList()) {
					if (serverlevel.structureManager().getStructureWithPieceAt(blockpos, structure.get()).isValid()) {
						structureName = TextUtil.composeTranslatableStructure(structure);
						break;
					}
				}

				if (!dimensionName.equals(environsTracker.getMostRecentDimension())) {
					if (environsTracker.addDimension(dimensionName) || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.EVERY) || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.ALWAYS)) {
						if (!EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.NEVER)) {
							Environs.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new TriggerEnvironsTitleCardPacket("dimension", dimensionName));
						}
					}
				}

				if (!biomeName.equals(environsTracker.getMostRecentBiome())) {
					if (environsTracker.addBiome(biomeName) || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.EVERY) || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.ALWAYS)) {
						if (!EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.NEVER)) {
							Environs.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new TriggerEnvironsTitleCardPacket("biome", biomeName));
						}
					}
				}

				if (!structureName.equals(environsTracker.getMostRecentStructure())) {
					if (environsTracker.addStructure(structureName) || EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.EVERY) && !structureName.equals("") || EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.ALWAYS)) {
						if (!EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.NEVER)) {
							Environs.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new TriggerEnvironsTitleCardPacket("structure", structureName));
						}
					}
				}
			}
		}
	}

	// These two methods are only available in BlockPos as of 1.19.4
	public static BlockPos containing(Position p_275443_) {
		return containing(p_275443_.x(), p_275443_.y(), p_275443_.z());
	}

	public static BlockPos containing(double p_275310_, double p_275414_, double p_275737_) {
		return new BlockPos(Mth.floor(p_275310_), Mth.floor(p_275414_), Mth.floor(p_275737_));
	}
}
