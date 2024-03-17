package com.environs.server.commands;

import com.environs.capabilities.entity.EnvironsTracker;
import com.environs.setup.config.EnvironsConfigClient;
import com.environs.setup.events.EnvironsCapabilities;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class EnvironsListCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("environs").requires((stack) -> {
			return stack.hasPermission(0);
		}).then(Commands.literal("list").then(Commands.literal("all").executes((context) -> {
			return listAll(context.getSource());
		})).then(Commands.literal("biomes").executes((context) -> {
			return listBiomes(context.getSource());
		})).then(Commands.literal("dimensions").executes((context) -> {
			return listDimensions(context.getSource());
		})).then(Commands.literal("structures").executes((context) -> {
			return listStructures(context.getSource());
		}))).then(Commands.literal("purge").then(Commands.literal("all").executes((context) -> {
			return purgeAll(context.getSource());
		})).then(Commands.literal("biomes").executes((context) -> {
			return purgeBiomes(context.getSource());
		})).then(Commands.literal("dimensions").executes((context) -> {
			return purgeDimensions(context.getSource());
		})).then(Commands.literal("structures").executes((context) -> {
			return purgeStructures(context.getSource());
		}))));
	}

	private static int listAll(CommandSourceStack stack) {
		listDimensions(stack);
		listBiomes(stack);
		listStructures(stack);
		return 0;
	}

	private static int listBiomes(CommandSourceStack stack) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		stack.sendSuccess(() -> {
			return Component.literal("Visited Biomes").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.BIOME_COLOR.get()).withUnderlined(true);
			});
		}, false);
		for (String biome : environsTracker.getBiomes()) {
			stack.sendSuccess(() -> {
				return Component.literal(biome).withStyle((p_265659_) -> {
					return p_265659_.withColor(EnvironsConfigClient.BIOME_COLOR.get());
				});
			}, false);

		}
		return 0;
	}

	private static int listDimensions(CommandSourceStack stack) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		stack.sendSuccess(() -> {
			return Component.literal("Visited Dimensions").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.DIMENSION_COLOR.get()).withUnderlined(true);
			});
		}, false);
		for (String dimension : environsTracker.getDimensions()) {
			stack.sendSuccess(() -> {
				return Component.literal(dimension).withStyle((p_265659_) -> {
					return p_265659_.withColor(EnvironsConfigClient.DIMENSION_COLOR.get());
				});
			}, false);

		}
		return 0;
	}

	private static int listStructures(CommandSourceStack stack) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		stack.sendSuccess(() -> {
			return Component.literal("Visited Structures").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.STRUCTURE_COLOR.get()).withUnderlined(true);
			});
		}, false);
		if (environsTracker.getStructures() != null) {
			for (String structure : environsTracker.getStructures()) {
				stack.sendSuccess(() -> {
					return Component.literal(structure).withStyle((p_265659_) -> {
						return p_265659_.withColor(EnvironsConfigClient.STRUCTURE_COLOR.get());
					});
				}, false);

			}
		}
		return 0;
	}

	private static int purgeAll(CommandSourceStack stack) {
		purgeDimensions(stack);
		purgeBiomes(stack);
		purgeStructures(stack);
		return 0;
	}

	private static int purgeBiomes(CommandSourceStack stack) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		environsTracker.purgeBiomes();
		stack.sendSuccess(() -> {
			return Component.literal("Visited Biomes purged").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.BIOME_COLOR.get());
			});
		}, false);
		return 0;
	}

	private static int purgeDimensions(CommandSourceStack stack) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		environsTracker.purgeDimensions();
		stack.sendSuccess(() -> {
			return Component.literal("Visited Dimensions purged").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.DIMENSION_COLOR.get());
			});
		}, false);
		return 0;
	}

	private static int purgeStructures(CommandSourceStack stack) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		environsTracker.purgeStructures();
		stack.sendSuccess(() -> {
			return Component.literal("Visited Structures purged").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.STRUCTURE_COLOR.get());
			});
		}, false);
		return 0;
	}
}
