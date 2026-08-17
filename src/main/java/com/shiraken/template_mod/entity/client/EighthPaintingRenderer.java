package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.EighthPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EighthPaintingRenderer extends GeoEntityRenderer<EighthPaintingEntity> {
    public EighthPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EighthPaintingModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(EighthPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/eighth_painting.png");
    }

    @Override
    public boolean shouldRender(EighthPaintingEntity pLivingEntity, net.minecraft.client.renderer.culling.Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }
}
