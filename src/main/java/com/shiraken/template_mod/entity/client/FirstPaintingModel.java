package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FirstPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FirstPaintingModel extends GeoModel<FirstPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(FirstPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/1st_skull.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FirstPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/1st_skull.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FirstPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/1st_skull.animation.json");
    }
}
