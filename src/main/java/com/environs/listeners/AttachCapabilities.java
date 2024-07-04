package com.environs.listeners;

import com.environs.Environs;
import com.environs.capabilities.entity.EnvironsTrackerProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE)
public final class AttachCapabilities {

	@SubscribeEvent
	public static void onAttachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof ServerPlayer) {
			event.addCapability(new ResourceLocation(Environs.MOD_ID, "environs_tracker"), new EnvironsTrackerProvider());
		}
	}
}
