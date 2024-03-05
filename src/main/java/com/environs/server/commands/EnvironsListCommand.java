package com.environs.server.commands;

import com.environs.capabilities.entity.EnvironsTracker;
import com.environs.setup.config.EnvironsConfigClient;
import com.environs.setup.events.EnvironsCapabilities;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class EnvironsListCommand {

	public static void register(CommandDispatcher<CommandSourceStack> command) {
		command.register(Commands.literal("environs").requires((stack) -> {
			return stack.hasPermission(2);
		}).then(Commands.literal("dimensions").then(Commands.literal("list").executes((context) -> {
			return listDimensions(context.getSource(), -1);
		})).then(Commands.literal("purge").executes((context) -> {
			return purgeDimensions(context.getSource(), -1);
		}))).then(Commands.literal("biomes").then(Commands.literal("list").executes((context) -> {
			return listBiomes(context.getSource(), -1);
		})).then(Commands.literal("purge").executes((context) -> {
			return purgeBiomes(context.getSource(), -1);
		}))).then(Commands.literal("structures").then(Commands.literal("list").executes((context) -> {
			return listStructures(context.getSource(), -1);
		})).then(Commands.literal("purge").executes((context) -> {
			return purgeStructures(context.getSource(), -1);
		}))));
	}

	private static int listDimensions(CommandSourceStack stack, int p_139174_) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		stack.sendSuccess(() -> {
			return Component.literal("Visited Dimensions").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.DIMENSION_COLOR.get()).withUnderlined(true);
			});
		}, true);
		for (String dimension : environsTracker.getDimensions()) {
			stack.sendSuccess(() -> {
				return Component.literal(dimension).withStyle((p_265659_) -> {
					return p_265659_.withColor(EnvironsConfigClient.DIMENSION_COLOR.get());
				});
			}, true);

		}
		return p_139174_;
	}

	private static int purgeDimensions(CommandSourceStack stack, int p_139174_) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		environsTracker.purgeDimensions();
		stack.sendSuccess(() -> {
			return Component.literal("Visited Dimensions purged").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.DIMENSION_COLOR.get());
			});
		}, true);
		return p_139174_;
	}

	private static int listBiomes(CommandSourceStack stack, int p_139174_) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		stack.sendSuccess(() -> {
			return Component.literal("Visited Biomes").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.BIOME_COLOR.get()).withUnderlined(true);
			});
		}, true);
		for (String biome : environsTracker.getBiomes()) {
			stack.sendSuccess(() -> {
				return Component.literal(biome).withStyle((p_265659_) -> {
					return p_265659_.withColor(EnvironsConfigClient.BIOME_COLOR.get());
				});
			}, true);

		}
		return p_139174_;
	}

	private static int purgeBiomes(CommandSourceStack stack, int p_139174_) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		environsTracker.purgeBiomes();
		stack.sendSuccess(() -> {
			return Component.literal("Visited Biomes purged").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.BIOME_COLOR.get());
			});
		}, true);
		return p_139174_;
	}

	private static int listStructures(CommandSourceStack stack, int p_139174_) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		stack.sendSuccess(() -> {
			return Component.literal("Visited Structures").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.STRUCTURE_COLOR.get()).withUnderlined(true);
			});
		}, true);
		if (environsTracker.getStructures() != null) {
			for (String structure : environsTracker.getStructures()) {
				stack.sendSuccess(() -> {
					return Component.literal(structure).withStyle((p_265659_) -> {
						return p_265659_.withColor(EnvironsConfigClient.STRUCTURE_COLOR.get());
					});
				}, true);

			}
		}
		return p_139174_;
	}

	private static int purgeStructures(CommandSourceStack stack, int p_139174_) {
		EnvironsTracker environsTracker = stack.getPlayer().getCapability(EnvironsCapabilities.ENVIRONS_TRACKER_INSTANCE).orElseThrow(NullPointerException::new);
		environsTracker.purgeStructures();
		stack.sendSuccess(() -> {
			return Component.literal("Visited Structures purged").withStyle((p_265659_) -> {
				return p_265659_.withColor(EnvironsConfigClient.STRUCTURE_COLOR.get());
			});
		}, true);
		return p_139174_;
	}
}
