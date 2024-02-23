package com.environs.network.packethandlers;

import java.util.function.Supplier;

import com.environs.client.multiplayer.ClientLevelDataExtension;
import com.environs.network.packets.ClientboundSetMoonTypePacket;
import com.environs.network.packets.ClientboundSyncSavedDataPacket;
import com.environs.setup.config.RareMoonConfigClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundPacketHandlers {
	public static void handleSetMoonType(ClientboundSetMoonTypePacket packet, final Supplier<NetworkEvent.Context> context) {
		ClientLevelDataExtension data = new ClientLevelDataExtension();
		data.setMoon(packet.getMoonType());

		if (RareMoonConfigClient.RARE_MOON_NOTIFICATION.get()) {
			Minecraft mc = Minecraft.getInstance();
			LocalPlayer player = mc.player;
			int moon = packet.getMoonType();
			String message = "";

			if (moon == 1) {
				message = "raremoon.rise.blood";
				player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 0.2F, 0.2F, false);
				player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_DEATH, SoundSource.BLOCKS, 0.2F, 0.1F, false);
			} else if (moon == 2) {
				message = "raremoon.rise.fortune";
				player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 10.0F, 0.2F, false);
				player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 0.1F, 2.0F, false);
			} else if (moon == 3) {
				message = "raremoon.rise.harvest";
				player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 10.0F, 0.1F, false);
				player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.CAT_PURR, SoundSource.BLOCKS, 10.0F, 0.1F, false);
			} else if (moon == 4) {
				message = "raremoon.rise.blue";
				player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_RESONATE, SoundSource.BLOCKS, 5.0F, 0.1F, false);
				player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_RESONATE, SoundSource.BLOCKS, 5.0F, 0.3F, false);
			}

			player.displayClientMessage(Component.translatable(message), true);
		}
	}

	public static void handleSyncSavedData(ClientboundSyncSavedDataPacket packet, final Supplier<NetworkEvent.Context> context) {
		ClientLevelDataExtension data = new ClientLevelDataExtension();
		data.setMoon(packet.getMoonType());
	}
}
