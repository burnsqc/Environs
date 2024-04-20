package com.environs.network.packethandlers;

import java.util.function.Supplier;

import com.environs.listeners.RenderGuiOverlayEventListener;
import com.environs.network.packets.ClientboundTriggerEnvironsTitleCardPacket;

import net.minecraftforge.network.NetworkEvent;

public class ClientboundPacketHandlers {

	public static void handleTriggerEnvironmentTitleCard(ClientboundTriggerEnvironsTitleCardPacket packet, final Supplier<NetworkEvent.Context> context) {
		String type = packet.getType();
		String title = packet.getTitle();
		if (type.equals("dimension")) {
			RenderGuiOverlayEventListener.triggerDimensionTitleCard(title);
		} else if (type.equals("biome")) {
			RenderGuiOverlayEventListener.triggerBiomeTitleCard(title);
		} else if (type.equals("structure")) {
			RenderGuiOverlayEventListener.triggerStructureTitleCard(title);
		}
	}
}
