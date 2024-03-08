package com.environs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.environs.listeners.AttachCapabilities;
import com.environs.listeners.PlayerEventListener;
import com.environs.listeners.TickEventListener;
import com.environs.network.packets.ClientboundTriggerEnvironsTitleCardPacket;
import com.environs.setup.ClientSetup;
import com.environs.setup.events.EnvironsCapabilities;
import com.environs.setup.events.EnvironsCommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(Environs.MOD_ID)
public final class Environs {
	public static final String MOD_ID = "environs";
	private static final Logger LOGGER = LogManager.getLogger();
	private static final IEventBus MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
	public static final IEventBus FORGE_EVENT_BUS = MinecraftForge.EVENT_BUS;
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int PACKET_ID = 0;

	public Environs() {
		LOGGER.info("ENVIRONS NOW LOADING FOR DISTRIBUTION - " + FMLEnvironment.dist.toString());

		MOD_EVENT_BUS.addListener(EnvironsCapabilities::onRegisterCapabilitiesEvent);
		FORGE_EVENT_BUS.addListener(EnvironsCommands::register);
		FORGE_EVENT_BUS.addGenericListener(Entity.class, AttachCapabilities::onAttachCapabilities);
		FORGE_EVENT_BUS.register(new TickEventListener());
		FORGE_EVENT_BUS.addListener(PlayerEventListener::onClone);

		CHANNEL.registerMessage(PACKET_ID++, ClientboundTriggerEnvironsTitleCardPacket.class, ClientboundTriggerEnvironsTitleCardPacket::encode, ClientboundTriggerEnvironsTitleCardPacket::decode, ClientboundTriggerEnvironsTitleCardPacket::handle);

		if (FMLEnvironment.dist.isClient()) {
			ClientSetup.init();
		}
	}
}
