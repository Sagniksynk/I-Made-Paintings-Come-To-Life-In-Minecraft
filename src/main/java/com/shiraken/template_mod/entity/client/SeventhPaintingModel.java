package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.SeventhPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SeventhPaintingModel extends GeoModel<SeventhPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(SeventhPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/seventh_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SeventhPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/seventh_painting.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SeventhPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/seventh_painting.animation.json");
    }
}
