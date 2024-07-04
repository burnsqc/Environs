package com.environs.setup.events;

import com.environs.capabilities.entity.EnvironsTracker;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public final class EnvironsCapabilities {
	public static final Capability<EnvironsTracker> ENVIRONS_TRACKER_INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
	});

	@SubscribeEvent
	public static void onRegisterCapabilitiesEvent(final RegisterCapabilitiesEvent event) {
		event.register(EnvironsTracker.class);
	}
}
