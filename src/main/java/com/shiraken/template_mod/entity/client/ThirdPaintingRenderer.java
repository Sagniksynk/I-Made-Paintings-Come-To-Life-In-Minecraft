package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.ThirdPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ThirdPaintingRenderer extends GeoEntityRenderer<ThirdPaintingEntity> {
    public ThirdPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ThirdPaintingModel());
    }

    @Override
    public ResourceLocation getTextureLocation(ThirdPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/third_painting.png");
    }

    @Override
    public void render(ThirdPaintingEntity entity, float entityYaw, float partialTick, com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(1.25f, 1.25f, 1.25f);
        
        // 60FPS Smooth Render Offset for the landing!
        if (entity.isActivated()) {
            float ticksSinceActivation = entity.clientTicksSinceActivation + partialTick;
            
            // The jump down occurs between ticks 120 (6.0s) and 130 (6.5s)
            if (ticksSinceActivation >= 120.0f && ticksSinceActivation < 130.0f) {
                float progress = (ticksSinceActivation - 120.0f) / 10.0f;
                
                double startY = entity.getEntityData().get(ThirdPaintingEntity.SPAWN_Y);
                double targetY = entity.getEntityData().get(ThirdPaintingEntity.TARGET_Y);
                double diffY = targetY - startY; // The total Y distance to the floor
                
                // Translate the entire visual model smoothly downwards to meet the floor!
                // We divide by the scale (1.25f) to ensure the offset matches true world units.
                poseStack.translate(0, (diffY * progress) / 1.25f, 0);
            }
        }
        
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
