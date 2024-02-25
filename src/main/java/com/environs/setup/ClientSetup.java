package com.environs.setup;

import com.environs.Environs;
import com.environs.listeners.PlayerEventListener;
import com.environs.listeners.RenderGuiOverlayEventListener;
import com.environs.listeners.TickEventListener;
import com.environs.setup.config.EnvironsConfigClient;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ClientSetup {
	public static void init() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, EnvironsConfigClient.CLIENT_SPEC, "environs-client.toml");
		Environs.FORGE_EVENT_BUS.register(new PlayerEventListener());
		Environs.FORGE_EVENT_BUS.register(new RenderGuiOverlayEventListener());
		Environs.FORGE_EVENT_BUS.register(new TickEventListener());
	}
}
