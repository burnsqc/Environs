package com.environs.setup;

import com.environs.Environs;
import com.environs.client.renderer.RenderRareMoonTint;
import com.environs.setup.config.RareMoonConfigClient;
import com.environs.setup.events.DimensionSpecialEffects;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class ClientSetup {
	public static void init() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, RareMoonConfigClient.CLIENT_SPEC, "raremoon-client.toml");

		Environs.MOD_EVENT_BUS.addListener(DimensionSpecialEffects::register);
		Environs.FORGE_EVENT_BUS.register(new RenderRareMoonTint());
	}
}
