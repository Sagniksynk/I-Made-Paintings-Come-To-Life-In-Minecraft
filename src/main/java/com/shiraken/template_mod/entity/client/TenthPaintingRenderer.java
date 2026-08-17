package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.entity.TenthPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TenthPaintingRenderer extends GeoEntityRenderer<TenthPaintingEntity> {
    public TenthPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TenthPaintingModel());
    }
}
