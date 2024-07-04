package com.environs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.environs.network.packets.ClientboundTriggerEnvironsTitleCardPacket;
import com.environs.setup.ClientSetup;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(Environs.MOD_ID)
public final class Environs {
	public static final String MOD_ID = "environs";
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int PACKET_ID = 0;

	public Environs() {
		LOGGER.info("ENVIRONS NOW LOADING FOR DISTRIBUTION - " + FMLEnvironment.dist.toString());
		CHANNEL.registerMessage(PACKET_ID++, ClientboundTriggerEnvironsTitleCardPacket.class, ClientboundTriggerEnvironsTitleCardPacket::encode, ClientboundTriggerEnvironsTitleCardPacket::decode, ClientboundTriggerEnvironsTitleCardPacket::handle);
		if (FMLEnvironment.dist.isClient()) {
			ClientSetup.init();
		}
	}
}
