package com.shiraken.template_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shiraken.template_mod.entity.SecondPaintingEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SecondPaintingRenderer extends GeoEntityRenderer<SecondPaintingEntity> {
    public SecondPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SecondPaintingModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(SecondPaintingEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float scale = 0.65f;
        poseStack.scale(scale, scale, scale);
        // 60FPS Smooth Render Offset for the landing!
        if (entity.isActivated()) {
            float ticksSinceActivation = entity.clientTicksSinceActivation + partialTick;
            
            // The jump down occurs between ticks 80 (4.0s) and 100 (5.0s)
            if (ticksSinceActivation >= 80.0f && ticksSinceActivation < 100.0f) {
                float progress = (ticksSinceActivation - 80.0f) / 20.0f;
                
                double startY = entity.getEntityData().get(SecondPaintingEntity.START_Y);
                double targetY = entity.getEntityData().get(SecondPaintingEntity.TARGET_Y);
                double diffY = targetY - startY; // The total Y distance to the floor
                
                // Translate the entire visual model smoothly downwards to meet the floor!
                // We divide by the scale to ensure the offset matches true world units.
                poseStack.translate(0, (diffY * progress) / scale, 0);
            }
        }
        
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        
        poseStack.popPose();
    }
}
