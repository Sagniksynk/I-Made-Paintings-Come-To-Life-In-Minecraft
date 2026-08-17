package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.SeventhPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SeventhPaintingRenderer extends GeoEntityRenderer<SeventhPaintingEntity> {
    public SeventhPaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SeventhPaintingModel());
    }

    @Override
    public ResourceLocation getTextureLocation(SeventhPaintingEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/seventh_painting.png");
    }

    @Override
    public void render(SeventhPaintingEntity entity, float entityYaw, float partialTick, com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        // Increase the size of the wither mob
        float scale = 1.35f; 
        poseStack.scale(scale, scale, scale);
        
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        
        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(SeventhPaintingEntity pLivingEntity, net.minecraft.client.renderer.culling.Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }
}
