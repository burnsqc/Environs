package com.environs.client.renderer;

import org.joml.Matrix4f;

import com.environs.Environs;
import com.environs.client.multiplayer.ClientLevelDataExtension;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.IForgeDimensionSpecialEffects;

public class RareMoonOverworldRenderer extends DimensionSpecialEffects implements IForgeDimensionSpecialEffects {
	private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation BLOOD_MOON_LOCATION = new ResourceLocation(Environs.MOD_ID, "textures/environment/blood_moon_phases.png");
	private static final ResourceLocation FORTUNE_MOON_LOCATION = new ResourceLocation(Environs.MOD_ID, "textures/environment/fortune_moon_phases.png");
	private static final ResourceLocation HARVEST_MOON_LOCATION = new ResourceLocation(Environs.MOD_ID, "textures/environment/harvest_moon_phases.png");
	private static final ResourceLocation BLUE_MOON_LOCATION = new ResourceLocation(Environs.MOD_ID, "textures/environment/blue_moon_phases.png");
	private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
	private final Minecraft minecraft;
	private VertexBuffer starBuffer;
	private VertexBuffer skyBuffer;
	private VertexBuffer darkBuffer;

	public RareMoonOverworldRenderer() {
		super(Float.NaN, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
		this.minecraft = Minecraft.getInstance();
		this.createStars();
		this.createLightSky();
		this.createDarkSky();
	}

	@Override
	public boolean renderSky(ClientLevel level, int ticks, float p_202426_, PoseStack p_202424_, Camera p_202427_, Matrix4f p_254034_, boolean p_202428_, Runnable p_202429_) {
		p_202429_.run();
		if (!p_202428_) {
			FogType fogtype = p_202427_.getFluidInCamera();
			if (fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA && !this.doesMobEffectBlockSky(p_202427_)) {
				if (minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL) {
					Vec3 vec3 = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), p_202426_);
					float f = (float) vec3.x;
					float f1 = (float) vec3.y;
					float f2 = (float) vec3.z;
					FogRenderer.levelFogColor();
					BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
					RenderSystem.depthMask(false);

					long time = minecraft.level.getDayTime() - 12000;
					float red = (ClientLevelDataExtension.getMoon() == 1) ? Mth.clamp(time < 12000 ? time / 4000F : -(time - 12000) / 4000F, 0.0F, 0.1F) : 0;
					float yellow = (ClientLevelDataExtension.getMoon() == 2) ? Mth.clamp(time < 12000 ? time / 4000F : -(time - 12000) / 4000F, 0.0F, 0.1F) : 0;
					float green = (ClientLevelDataExtension.getMoon() == 3) ? Mth.clamp(time < 12000 ? time / 4000F : -(time - 12000) / 4000F, 0.0F, 0.1F) : 0;
					float blue = (ClientLevelDataExtension.getMoon() == 4) ? Mth.clamp(time < 12000 ? time / 2000F : -(time - 12000) / 2000F, 0.0F, 0.2F) : 0;

					RenderSystem.setShaderColor(f + red + yellow, f1 + green + yellow, f2 + blue, 1.0F);

					ShaderInstance shaderinstance = RenderSystem.getShader();
					this.skyBuffer.bind();
					this.skyBuffer.drawWithShader(p_202424_.last().pose(), p_254034_, shaderinstance);
					VertexBuffer.unbind();
					RenderSystem.enableBlend();
					float[] afloat = level.effects().getSunriseColor(level.getTimeOfDay(p_202426_), p_202426_);
					if (afloat != null) {
						RenderSystem.setShader(GameRenderer::getPositionColorShader);
						RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
						p_202424_.pushPose();
						p_202424_.mulPose(Axis.XP.rotationDegrees(90.0F));
						float f3 = Mth.sin(level.getSunAngle(p_202426_)) < 0.0F ? 180.0F : 0.0F;
						p_202424_.mulPose(Axis.ZP.rotationDegrees(f3));
						p_202424_.mulPose(Axis.ZP.rotationDegrees(90.0F));
						float f4 = afloat[0];
						float f5 = afloat[1];
						float f6 = afloat[2];
						Matrix4f matrix4f = p_202424_.last().pose();
						bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
						bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();

						for (int j = 0; j <= 16; ++j) {
							float f7 = j * ((float) Math.PI * 2F) / 16.0F;
							float f8 = Mth.sin(f7);
							float f9 = Mth.cos(f7);
							bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
						}

						BufferUploader.drawWithShader(bufferbuilder.end());
						p_202424_.popPose();
					}

					RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					p_202424_.pushPose();
					float f11 = 1.0F - level.getRainLevel(p_202426_);
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
					p_202424_.mulPose(Axis.YP.rotationDegrees(-90.0F));
					p_202424_.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(p_202426_) * 360.0F));
					Matrix4f matrix4f1 = p_202424_.last().pose();
					float f12 = 30.0F;
					RenderSystem.setShader(GameRenderer::getPositionTexShader);
					RenderSystem.setShaderTexture(0, SUN_LOCATION);
					bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
					bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
					bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
					bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
					bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
					BufferUploader.drawWithShader(bufferbuilder.end());
					f12 = 20.0F;

					if (ClientLevelDataExtension.getMoon() == 1) {
						RenderSystem.setShaderTexture(0, BLOOD_MOON_LOCATION);
					} else if (ClientLevelDataExtension.getMoon() == 2) {
						RenderSystem.setShaderTexture(0, FORTUNE_MOON_LOCATION);
					} else if (ClientLevelDataExtension.getMoon() == 3) {
						RenderSystem.setShaderTexture(0, HARVEST_MOON_LOCATION);
					} else if (ClientLevelDataExtension.getMoon() == 4) {
						RenderSystem.setShaderTexture(0, BLUE_MOON_LOCATION);
					} else {
						RenderSystem.setShaderTexture(0, MOON_LOCATION);
					}

					int k = level.getMoonPhase();
					int l = k % 4;
					int i1 = k / 4 % 2;
					float f13 = (l + 0) / 4.0F;
					float f14 = (i1 + 0) / 2.0F;
					float f15 = (l + 1) / 4.0F;
					float f16 = (i1 + 1) / 2.0F;
					bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
					bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
					bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
					bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
					bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
					BufferUploader.drawWithShader(bufferbuilder.end());
					float f10 = level.getStarBrightness(p_202426_) * f11;
					if (f10 > 0.0F) {
						RenderSystem.setShaderColor(f10, f10, f10, f10);
						FogRenderer.setupNoFog();
						starBuffer.bind();
						starBuffer.drawWithShader(p_202424_.last().pose(), p_254034_, GameRenderer.getPositionShader());
						VertexBuffer.unbind();
						p_202429_.run();
					}

					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					RenderSystem.disableBlend();
					RenderSystem.defaultBlendFunc();
					p_202424_.popPose();
					RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
					double d0 = minecraft.player.getEyePosition(p_202426_).y - level.getLevelData().getHorizonHeight(level);
					if (d0 < 0.0D) {
						p_202424_.pushPose();
						p_202424_.translate(0.0F, 12.0F, 0.0F);
						this.darkBuffer.bind();
						this.darkBuffer.drawWithShader(p_202424_.last().pose(), p_254034_, shaderinstance);
						VertexBuffer.unbind();
						p_202424_.popPose();
					}

					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					RenderSystem.depthMask(true);
				}
			}
		}
		return true;
	}

	private void createStars() {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		if (this.starBuffer != null) {
			this.starBuffer.close();
		}

		this.starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.drawStars(bufferbuilder);
		this.starBuffer.bind();
		this.starBuffer.upload(bufferbuilder$renderedbuffer);
		VertexBuffer.unbind();
	}

	private BufferBuilder.RenderedBuffer drawStars(BufferBuilder p_234260_) {
		RandomSource randomsource = RandomSource.create(10842L);
		p_234260_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

		for (int i = 0; i < 1500; ++i) {
			double d0 = randomsource.nextFloat() * 2.0F - 1.0F;
			double d1 = randomsource.nextFloat() * 2.0F - 1.0F;
			double d2 = randomsource.nextFloat() * 2.0F - 1.0F;
			double d3 = 0.15F + randomsource.nextFloat() * 0.1F;
			double d4 = d0 * d0 + d1 * d1 + d2 * d2;
			if (d4 < 1.0D && d4 > 0.01D) {
				d4 = 1.0D / Math.sqrt(d4);
				d0 *= d4;
				d1 *= d4;
				d2 *= d4;
				double d5 = d0 * 100.0D;
				double d6 = d1 * 100.0D;
				double d7 = d2 * 100.0D;
				double d8 = Math.atan2(d0, d2);
				double d9 = Math.sin(d8);
				double d10 = Math.cos(d8);
				double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
				double d12 = Math.sin(d11);
				double d13 = Math.cos(d11);
				double d14 = randomsource.nextDouble() * Math.PI * 2.0D;
				double d15 = Math.sin(d14);
				double d16 = Math.cos(d14);

				for (int j = 0; j < 4; ++j) {
					double d18 = ((j & 2) - 1) * d3;
					double d19 = ((j + 1 & 2) - 1) * d3;
					double d21 = d18 * d16 - d19 * d15;
					double d22 = d19 * d16 + d18 * d15;
					double d23 = d21 * d12 + 0.0D * d13;
					double d24 = 0.0D * d12 - d21 * d13;
					double d25 = d24 * d9 - d22 * d10;
					double d26 = d22 * d9 + d24 * d10;
					p_234260_.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
				}
			}
		}

		return p_234260_.end();
	}

	private void createLightSky() {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		if (this.skyBuffer != null) {
			this.skyBuffer.close();
		}

		this.skyBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = buildSkyDisc(bufferbuilder, 16.0F);
		this.skyBuffer.bind();
		this.skyBuffer.upload(bufferbuilder$renderedbuffer);
		VertexBuffer.unbind();
	}

	private static BufferBuilder.RenderedBuffer buildSkyDisc(BufferBuilder p_234268_, float p_234269_) {
		float f = Math.signum(p_234269_) * 512.0F;
		RenderSystem.setShader(GameRenderer::getPositionShader);
		p_234268_.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
		p_234268_.vertex(0.0D, p_234269_, 0.0D).endVertex();

		for (int i = -180; i <= 180; i += 45) {
			p_234268_.vertex(f * Mth.cos(i * ((float) Math.PI / 180F)), p_234269_, 512.0F * Mth.sin(i * ((float) Math.PI / 180F))).endVertex();
		}

		return p_234268_.end();
	}

	private void createDarkSky() {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		if (this.darkBuffer != null) {
			this.darkBuffer.close();
		}

		this.darkBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = buildSkyDisc(bufferbuilder, -16.0F);
		this.darkBuffer.bind();
		this.darkBuffer.upload(bufferbuilder$renderedbuffer);
		VertexBuffer.unbind();
	}

	private boolean doesMobEffectBlockSky(Camera p_234311_) {
		Entity entity = p_234311_.getEntity();
		if (!(entity instanceof LivingEntity livingentity)) {
			return false;
		} else {
			return livingentity.hasEffect(MobEffects.BLINDNESS) || livingentity.hasEffect(MobEffects.DARKNESS);
		}
	}

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 p_108908_, float p_108909_) {
		long time = minecraft.level.getDayTime() - 12000;
		float red = (ClientLevelDataExtension.getMoon() == 1) ? Mth.clamp(time < 6000 ? time / 4000F : -(time - 12000) / 4000F, 0.0F, 0.1F) : 0;
		float yellow = (ClientLevelDataExtension.getMoon() == 2) ? Mth.clamp(time < 6000 ? time / 4000F : -(time - 12000) / 4000F, 0.0F, 0.1F) : 0;
		float green = (ClientLevelDataExtension.getMoon() == 3) ? Mth.clamp(time < 6000 ? time / 4000F : -(time - 12000) / 400F, 0.0F, 0.1F) : 0;
		float blue = (ClientLevelDataExtension.getMoon() == 4) ? Mth.clamp(time < 6000 ? time / 2000F : -(time - 12000) / 2000F, 0.0F, 0.2F) : 0;
		return p_108908_.multiply(p_108909_ * 0.94F + 0.06F + red + yellow, p_108909_ * 0.94F + 0.06F + green + yellow, p_108909_ * 0.91F + 0.09F + blue);
	}

	@Override
	public boolean isFoggyAt(int p_108905_, int p_108906_) {
		return false;
	}
}
