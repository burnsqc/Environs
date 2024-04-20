package com.environs.listeners;

import com.environs.capabilities.entity.EnvironsTracker;
import com.environs.setup.events.EnvironsCapabilities;

import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerEventListener {
	public static void onClone(final PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			event.getOriginal().reviveCaps();
			EnvironsTracker environsTrackerOld = event.getOriginal().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
			EnvironsTracker environsTrackerNew = event.getEntity().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
			environsTrackerNew.cloneDimensions(environsTrackerOld.getDimensions());
			environsTrackerNew.cloneBiomes(environsTrackerOld.getBiomes());
			environsTrackerNew.cloneStructures(environsTrackerOld.getStructures());
			environsTrackerNew.setMostRecentDimension(environsTrackerOld.getMostRecentDimension());
			environsTrackerNew.setMostRecentBiome(environsTrackerOld.getMostRecentBiome());
			environsTrackerNew.setMostRecentStructure(environsTrackerOld.getMostRecentStructure());
			event.getOriginal().invalidateCaps();
		}
	}
}
