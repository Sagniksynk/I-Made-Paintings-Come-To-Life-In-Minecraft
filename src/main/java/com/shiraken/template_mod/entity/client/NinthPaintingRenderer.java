package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.entity.NinthPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NinthPaintingRenderer extends GeoEntityRenderer<NinthPaintingEntity> {
    public NinthPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NinthPaintingModel());
    }
}
