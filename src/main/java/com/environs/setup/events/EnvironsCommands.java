package com.environs.setup.events;

import com.environs.server.commands.EnvironsListCommand;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE)
public class EnvironsCommands {

	@SubscribeEvent
	public static void onRegisterCommandsEvent(final RegisterCommandsEvent event) {
		EnvironsListCommand.register(event.getDispatcher());
	}
}
