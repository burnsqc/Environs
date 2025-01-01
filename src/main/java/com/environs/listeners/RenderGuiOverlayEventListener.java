package com.environs.listeners;

import org.joml.Matrix4f;

import com.environs.config.EnvironsConfigClient;
import com.environs.util.TextUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class RenderGuiOverlayEventListener {
	private static long fadeDimensionTimer;
	private static long fadeBiomeTimer;
	private static long fadeStructureTimer;
	private static MutableComponent dimensionName;
	private static MutableComponent biomeName;
	private static MutableComponent structureName;
	private static long time;

	@SubscribeEvent
	public static void onPre(final RenderGuiOverlayEvent.Pre event) {
		if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
			Window window = event.getWindow();
			PoseStack poseStack = event.getPoseStack();
			Minecraft minecraft = Minecraft.getInstance();
			Font font = minecraft.font;
			time = minecraft.level.getGameTime();
			MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

			int scaledWidth = window.getGuiScaledWidth();
			int scaledHeight = window.getGuiScaledHeight();

			float dimensionWidth = dimensionName == null ? 0 : font.width(dimensionName);
			float biomeWidth = biomeName == null ? 0 : font.width(biomeName);
			float structureWidth = structureName == null ? 0 : font.width(structureName);

			// Apply size from config
			float dimensionSize = EnvironsConfigClient.DIMENSION_SIZE.get().getSize();
			float biomeSize = EnvironsConfigClient.BIOME_SIZE.get().getSize();
			float structureSize = EnvironsConfigClient.STRUCTURE_SIZE.get().getSize();

			// Apply position from config
			float dimensionNamePosX = 0;
			float dimensionNamePosY = 0;
			float biomeNamePosX = 0;
			float structureNamePosX = 0;

			// Apply horizontal position from config
			switch (EnvironsConfigClient.POSITION_HORIZONTAL.get()) {
			case LEFT:
				dimensionNamePosX = 5 / dimensionSize;
				biomeNamePosX = 5 / biomeSize;
				structureNamePosX = 5 / structureSize;
				break;
			case CENTER:
				dimensionNamePosX = (scaledWidth / dimensionSize - dimensionWidth) / 2;
				biomeNamePosX = (scaledWidth / biomeSize - biomeWidth) / 2;
				structureNamePosX = (scaledWidth / structureSize - structureWidth) / 2;
				break;
			case RIGHT:
				dimensionNamePosX = scaledWidth / dimensionSize - dimensionWidth - 5 / dimensionSize;
				biomeNamePosX = scaledWidth / biomeSize - biomeWidth - 5 / biomeSize;
				structureNamePosX = scaledWidth / structureSize - structureWidth - 5 / structureSize;
				break;
			}

			// Apply vertical position from config
			switch (EnvironsConfigClient.POSITION_VERTICAL.get()) {
			case TOP:
				dimensionNamePosY = 5 / dimensionSize;
				break;
			case MIDDLE:
				dimensionNamePosY = scaledHeight / 2 / dimensionSize - 16;
				break;
			case BOTTOM:
				dimensionNamePosY = (scaledHeight - 120) / dimensionSize;
				break;
			}
			float biomeNamePosY = (dimensionNamePosY + 11) * dimensionSize / biomeSize;
			float structurenamePosY = (biomeNamePosY + 11) * biomeSize / structureSize;

			// Apply fade function, but force a value of 1 if triggers are set to always
			float dimensionAlpha = Mth.clamp((time - fadeDimensionTimer < 70 ? (time - fadeDimensionTimer) / 40.0F : -(time - fadeDimensionTimer - 140) / 40.0F), EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().getAlphaAdd(), 1.0F);
			float biomeAlpha = Mth.clamp(time - fadeBiomeTimer < 80 ? (time - fadeBiomeTimer - 20) / 40.0F : -(time - fadeBiomeTimer - 140) / 40.0F, EnvironsConfigClient.BIOME_TITLE_CARDS.get().getAlphaAdd(), 1.0F);
			float structureAlpha = Mth.clamp(time - fadeStructureTimer < 80 ? (time - fadeStructureTimer - 20) / 40.0F : -(time - fadeStructureTimer - 140) / 40.0F, EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().getAlphaAdd(), 1.0F);

			// Apply color from config
			int dimensionColor = EnvironsConfigClient.DIMENSION_COLOR.get() | (int) (dimensionAlpha * 255) << 24;
			int biomeColor = EnvironsConfigClient.BIOME_COLOR.get() | (int) (biomeAlpha * 255) << 24;
			int structureColor = EnvironsConfigClient.STRUCTURE_COLOR.get() | (int) (structureAlpha * 255) << 24;

			// Apply style from config
			if (EnvironsConfigClient.UNDERLINE.get()) {
				dimensionName = dimensionName != null ? dimensionName.withStyle(ChatFormatting.UNDERLINE) : null;
				biomeName = biomeName != null ? biomeName.withStyle(ChatFormatting.UNDERLINE) : null;
				structureName = structureName != null ? structureName.withStyle(ChatFormatting.UNDERLINE) : null;
			}
			boolean shadow = EnvironsConfigClient.SHADOW.get();

			// Apply backdrop from config
			int offsetX = 0;
			int offsetY = 0;
			switch (EnvironsConfigClient.BACKDROP_STYLE.get()) {
			case NONE:
				break;
			case ROUND:
				switch (EnvironsConfigClient.POSITION_VERTICAL.get()) {
				case TOP:
					offsetY = -100;
					break;
				case MIDDLE:
					offsetY = 0;
					break;
				case BOTTOM:
					offsetY = 64;
					break;
				}

				switch (EnvironsConfigClient.POSITION_HORIZONTAL.get()) {
				case LEFT:
					offsetX = -224;
					break;
				case CENTER:
					offsetX = 0;
					break;
				case RIGHT:
					offsetX = 224;
					break;
				}
				break;
			case SOLID:
				break;
			case VIGNETTE:
				break;
			}

			if (EnvironsConfigClient.BACKDROP_STYLE.get() != EnvironsConfigClient.BackdropStyle.NONE) {
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.disableDepthTest();
				RenderSystem.depthMask(false);

				int color = EnvironsConfigClient.BACKDROP_COLOR.get();
				float red = (color >> 16 & 0xFF) / 255.0F;
				float green = (color >> 8 & 0xFF) / 255.0F;
				float blue = (color & 0xFF) / 255.0F;
				float alpha = Mth.clamp(biomeAlpha + dimensionAlpha + structureAlpha, 0.0F, EnvironsConfigClient.BACKDROP_STYLE.get().getClamp());

				RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				RenderSystem.setShaderColor(red, green, blue, alpha);
				RenderSystem.setShaderTexture(0, EnvironsConfigClient.BACKDROP_STYLE.get().getTexture());
				GuiComponent.blit(poseStack, offsetX, offsetY, -90, 0.0F, 0.0F, scaledWidth, scaledHeight, scaledWidth, scaledHeight);

				RenderSystem.depthMask(true);
				RenderSystem.enableDepthTest();
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.defaultBlendFunc();
			}

			if (dimensionAlpha > 0.015 && dimensionName != null) {
				poseStack.pushPose();
				Matrix4f matrix4f = poseStack.last().pose();
				matrix4f.scale(dimensionSize, dimensionSize, dimensionSize);
				font.drawInBatch(dimensionName, dimensionNamePosX, dimensionNamePosY, dimensionColor, shadow, matrix4f, bufferSource, true, 0, 0);
				poseStack.popPose();
			}

			if (biomeAlpha > 0.015 && biomeName != null) {
				poseStack.pushPose();
				Matrix4f matrix4f = poseStack.last().pose();
				matrix4f.scale(biomeSize, biomeSize, biomeSize);
				font.drawInBatch(biomeName, biomeNamePosX, biomeNamePosY, biomeColor, shadow, matrix4f, bufferSource, true, 0, 0);
				poseStack.popPose();
			}

			if (structureAlpha > 0.015 && structureName != null) {
				poseStack.pushPose();
				Matrix4f matrix4f = poseStack.last().pose();
				matrix4f.scale(structureSize, structureSize, structureSize);
				font.drawInBatch(structureName, structureNamePosX, structurenamePosY, structureColor, shadow, matrix4f, bufferSource, true, 0, 0);
				poseStack.popPose();
			}
			bufferSource.endBatch();
		}
	}

	public static void triggerDimensionTitleCard(String dimension) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeDimensionTimer > 120 || fadeDimensionTimer == 0 || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.ALWAYS)) {
			dimensionName = TextUtil.translatableWithFallback(dimension, TextUtil.translationFallbackGuess(dimension));
			fadeDimensionTimer = minecraft.level.getGameTime();
			playAudioAlert();
		}
	}

	public static void triggerBiomeTitleCard(String biome) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeBiomeTimer > 120 || fadeBiomeTimer == 0 || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.ALWAYS)) {
			biomeName = TextUtil.translatableWithFallback(biome, TextUtil.translationFallbackGuess(biome));
			fadeBiomeTimer = minecraft.level.getGameTime();
			playAudioAlert();
		}
	}

	public static void triggerStructureTitleCard(String structure) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeStructureTimer > 120 || fadeStructureTimer == 0 || EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals(EnvironsConfigClient.Trigger.ALWAYS)) {
			structureName = TextUtil.translatableWithFallback(structure, TextUtil.translationFallbackGuess(structure));
			fadeStructureTimer = minecraft.level.getGameTime();
			if (structureName.getString() != "") {
				playAudioAlert();
			}
		}
	}

	private static void playAudioAlert() {
		Minecraft minecraft = Minecraft.getInstance();
		switch (EnvironsConfigClient.AUDIO_ALERT.get()) {
		case BELL:
			minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.BELL_RESONATE, SoundSource.AMBIENT, 3.0F, 0.2F, false);
		case CHIME:
			minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.AMBIENT, 0.2F, 0.2F, false);
		case DISCOVER:
			minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.AMBIENT, 0.2F, 1.0F, false);
		default:
			break;
		}
	}
}
