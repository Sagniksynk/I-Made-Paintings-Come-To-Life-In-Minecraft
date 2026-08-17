package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FifthDoorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FifthDoorModel extends GeoModel<FifthDoorEntity> {
    @Override
    public ResourceLocation getModelResource(FifthDoorEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/fifth_door.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FifthDoorEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/fifth_door.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FifthDoorEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/fifth_door.animation.json");
    }
}
