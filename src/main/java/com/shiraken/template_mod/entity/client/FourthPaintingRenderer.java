package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FourthPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FourthPaintingRenderer extends GeoEntityRenderer<FourthPaintingEntity> {
    public FourthPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FourthPaintingModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(FourthPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/spider_texture.png");
    }

    @Override
    public void render(FourthPaintingEntity entity, float entityYaw, float partialTick, com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        
        // Base scale so it fits in the painting
        float scale = 0.20f; 
        
        if (entity.isActivated()) {
            int ticks = entity.tickCount - entity.getActivationTick();
            int totalLocked = 90; // ANIM_TICKS (80) + BUFFER_TICKS (10)
            
            // Once the break animation finishes and it starts walking, smoothly increase size
            if (ticks >= totalLocked) {
                float progress = Math.min(1.0f, (ticks - totalLocked + partialTick) / 20.0f);
                scale = 0.20f + (0.60f * progress); // Scale up to 0.80f over 1 second
            }
        }
        
        poseStack.scale(scale, scale, scale);
        
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        
        poseStack.popPose();
    }
}
