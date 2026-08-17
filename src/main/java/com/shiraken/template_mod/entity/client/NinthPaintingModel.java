package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.NinthPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NinthPaintingModel extends GeoModel<NinthPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(NinthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/ninth_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NinthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/ninth_painting.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NinthPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/ninth_painting.animation.json");
    }
}
