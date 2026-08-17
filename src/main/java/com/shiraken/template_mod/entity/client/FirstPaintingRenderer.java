package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FirstPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FirstPaintingRenderer extends GeoEntityRenderer<FirstPaintingEntity> {
    public FirstPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FirstPaintingModel());
    }

    @Override
    public ResourceLocation getTextureLocation(FirstPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/1st_skull.png");
    }
}
