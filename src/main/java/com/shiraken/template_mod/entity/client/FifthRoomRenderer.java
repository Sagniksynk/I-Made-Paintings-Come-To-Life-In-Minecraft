package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FifthRoomEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FifthRoomRenderer extends GeoEntityRenderer<FifthRoomEntity> {
    public FifthRoomRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FifthRoomModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(FifthRoomEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/fifth_painting.png");
    }

    @Override
    public boolean shouldRender(FifthRoomEntity pLivingEntity, net.minecraft.client.renderer.culling.Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        // Always render to prevent invisibility when looking away from its center
        return true;
    }

    @Override
    public void render(FifthRoomEntity entity, float entityYaw, float partialTick, com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        // Translate slightly up to avoid Z-fighting with the ground block
        poseStack.translate(0, 0.01, 0);
        
        // User requested not to scale down
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
