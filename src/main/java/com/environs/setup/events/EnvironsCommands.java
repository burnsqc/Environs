package com.environs.setup.events;

import com.environs.server.commands.EnvironsListCommand;

import net.minecraftforge.event.RegisterCommandsEvent;

public class EnvironsCommands {
	public static void register(final RegisterCommandsEvent event) {
		EnvironsListCommand.register(event.getDispatcher());
	}
}
