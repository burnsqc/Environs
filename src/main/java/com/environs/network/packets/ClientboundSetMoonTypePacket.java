package com.environs.network.packets;

import java.util.function.Supplier;

import com.environs.network.packethandlers.ClientboundPacketHandlers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundSetMoonTypePacket {
	private int moonType;

	public ClientboundSetMoonTypePacket(int moonTypeIn) {
		moonType = moonTypeIn;
	}

	public static void encode(ClientboundSetMoonTypePacket msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.moonType);
	}

	public static ClientboundSetMoonTypePacket decode(FriendlyByteBuf buf) {
		return new ClientboundSetMoonTypePacket(buf.readInt());
	}

	public static void handle(ClientboundSetMoonTypePacket packet, final Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ClientboundPacketHandlers.handleSetMoonType(packet, context);
		});
		context.get().setPacketHandled(true);
	}

	public int getMoonType() {
		return moonType;
	}
}
