package com.environs.listeners;

import org.joml.Matrix4f;

import com.environs.Environs;
import com.environs.setup.config.EnvironsConfigClient;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(bus = EventBusSubscriber.Bus.FORGE)
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

			Minecraft minecraft = Minecraft.getInstance();
			time = minecraft.level.getGameTime();
			MultiBufferSource.BufferSource irendertypebuffer$impl = minecraft.renderBuffers().bufferSource();

			float scaledWidth = minecraft.getWindow().getGuiScaledWidth();
			float scaledHeight = minecraft.getWindow().getGuiScaledHeight();

			float dimensionWidth = 0;
			float biomeWidth = 0;
			float structureWidth = 0;

			if (dimensionName != null) {
				dimensionWidth = minecraft.font.width(dimensionName);
			}
			if (biomeName != null) {
				biomeWidth = minecraft.font.width(biomeName);
			}
			if (structureName != null) {
				structureWidth = minecraft.font.width(structureName);
			}

			float dimensionNamePosX;
			float dimensionNamePosY;
			float biomeNamePosX;
			float biomeNamePosY;
			float structureNamePosX;
			float structurenamePosY;

			float dimensionSize;
			float biomeSize;
			float structureSize;

			if (EnvironsConfigClient.DIMENSION_SIZE.get().equals("large")) {
				dimensionSize = 2.5F;
			} else if (EnvironsConfigClient.DIMENSION_SIZE.get().equals("medium")) {
				dimensionSize = 2.0F;
			} else {
				dimensionSize = 1.5F;
			}

			if (EnvironsConfigClient.BIOME_SIZE.get().equals("large")) {
				biomeSize = 2.5F;
			} else if (EnvironsConfigClient.BIOME_SIZE.get().equals("medium")) {
				biomeSize = 2.0F;
			} else {
				biomeSize = 1.5F;
			}

			if (EnvironsConfigClient.STRUCTURE_SIZE.get().equals("large")) {
				structureSize = 2.5F;
			} else if (EnvironsConfigClient.STRUCTURE_SIZE.get().equals("medium")) {
				structureSize = 2.0F;
			} else {
				structureSize = 1.5F;
			}

			if (EnvironsConfigClient.POSITION_HORIZONTAL.get().equals("left")) {
				dimensionNamePosX = 5 / dimensionSize;
				biomeNamePosX = 5 / biomeSize;
				structureNamePosX = 5 / structureSize;
			} else if (EnvironsConfigClient.POSITION_HORIZONTAL.get().equals("center")) {
				dimensionNamePosX = (scaledWidth / dimensionSize - dimensionWidth) / 2;
				biomeNamePosX = (scaledWidth / biomeSize - biomeWidth) / 2;
				structureNamePosX = (scaledWidth / structureSize - structureWidth) / 2;
			} else {
				dimensionNamePosX = scaledWidth / dimensionSize - dimensionWidth - 5 / dimensionSize;
				biomeNamePosX = scaledWidth / biomeSize - biomeWidth - 5 / biomeSize;
				structureNamePosX = scaledWidth / structureSize - structureWidth - 5 / structureSize;
			}

			if (EnvironsConfigClient.POSITION_VERTICAL.get().equals("top")) {
				dimensionNamePosY = 5 / dimensionSize;
			} else if (EnvironsConfigClient.POSITION_VERTICAL.get().equals("center")) {
				dimensionNamePosY = scaledHeight / 2 / dimensionSize - 16;
			} else {
				dimensionNamePosY = (scaledHeight - 120) / dimensionSize;
			}

			biomeNamePosY = (dimensionNamePosY + 11) * dimensionSize / biomeSize;
			structurenamePosY = (biomeNamePosY + 11) * biomeSize / structureSize;

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

			if (dimensionAlpha > 0.015 && dimensionName != null) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(dimensionSize, dimensionSize, dimensionSize);
				minecraft.font.drawInBatch(EnvironsConfigClient.UNDERLINE.get() ? dimensionName.withStyle(ChatFormatting.UNDERLINE) : dimensionName, dimensionNamePosX, dimensionNamePosY, EnvironsConfigClient.DIMENSION_COLOR.get() | (int) (dimensionAlpha * 255.0F) << 24, EnvironsConfigClient.SHADOW.get(), matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}

			if (biomeAlpha > 0.015 && biomeName != null) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(biomeSize, biomeSize, biomeSize);
				minecraft.font.drawInBatch(EnvironsConfigClient.UNDERLINE.get() ? biomeName.withStyle(ChatFormatting.UNDERLINE) : biomeName, biomeNamePosX, biomeNamePosY, EnvironsConfigClient.BIOME_COLOR.get() | (int) (biomeAlpha * 255.0F) << 24, EnvironsConfigClient.SHADOW.get(), matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}

			if (structureAlpha > 0.015 && structureName != null) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(structureSize, structureSize, structureSize);
				minecraft.font.drawInBatch(EnvironsConfigClient.UNDERLINE.get() ? structureName.withStyle(ChatFormatting.UNDERLINE) : structureName, structureNamePosX, structurenamePosY, EnvironsConfigClient.STRUCTURE_COLOR.get() | (int) (structureAlpha * 255.0F) << 24, EnvironsConfigClient.SHADOW.get(), matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}

			if (!EnvironsConfigClient.BACKDROP_STYLE.get().equals("none")) {
				int color = EnvironsConfigClient.BACKDROP_COLOR.get();

				RenderSystem.enableBlend();
				RenderSystem.disableDepthTest();
				RenderSystem.depthMask(false);
				event.getGuiGraphics().pose().pushPose();

				float clamp = 0.5F;
				int offsetX = 0;
				int offsetY = 0;

				float r = (color >> 16 & 0xFF) / 255.0F;
				float g = (color >> 8 & 0xFF) / 255.0F;
				float b = (color & 0xFF) / 255.0F;

				ResourceLocation backdrop = new ResourceLocation("textures/misc/white.png");
				if (EnvironsConfigClient.BACKDROP_STYLE.get().equals("vignette")) {
					backdrop = new ResourceLocation(Environs.MOD_ID, "textures/misc/vignette.png");
					clamp = 1.0F;
				} else if (EnvironsConfigClient.BACKDROP_STYLE.get().equals("rvignette")) {
					backdrop = new ResourceLocation(Environs.MOD_ID, "textures/misc/vignette_reverse.png");
					clamp = 1.0F;
					if (EnvironsConfigClient.POSITION_VERTICAL.get().equals("top")) {
						offsetY = -100;
					} else if (EnvironsConfigClient.POSITION_VERTICAL.get().equals("bottom")) {
						offsetY = 64;
					}
					if (EnvironsConfigClient.POSITION_HORIZONTAL.get().equals("left")) {
						offsetX = -224;
					} else if (EnvironsConfigClient.POSITION_HORIZONTAL.get().equals("right")) {
						offsetX = 224;
					}
				}

				float fade = Mth.clamp(biomeAlpha + dimensionAlpha + structureAlpha, 0.0F, clamp);

				event.getGuiGraphics().setColor(r, g, b, fade);
				RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				event.getGuiGraphics().blit(backdrop, offsetX, offsetY, -90, 0.0F, 0.0F, event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight(), event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());

				event.getGuiGraphics().pose().popPose();
				RenderSystem.depthMask(true);
				RenderSystem.enableDepthTest();
				RenderSystem.defaultBlendFunc();
			}
		}
	}

	public static void triggerDimensionTitleCard(String dimension) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeDimensionTimer > 120 || fadeDimensionTimer == 0 || EnvironsConfigClient.DIMENSION_TITLE_CARDS.get().equals("always")) {
			dimensionName = Component.translatable(dimension);
			fadeDimensionTimer = minecraft.level.getGameTime();
			if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("discover")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.AMBIENT, 0.2F, 1.0F, false);
			} else if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("bell")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.BELL_RESONATE, SoundSource.AMBIENT, 3.0F, 0.2F, false);
			} else if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("chime")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.AMBIENT, 0.2F, 0.2F, false);
			}
		}
	}

	public static void triggerBiomeTitleCard(String biome) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeBiomeTimer > 120 || fadeBiomeTimer == 0 || EnvironsConfigClient.BIOME_TITLE_CARDS.get().equals("always")) {
			biomeName = Component.translatable(biome);
			fadeBiomeTimer = minecraft.level.getGameTime();
			if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("discover")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.AMBIENT, 0.2F, 1.0F, false);
			} else if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("bell")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.BELL_RESONATE, SoundSource.AMBIENT, 3.0F, 0.2F, false);
			} else if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("chime")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.AMBIENT, 0.2F, 0.2F, false);
			}
		}
	}

	public static void triggerStructureTitleCard(String structure) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level.getGameTime() - fadeStructureTimer > 120 || fadeStructureTimer == 0 || EnvironsConfigClient.STRUCTURE_TITLE_CARDS.get().equals("always")) {
			structureName = Component.translatable(structure);
			fadeStructureTimer = minecraft.level.getGameTime();
			if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("discover")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.AMBIENT, 0.2F, 1.0F, false);
			} else if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("bell")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.BELL_RESONATE, SoundSource.AMBIENT, 3.0F, 0.2F, false);
			} else if (EnvironsConfigClient.AUDIO_QUEUE.get().equals("chime")) {
				minecraft.level.playLocalSound(minecraft.player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.AMBIENT, 0.2F, 0.2F, false);
			}
		}
	}
}
