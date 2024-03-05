package com.environs.network.packets;

import java.util.function.Supplier;

import com.environs.network.packethandlers.ClientboundPacketHandlers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundTriggerEnvironsTitleCardPacket {
	private final String type;
	private final String title;

	public ClientboundTriggerEnvironsTitleCardPacket(String type, String title) {
		this.type = type;
		this.title = title;
	}

	public static void encode(ClientboundTriggerEnvironsTitleCardPacket msg, FriendlyByteBuf buf) {
		buf.writeUtf(msg.type);
		buf.writeUtf(msg.title);
	}

	public static ClientboundTriggerEnvironsTitleCardPacket decode(FriendlyByteBuf buf) {
		String type = buf.readUtf();
		String title = buf.readUtf();
		return new ClientboundTriggerEnvironsTitleCardPacket(type, title);
	}

	public static void handle(ClientboundTriggerEnvironsTitleCardPacket packet, final Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ClientboundPacketHandlers.handleTriggerEnvironmentTitleCard(packet, context);
		});
		context.get().setPacketHandled(true);
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}
}
