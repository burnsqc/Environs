package com.environs.server.commands;

import com.environs.world.level.saveddata.RareMoonOverworldExtension;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class MoonTypeCommand {

	public static void register(CommandDispatcher<CommandSourceStack> command) {
		command.register(Commands.literal("moon").requires((stack) -> {
			return stack.hasPermission(2);
		}).then(Commands.literal("normal").executes((context) -> {
			return setNormal(context.getSource());
		})).then(Commands.literal("blood").executes((context) -> {
			return setBlood(context.getSource());
		})).then(Commands.literal("fortune").executes((context) -> {
			return setFortune(context.getSource());
		})).then(Commands.literal("harvest").executes((context) -> {
			return setHarvest(context.getSource());
		})).then(Commands.literal("blue").executes((context) -> {
			return setBlue(context.getSource());
		})));
	}

	private static int setNormal(CommandSourceStack stack) {
		RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(stack.getLevel().getServer());
		data.setMoon(0);
		stack.sendSuccess(() -> {
			return Component.translatable("commands.moon.set.normal");
		}, true);
		return -1;
	}

	private static int setBlood(CommandSourceStack stack) {
		RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(stack.getLevel().getServer());
		data.setMoon(1);
		stack.sendSuccess(() -> {
			return Component.translatable("commands.moon.set.blood");
		}, true);
		return -1;
	}

	private static int setFortune(CommandSourceStack stack) {
		RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(stack.getLevel().getServer());
		data.setMoon(2);
		stack.sendSuccess(() -> {
			return Component.translatable("commands.moon.set.fortune");
		}, true);
		return -1;
	}

	private static int setHarvest(CommandSourceStack stack) {
		RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(stack.getLevel().getServer());
		data.setMoon(3);
		stack.sendSuccess(() -> {
			return Component.translatable("commands.moon.set.harvest");
		}, true);
		return -1;
	}

	private static int setBlue(CommandSourceStack stack) {
		RareMoonOverworldExtension data = RareMoonOverworldExtension.getData(stack.getLevel().getServer());
		data.setMoon(4);
		stack.sendSuccess(() -> {
			return Component.translatable("commands.moon.set.blue");
		}, true);
		return -1;
	}
}
