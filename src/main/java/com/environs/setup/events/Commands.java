package com.environs.setup.events;

import com.environs.server.commands.MoonTypeCommand;

import net.minecraftforge.event.RegisterCommandsEvent;

public class Commands {
	public static void register(final RegisterCommandsEvent event) {
		MoonTypeCommand.register(event.getDispatcher());
	}
}
