package com.environs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.environs.network.packets.clientbound.TriggerEnvironsTitleCardPacket;
import com.environs.setup.ClientSetup;

import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(Environs.MOD_ID)
public final class Environs {
	public static final String MOD_ID = "environs";
	public static final String VERSION = "1.0.2";
	private static final Logger LOGGER = LogManager.getLogger("ENVIRONS");
	private static final Marker MARKER = MarkerManager.getMarker("LOADING");
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int PACKET_ID = 0;

	public Environs() {
		LOGGER.info(MARKER, "ENVIRONS " + VERSION + " NOW LOADING FOR MINECRAFT " + SharedConstants.getCurrentVersion().getName() + " ON " + FMLEnvironment.dist.toString() + " DISTRIBUTION");
		CHANNEL.registerMessage(PACKET_ID++, TriggerEnvironsTitleCardPacket.class, TriggerEnvironsTitleCardPacket::encode, TriggerEnvironsTitleCardPacket::decode, TriggerEnvironsTitleCardPacket::handle);
		if (FMLEnvironment.dist.isClient()) {
			ClientSetup.init();
		}
	}
}
