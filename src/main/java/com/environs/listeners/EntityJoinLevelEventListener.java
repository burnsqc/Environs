package com.environs.listeners;

import com.environs.Environs;
import com.environs.network.packets.ClientboundSyncSavedDataPacket;
import com.environs.world.level.saveddata.RareMoonOverworldExtension;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

public class EntityJoinLevelEventListener {

	@SubscribeEvent
	public void onEntityJoinLevel(final EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer) {
			if (serverPlayer.level().dimensionTypeId() == BuiltinDimensionTypes.OVERWORLD) {
				RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(event.getEntity().getServer());
				Environs.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new ClientboundSyncSavedDataPacket(data.getMoon()));
			}
		}
	}
}
