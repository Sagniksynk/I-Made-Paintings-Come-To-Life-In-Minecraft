package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.EighthPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EighthPaintingModel extends GeoModel<EighthPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(EighthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/eighth_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EighthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/eighth_painting.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EighthPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/eighth_painting.animation.json");
    }
}
