package com.environs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.environs.setup.ClientSetup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Environs.MOD_ID)
public class Environs {
	public static final String MOD_ID = "environs";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final IEventBus MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
	public static final IEventBus FORGE_EVENT_BUS = MinecraftForge.EVENT_BUS;

	public Environs() {
		LOGGER.info("ENVIRONS NOW LOADING FOR DISTRIBUTION - " + FMLEnvironment.dist.toString());

		if (FMLEnvironment.dist.isClient()) {
			ClientSetup.init();
		}
	}
}
