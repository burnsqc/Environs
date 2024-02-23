package com.environs.listeners;

import com.environs.world.level.saveddata.RareMoonOverworldExtension;

import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BloodMoonListener {

	@SubscribeEvent
	public void onLivingHurtEvent(final LivingHurtEvent event) {
		if (event.getEntity().level().dimensionTypeId() == BuiltinDimensionTypes.OVERWORLD) {
			RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(event.getEntity().level().getServer());
			if (data.getMoon() == 1) {
				event.setAmount(event.getAmount() * 2);
			}
		}
	}
}
