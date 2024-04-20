package com.environs.listeners;

import com.environs.Environs;
import com.environs.capabilities.entity.EnvironsTrackerProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class AttachCapabilities {

	@SubscribeEvent
	public static void onAttachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof ServerPlayer) {
			final EnvironsTrackerProvider environsTrackerProvider = new EnvironsTrackerProvider();
			event.addCapability(new ResourceLocation(Environs.MOD_ID, "environs_tracker"), environsTrackerProvider);
		}
	}
}
