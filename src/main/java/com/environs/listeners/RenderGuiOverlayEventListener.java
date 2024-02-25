package com.environs.listeners;

import org.joml.Matrix4f;

import com.environs.setup.config.EnvironsConfigClient;
import com.environs.util.TextUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderGuiOverlayEventListener {

	private static int fadeDimensionTimer;
	private static int fadeBiomeTimer;
	private static String structureName;

	@SubscribeEvent
	public static void onRenderGameOverlayPreEvent(final RenderGuiOverlayEvent.Pre event) {
		if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
			Minecraft minecraft = Minecraft.getInstance();
			Player player = (Player) minecraft.getCameraEntity();

			MultiBufferSource.BufferSource irendertypebuffer$impl = minecraft.renderBuffers().bufferSource();
			BlockPos blockpos = BlockPos.containing(player.getEyePosition().x, player.getEyePosition().y, player.getEyePosition().z);
			String dimensionName = TextUtil.stringToProperName(TextUtil.getPath(player.level().dimension()));
			String biomeName = TextUtil.stringToCapsName(TextUtil.getPath(player.level().getBiome(blockpos)));

			float scaledWidth25 = minecraft.getWindow().getGuiScaledWidth() / 2.5F;
			float scaledWidth2 = minecraft.getWindow().getGuiScaledWidth() / 2.0F;
			float scaledHeight25 = minecraft.getWindow().getGuiScaledHeight() / 2.5F;

			float dimensionWidth = minecraft.font.width(dimensionName);
			float biomeWidth = minecraft.font.width(biomeName);

			float dimensionNamePosX;
			float biomeNamePosX;
			float dimensionNamePosY;
			float biomeNamePosY;

			if (EnvironsConfigClient.POSITION_HORIZONTAL.get().equals("left")) {
				dimensionNamePosX = 2.0F;
				biomeNamePosX = 2.5F;
			} else if (EnvironsConfigClient.POSITION_HORIZONTAL.get().equals("center")) {
				dimensionNamePosX = (scaledWidth25 - dimensionWidth) / 2;
				biomeNamePosX = (scaledWidth2 - biomeWidth) / 2;
			} else {
				dimensionNamePosX = scaledWidth25 - dimensionWidth - 2.0F;
				biomeNamePosX = scaledWidth2 - biomeWidth - 2.5F;
			}

			if (EnvironsConfigClient.POSITION_VERTICAL.get().equals("top")) {
				dimensionNamePosY = 2.0F;
			} else if (EnvironsConfigClient.POSITION_VERTICAL.get().equals("center")) {
				dimensionNamePosY = scaledHeight25 / 2 - 15.0F;
			} else {
				dimensionNamePosY = scaledHeight25 - 35.0F;
			}

			biomeNamePosY = dimensionNamePosY * 1.25F + 15.0F;

			if (fadeDimensionTimer > 0) {
				fadeDimensionTimer--;
			}

			if (fadeBiomeTimer > 0) {
				fadeBiomeTimer--;
			}

			float dimensionAlpha = Mth.clamp(fadeDimensionTimer > 400 ? -(fadeDimensionTimer - 750) / 200F : (fadeDimensionTimer - 50) / 200F, 0.0F, 1.0F);
			float biomeAlpha = Mth.clamp(fadeBiomeTimer > 400 ? -(fadeBiomeTimer - 650) / 200F : (fadeBiomeTimer - 50) / 200F, 0.0F, 1.0F);
			float structureAlpha = Mth.clamp(fadeBiomeTimer > 400 ? -(fadeBiomeTimer - 650) / 200F : (fadeBiomeTimer - 50) / 200F, 0.0F, 1.0F);

			if (dimensionAlpha > 0.015 || true) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(2.5F, 2.5F, 2.5F);
				minecraft.font.drawInBatch(ChatFormatting.UNDERLINE + dimensionName, dimensionNamePosX, dimensionNamePosY, 0xFFFFFF | (int) (dimensionAlpha * 255.0F) << 24, true, matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}

			if (biomeAlpha > 0.015 || true) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(2.0F, 2.0F, 2.0F);
				minecraft.font.drawInBatch(ChatFormatting.UNDERLINE + biomeName, biomeNamePosX, biomeNamePosY, 0xFFFFFF | (int) (biomeAlpha * 255.0F) << 24, true, matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}

			if (structureAlpha > 0.015 || true) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(1.5F, 1.5F, 1.5F);
				minecraft.font.drawInBatch(ChatFormatting.UNDERLINE + structureName, biomeNamePosX, biomeNamePosY + 20, 0xFFFFFF | (int) (biomeAlpha * 255.0F) << 24, true, matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}
		}
	}

	public static void triggerDimensionTitleCard() {
		if (fadeDimensionTimer == 0) {
			fadeDimensionTimer = 800;
		}
	}

	public static void triggerBiomeTitleCard() {
		if (fadeBiomeTimer == 0) {
			fadeBiomeTimer = fadeDimensionTimer > 0 ? 800 : 700;
		}
	}

	public static void setStructure(String name) {
		structureName = name;
	}
}
