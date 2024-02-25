package com.environs.listeners;

import org.joml.Matrix4f;

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

	@SubscribeEvent
	public static void onRenderGameOverlayEventPre(final RenderGuiOverlayEvent.Pre event) {
		if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
			Minecraft minecraft = Minecraft.getInstance();
			Player player = (Player) minecraft.getCameraEntity();

			MultiBufferSource.BufferSource irendertypebuffer$impl = minecraft.renderBuffers().bufferSource();
			BlockPos blockpos = BlockPos.containing(player.getEyePosition().x, player.getEyePosition().y, player.getEyePosition().z);
			String dimensionName = TextUtil.stringToProperName(TextUtil.getPath(player.level().dimension()));
			String biomeName = TextUtil.stringToCapsName(TextUtil.getPath(player.level().getBiome(blockpos)));
			int dimensionNamePosX = minecraft.getWindow().getGuiScaledWidth() / 6 - minecraft.font.width(dimensionName) / 2;
			int biomeNamePosX = minecraft.getWindow().getGuiScaledWidth() / 4 - minecraft.font.width(biomeName) / 2;

			if (fadeDimensionTimer > 0) {
				fadeDimensionTimer--;
			}

			if (fadeBiomeTimer > 0) {
				fadeBiomeTimer--;
			}

			float dimensionAlpha = Mth.clamp(fadeDimensionTimer > 400 ? -(fadeDimensionTimer - 750) / 200F : (fadeDimensionTimer - 50) / 200F, 0.0F, 1.0F);
			float biomeAlpha = Mth.clamp(fadeBiomeTimer > 400 ? -(fadeBiomeTimer - 650) / 200F : (fadeBiomeTimer - 50) / 200F, 0.0F, 1.0F);

			if (dimensionAlpha > 0.015) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(3.0F, 3.0F, 3.0F);
				minecraft.font.drawInBatch(ChatFormatting.UNDERLINE + dimensionName, dimensionNamePosX, 8, 0xFFFFFF | (int) (dimensionAlpha * 255.0F) << 24, true, matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}

			if (biomeAlpha > 0.015) {
				event.getGuiGraphics().pose().pushPose();
				Matrix4f matrix4f = event.getGuiGraphics().pose().last().pose();
				matrix4f.scale(2.0F, 2.0F, 2.0F);
				minecraft.font.drawInBatch(ChatFormatting.UNDERLINE + biomeName, biomeNamePosX, 30, 0xFFFFFF | (int) (biomeAlpha * 255.0F) << 24, true, matrix4f, irendertypebuffer$impl, Font.DisplayMode.SEE_THROUGH, 0, 0);
				event.getGuiGraphics().pose().popPose();
			}
		}
	}

	public static void triggerDimensionTitleCard() {
		fadeDimensionTimer = 800;
	}

	public static void triggerBiomeTitleCard() {
		fadeBiomeTimer = fadeDimensionTimer > 0 ? 800 : 700;
	}
}
