package com.environs.network.packets;

import java.util.function.Supplier;

import com.environs.network.packethandlers.ClientboundPacketHandlers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundSyncSavedDataPacket {
	private int moonType;

	public ClientboundSyncSavedDataPacket(int moonTypeIn) {
		moonType = moonTypeIn;
	}

	public static void encode(ClientboundSyncSavedDataPacket msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.moonType);
	}

	public static ClientboundSyncSavedDataPacket decode(FriendlyByteBuf buf) {
		return new ClientboundSyncSavedDataPacket(buf.readInt());
	}

	public static void handle(ClientboundSyncSavedDataPacket packet, final Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ClientboundPacketHandlers.handleSyncSavedData(packet, context);
		});
		context.get().setPacketHandled(true);
	}

	public int getMoonType() {
		return moonType;
	}
}
