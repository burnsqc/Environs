package com.environs.listeners;

import com.environs.setup.config.EnvironsConfigClient;
import com.environs.util.TextUtil;
import com.environs.world.level.saveddata.EnvironmentTracker;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TickEventListener {

	@SubscribeEvent
	public void onPlayerTickEvent(final TickEvent.PlayerTickEvent event) {
		if (event.player instanceof LocalPlayer localPlayer) {
			BlockPos pos = BlockPos.containing(localPlayer.getEyePosition());
			String biomeName = TextUtil.stringToCapsName(TextUtil.getPath(localPlayer.level().getBiome(pos)));
			if (!biomeName.equals(EnvironmentTracker.getMostRecentBiome())) {
				if (EnvironmentTracker.addBiome(biomeName) || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("always")) {
					if (!EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("never")) {
						RenderGuiOverlayEventListener.triggerBiomeTitleCard();
					}
				}
			}
		}
	}
}
