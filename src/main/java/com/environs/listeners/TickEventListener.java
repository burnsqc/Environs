package com.environs.listeners;

import com.environs.setup.config.EnvironsConfigClient;
import com.environs.util.TextUtil;
import com.environs.world.level.saveddata.EnvironmentTracker;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TickEventListener {

	@SubscribeEvent
	public void onPlayerTickEvent(final TickEvent.PlayerTickEvent event) {
		if (event.player instanceof LocalPlayer localPlayer) {
			BlockPos pos = BlockPos.containing(localPlayer.getEyePosition());
			String dimensionName = TextUtil.stringToCapsName(TextUtil.getPath(localPlayer.level().dimension()));
			String biomeName = TextUtil.stringToCapsName(TextUtil.getPath(localPlayer.level().getBiome(pos)));

			if (!dimensionName.equals(EnvironmentTracker.getMostRecentDimension())) {
				if (EnvironmentTracker.addDimension(dimensionName) || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("always")) {
					if (!EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("never")) {
						RenderGuiOverlayEventListener.triggerDimensionTitleCard();
					}
				}
			}

			if (!biomeName.equals(EnvironmentTracker.getMostRecentBiome())) {
				if (EnvironmentTracker.addBiome(biomeName) || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("always")) {
					if (!EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("never")) {
						RenderGuiOverlayEventListener.triggerBiomeTitleCard();
					}
				}
			}
		} else if (event.player instanceof ServerPlayer serverPlayer) {
			ServerLevel serverlevel = serverPlayer.serverLevel();
			Registry<Structure> registry = serverlevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
			HolderSet<Structure> holderset = HolderSet.direct(registry.holders().toList());
			Pair<BlockPos, Holder<Structure>> blockpos = serverlevel.getChunkSource().getGenerator().findNearestMapStructure(serverlevel, holderset, serverPlayer.blockPosition(), 0, false);
			if (blockpos != null) {
				if ((blockpos.getFirst().distSqr(serverPlayer.blockPosition())) < 10000) {
					RenderGuiOverlayEventListener.setStructure(TextUtil.stringToCapsName(TextUtil.getPath(blockpos.getSecond())));
				} else {
					RenderGuiOverlayEventListener.setStructure("NONE");
				}
			} else {
				RenderGuiOverlayEventListener.setStructure("NONE");
			}
		}
	}
}
