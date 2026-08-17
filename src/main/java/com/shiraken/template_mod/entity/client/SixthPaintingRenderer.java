package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.entity.SixthPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SixthPaintingRenderer extends GeoEntityRenderer<SixthPaintingEntity> {
    public SixthPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SixthPaintingModel());
    }
}
