package com.shiraken.template_mod.entity.client;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FifthDoorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FifthDoorRenderer extends GeoEntityRenderer<FifthDoorEntity> {
    public FifthDoorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FifthDoorModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(FifthDoorEntity animatable) {
        return new ResourceLocation(TemplateMod.MODID, "textures/entity/fifth_door.png");
    }

    @Override
    public boolean shouldRender(FifthDoorEntity pLivingEntity, net.minecraft.client.renderer.culling.Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        // Always render to prevent invisibility when looking away from its center
        return true;
    }

    @Override
    public void render(FifthDoorEntity entity, float entityYaw, float partialTick, com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        
        // Scale down to approximately 10x10 blocks
        // Original size: ~24 blocks wide, ~20 blocks high
        // Factor ~0.45 makes it about 10.7 wide, 9 high
        poseStack.scale(0.45f, 0.45f, 0.45f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
