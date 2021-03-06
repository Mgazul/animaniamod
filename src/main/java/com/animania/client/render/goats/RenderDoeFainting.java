package com.animania.client.render.goats;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.animania.client.models.goats.ModelDoeFainting;
import com.animania.common.entities.goats.EntityAnimaniaGoat;
import com.animania.common.entities.goats.EntityDoeFainting;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDoeFainting<T extends EntityDoeFainting> extends RenderLiving<T>
{
    public static final Factory           FACTORY          = new Factory();
    private static final ResourceLocation goatTextures      = new ResourceLocation("animania:textures/entity/goats/doe_fainting.png");
    private static final ResourceLocation goatTexturesBlink = new ResourceLocation("animania:textures/entity/goats/doe_fainting_blink.png");
    Random                                rand             = new Random();

    public RenderDoeFainting(RenderManager rm) {
        super(rm, new ModelDoeFainting(), 0.3F);
    }

    protected ResourceLocation getGoatTextures(T par1Entity) {
        return RenderDoeFainting.goatTextures;
    }

    protected ResourceLocation getGoatTexturesBlink(T par1Entity) {
        return RenderDoeFainting.goatTexturesBlink;
    }

    protected void preRenderScale(EntityDoeFainting entity, float f) {
        GL11.glScalef(0.4F, 0.4F, 0.4F);
        
        boolean isSleeping = false;
		EntityAnimaniaGoat entityGoat = (EntityAnimaniaGoat) entity;
		if (entityGoat.getSleeping()) {
			isSleeping = true;
		}
		
        if (!entity.getSleeping() && entity.getSpooked() && entity.getSpookedTimer() < 0.94F && entity.getSpookedTimer() > 0.06F)  {
			GlStateManager.translate(0.0F, entity.height - 1.5F, 0.0F);
			GlStateManager.rotate(86.0F, 0.0F, 0.0F, 1.0F);
			this.shadowSize = 0;
		} else {
			GL11.glTranslatef(0f, 0f, -0.5f);
			this.shadowSize = 0.3F;
		}
        GL11.glTranslatef(0f, 0f, -0.5f);
       

		if (isSleeping) {
			this.shadowSize = 0;
			float sleepTimer = entityGoat.getSleepTimer();
			if (sleepTimer > - 0.55F) {
				sleepTimer = sleepTimer - 0.01F;
			}
			entity.setSleepTimer(sleepTimer);

			GlStateManager.translate(-0.25F, entity.height - 1.10F - sleepTimer, -0.25F);
			GlStateManager.rotate(6.0F, 0.0F, 0.0F, 1.0F);
		} else {
			this.shadowSize = 0.3F;
			entityGoat.setSleeping(false);
			entityGoat.setSleepTimer(0F);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		int blinkTimer = entity.blinkTimer;
		long currentTime = entity.world.getWorldTime() % 23999;
		boolean isSleeping = false;

		EntityAnimaniaGoat entityGoat = (EntityAnimaniaGoat) entity;
		isSleeping = entityGoat.getSleeping();
		float sleepTimer = entityGoat.getSleepTimer();

		if (isSleeping && sleepTimer <= -0.55F && currentTime < 23250) {
			return this.getGoatTexturesBlink(entity);
		} else if (blinkTimer < 7 && blinkTimer >= 0) {
			return this.getGoatTexturesBlink(entity);
		} else {
			return this.getGoatTextures(entity);
		}
	}

    @Override
    protected void preRenderCallback(T entityliving, float f) {
        this.preRenderScale(entityliving, f);
    }
  
    static class Factory<T extends EntityDoeFainting> implements IRenderFactory<T>
    {
        @Override
        public Render<? super T> createRenderFor(RenderManager manager) {
            return new RenderDoeFainting(manager);
        }

    }
}
