package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.ThirdPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ThirdPaintingModel extends GeoModel<ThirdPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(ThirdPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/third_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ThirdPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/third_painting.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ThirdPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/third_painting.animation.json");
    }
}
