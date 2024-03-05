package com.environs.setup.events;

import com.environs.capabilities.entity.EnvironsTracker;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public final class EnvironsCapabilities {
	public static final Capability<EnvironsTracker> ENVIRONS_TRACKER_INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static void onRegisterCapabilitiesEvent(final RegisterCapabilitiesEvent event) {
		event.register(EnvironsTracker.class);
	}
}
