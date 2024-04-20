package com.environs.listeners;

import org.joml.Matrix4f;

import com.environs.setup.config.EnvironsConfigClient;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class RenderGuiOverlayEventListener {
	private static long fadeDimensionTimer;
	private static long fadeBiomeTimer;
	private static long fadeStructureTimer;
	private static String dimensionName;
	private static String biomeName;
	private static String structureName;
	private static long time;

	@SubscribeEvent
	public static void onPre(final RenderGuiOverlayEvent.Pre event) {
		if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {

			Minecraft minecraft = Minecraft.getInstance();
			time = minecraft.level.getGameTime();
			MultiBufferSource.BufferSource irendertypebuffer$impl = minecraft.renderBuffers().bufferSource();

			float scaledWidth25 = minecraft.getWindow().getGuiScaledWidth() / 2.5F;
			float scaledWidth2 = minecraft.getWindow().getGuiScaledWidth() / 2.0F;
			float scaledWidth15 = minecraft.getWindow().getGuiScaledWidth() / 1.5F;
			float scaledHeight25 = minecraft.getWindow().getGuiScaledHeight() / 2.5F;

			float dimensionWidth = minecraft.font.width(dimensionName);
			float biomeWidth = minecraft.font.width(biomeName);
			float structureWidth = minecraft.font.width(structureName);

			float dimensionNamePosX;
			float dimensionNamePosY;
			float biomeNamePosX;
			float biomeNamePosY;
			float structureNamePosX;
			float structurenamePosY;

			if (EnvironsConfigClient.POSITION_HORIZONTAL.get().equals("left")) {
				dimensionNamePosX = 2.0F;
				biomeNamePosX = 2.5F;
				structureNamePosX = 3.0F;
			} else if (EnvironsConfigClient.POSITION_HORIZONTAL.get().equals("center")) {
				dimensionNamePosX = (scaledWidth25 - dimensionWidth) / 2;
				biomeNamePosX = (scaledWidth2 - biomeWidth) / 2;
				structureNamePosX = (scaledWidth15 - structureWidth) / 2;
			} else {
				dimensionNamePosX = scaledWidth25 - dimensionWidth - 2.0F;
				biomeNamePosX = scaledWidth2 - biomeWidth - 2.5F;
				structureNamePosX = scaledWidth15 - structureWidth - 3.0F;
			}

			if (EnvironsConfigClient.POSITION_VERTICAL.get().equals("top")) {
				dimensionNamePosY = 2.0F;
			} else if (EnvironsConfigClient.POSITION_VERTICAL.get().equals("center")) {
				dimensionNamePosY = scaledHeight25 / 2 - 15.0F;
			} else {
				dimensionNamePosY = scaledHeight25 - 45.0F;
			}

			biomeNamePosY = dimensionNamePosY * 1.25F + 15.0F;
			structurenamePosY = biomeNamePosY * 1.33F + 17.0F;

			float dimensionAlpha = Mth.clamp(time - fadeDimensionTimer < 70 ? (time - fadeDimensionTimer) / 40F : -(time - fadeDimensionTimer - 140) / 40F, 0.0F, 1.0F);
			float biomeAlpha = Mth.clamp(time - fadeBiomeTimer < 80 ? (time - fadeBiomeTimer - 20) / 40F : -(time - fadeBiomeTimer - 140) / 40F, 0.0F, 1.0F);
			float structureAlpha = Mth.clamp(time - fadeStructureTimer < 80 ? (time - fadeStructureTimer - 20) / 40F : -(time - fadeStructureTimer - 140) / 40F, 0.0F, 1.0F);

			if (EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("always")) {
				dimensionAlpha = 1.0F;
			}
			if (EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("always")) {
				biomeAlpha = 1.0F;
			}
			if (EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals("always")) {
				structureAlpha = 1.0F;
			}

			if (dimensionAlpha > 0.015) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(2.5F, 2.5F, 2.5F);
				minecraft.font.drawInBatch(EnvironsConfigClient.UNDERLINE.get() ? ChatFormatting.UNDERLINE + dimensionName : dimensionName, dimensionNamePosX, dimensionNamePosY, EnvironsConfigClient.DIMENSION_COLOR.get() | (int) (dimensionAlpha * 255.0F) << 24, EnvironsConfigClient.SHADOW.get(), matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}

			if (biomeAlpha > 0.015) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(2.0F, 2.0F, 2.0F);
				minecraft.font.drawInBatch(EnvironsConfigClient.UNDERLINE.get() ? ChatFormatting.UNDERLINE + biomeName : biomeName, biomeNamePosX, biomeNamePosY, EnvironsConfigClient.BIOME_COLOR.get() | (int) (biomeAlpha * 255.0F) << 24, EnvironsConfigClient.SHADOW.get(), matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}

			if (structureAlpha > 0.015 && structureName != null) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(1.5F, 1.5F, 1.5F);
				minecraft.font.drawInBatch(EnvironsConfigClient.UNDERLINE.get() ? ChatFormatting.UNDERLINE + structureName : structureName, structureNamePosX, structurenamePosY, EnvironsConfigClient.STRUCTURE_COLOR.get() | (int) (structureAlpha * 255.0F) << 24, EnvironsConfigClient.SHADOW.get(), matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}
		}
	}

	public static void triggerDimensionTitleCard(String dimension) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeDimensionTimer > 120 || fadeDimensionTimer == 0 || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("always")) {
			dimensionName = dimension;
			fadeDimensionTimer = minecraft.level.getGameTime();
		}
	}

	public static void triggerBiomeTitleCard(String biome) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeBiomeTimer > 120 || fadeBiomeTimer == 0 || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("always")) {
			biomeName = biome;
			fadeBiomeTimer = minecraft.level.getGameTime();
		}
	}

	public static void triggerStructureTitleCard(String structure) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeStructureTimer > 120 || fadeStructureTimer == 0 || EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals("always")) {
			structureName = structure;
			fadeStructureTimer = minecraft.level.getGameTime();
		}
	}
}
