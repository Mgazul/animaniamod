package com.animania.client.render.cows;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.animania.client.models.ModelBullLonghorn;
import com.animania.common.entities.cows.EntityAnimaniaCow;
import com.animania.common.entities.cows.EntityBullLonghorn;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBullLonghorn<T extends EntityBullLonghorn> extends RenderLiving<T>
{
	public static final Factory           FACTORY          = new Factory();

	private static final ResourceLocation cowTextures      = new ResourceLocation("animania:textures/entity/cows/bull_longhorn.png");
	private static final ResourceLocation cowTexturesBlink = new ResourceLocation("animania:textures/entity/cows/bull_longhorn_blink.png");
	Random                                rand             = new Random();

	public RenderBullLonghorn(RenderManager rm) {
		super(rm, new ModelBullLonghorn(), 0.5F);
	}

	protected ResourceLocation getCowTextures(T par1EntityCow) {
		return RenderBullLonghorn.cowTextures;
	}

	protected ResourceLocation getCowTexturesBlink(T par1EntityCow) {
		return RenderBullLonghorn.cowTexturesBlink;
	}

	protected void preRenderScale(T entity, float f) {
		GL11.glScalef(1.5F, 1.5F, 1.5F); 
		boolean isSleeping = false;
		EntityAnimaniaCow entityCow = (EntityAnimaniaCow) entity;
		if (entityCow.getSleeping()) {
			isSleeping = true;
		}

		if (isSleeping) {

			float sleepTimer = entityCow.getSleepTimer();
			if (sleepTimer > - 0.55F) {
				sleepTimer = sleepTimer - 0.01F;
			}
			entity.setSleepTimer(sleepTimer);

			GlStateManager.translate(-0.25F, entity.height - 1.85F - sleepTimer, -0.25F);
			GlStateManager.rotate(6.0F, 0.0F, 0.0F, 1.0F);
		} else {
			entityCow.setSleeping(false);
			entityCow.setSleepTimer(0F);
		}

	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		int blinkTimer = entity.blinkTimer;
		long currentTime = entity.world.getWorldTime() % 23999;
		boolean isSleeping = false;

		EntityAnimaniaCow entityCow = (EntityAnimaniaCow) entity;
		isSleeping = entityCow.getSleeping();
		float sleepTimer = entityCow.getSleepTimer();

		if (isSleeping && sleepTimer <= -0.55F && currentTime < 23250) {
			return this.getCowTexturesBlink(entity);
		} else if (blinkTimer < 7 && blinkTimer >= 0) {
			return this.getCowTexturesBlink(entity);
		} else {
			return this.getCowTextures(entity);
		}

	}

	@Override
	protected void preRenderCallback(T entityliving, float f) {
		this.preRenderScale(entityliving, f);
	}

	static class Factory<T extends EntityBullLonghorn> implements IRenderFactory<T>
	{
		@Override
		public Render<? super T> createRenderFor(RenderManager manager) {
			return new RenderBullLonghorn(manager);
		}
	}
}
