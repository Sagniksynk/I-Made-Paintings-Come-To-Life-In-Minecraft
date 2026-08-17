package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FourthPaintingEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class FourthPaintingModel extends GeoModel<FourthPaintingEntity> {
    @Override
    public ResourceLocation getModelResource(FourthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/fourth_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FourthPaintingEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/spider_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FourthPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/fourth_painting.animation.json");
    }

    @Override
    public void setCustomAnimations(FourthPaintingEntity animatable, long instanceId, AnimationState<FourthPaintingEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        GeoBone stageBone = (GeoBone) this.getAnimationProcessor().getBone("stage");
        if (stageBone != null) {
            stageBone.setHidden(true);
        }
    }
}
