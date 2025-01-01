package com.environs.setup;

import com.environs.config.EnvironsConfigClient;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class ClientSetup {
	public static void init() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, EnvironsConfigClient.CLIENT_SPEC, "environs-client.toml");
	}
}
