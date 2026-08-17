package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.SixthPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SixthPaintingModel extends GeoModel<SixthPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(SixthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/sixth_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SixthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/sixth_painting.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SixthPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/sixth_painting.animation.json");
    }
}
