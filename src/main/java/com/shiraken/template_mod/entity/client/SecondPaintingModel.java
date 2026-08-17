package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.SecondPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SecondPaintingModel extends GeoModel<SecondPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(SecondPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/second_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SecondPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/second_painting.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SecondPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/second_painting.animation.json");
    }
}
