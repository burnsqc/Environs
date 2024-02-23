package com.environs.listeners;

import com.environs.Environs;
import com.environs.setup.config.RareMoonConfigCommon;
import com.environs.world.level.saveddata.RareMoonOverworldExtension;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerTickEventListener {
	private static boolean flag = true;

	@SubscribeEvent
	public static void onServerTickEvent(final LevelTickEvent event) {
		if (event.level instanceof ServerLevel) {
			if (event.level.getDayTime() < 12000 && flag) {
				RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(event.level.getServer());
				data.setMoon(0);
				flag = false;
			}

			if (event.level.getDayTime() >= 12000 && !flag) {
				flag = true;
				RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(event.level.getServer());
				if (event.level.getRandom().nextInt(100) < RareMoonConfigCommon.RARE_MOON_RARITY.get()) {
					int moon = event.level.getRandom().nextInt(1, 5);
					data.setMoon(moon);
					Environs.LOGGER.info("SET RARE MOON " + moon);
				} else {
					data.setMoon(0);
					Environs.LOGGER.info("SET " + 0);
				}
			}
		}
	}
}
