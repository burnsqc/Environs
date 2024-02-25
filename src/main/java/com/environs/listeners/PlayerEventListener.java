package com.environs.listeners;

import com.environs.setup.config.EnvironsConfigClient;
import com.environs.util.TextUtil;
import com.environs.world.level.saveddata.EnvironmentTracker;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEventListener {

	@SubscribeEvent
	public void onPlayerChangedDimensionEvent(final PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof LocalPlayer localPlayer) {
			if (EnvironmentTracker.addBiome(TextUtil.stringToProperName(TextUtil.getPath(event.getTo()))) || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("always")) {
				if (!EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("never")) {
					RenderGuiOverlayEventListener.triggerDimensionTitleCard();
				}
			}
		}
	}
}
