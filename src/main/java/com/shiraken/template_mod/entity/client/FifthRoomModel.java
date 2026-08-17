package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FifthRoomEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FifthRoomModel extends GeoModel<FifthRoomEntity> {
    @Override
    public ResourceLocation getModelResource(FifthRoomEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "geo/fifth_painting.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FifthRoomEntity object) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/fifth_painting.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FifthRoomEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "animations/fifth_painting.animation.json");
    }
}
