package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.TenthPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TenthPaintingModel extends GeoModel<TenthPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(TenthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/tenth_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TenthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/tenth_painting.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TenthPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/tenth_painting.animation.json");
    }
}
