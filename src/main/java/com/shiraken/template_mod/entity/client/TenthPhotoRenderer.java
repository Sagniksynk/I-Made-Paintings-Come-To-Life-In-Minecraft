package com.shiraken.template_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.TenthPhotoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class TenthPhotoRenderer extends EntityRenderer<TenthPhotoEntity> {
    private static final ResourceLocation FRAME_TEXTURE = new ResourceLocation(TemplateMod.MODID, "textures/entity/tenth_burning_skull.png");

    public TenthPhotoRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(TenthPhotoEntity entity) {
        return FRAME_TEXTURE;
    }

    @Override
    public void render(TenthPhotoEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F - entityYaw));
        int widthBlocks = entity.getPhotoWidth();
        int heightBlocks = entity.getPhotoHeight();

        final float FRONT_Z = -0.046875f; 
        final float BACK_Z  = 0.015625f;
        
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entitySolid(this.getTextureLocation(entity)));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        float startX = -widthBlocks / 2.0f;
        float endX = widthBlocks / 2.0f;
        float startY = -heightBlocks / 2.0f;
        float endY = heightBlocks / 2.0f;

        vertex(matrix4f, matrix3f, vertexconsumer, startX, startY, FRONT_Z, 0, 1, packedLight, 0.0F, 0.0F, -1.0F);
        vertex(matrix4f, matrix3f, vertexconsumer, endX, startY, FRONT_Z, 1, 1, packedLight, 0.0F, 0.0F, -1.0F);
        vertex(matrix4f, matrix3f, vertexconsumer, endX, endY, FRONT_Z, 1, 0, packedLight, 0.0F, 0.0F, -1.0F);
        vertex(matrix4f, matrix3f, vertexconsumer, startX, endY, FRONT_Z, 0, 0, packedLight, 0.0F, 0.0F, -1.0F);

        vertex(matrix4f, matrix3f, vertexconsumer, endX, startY, BACK_Z, 0, 1, packedLight, 0.0F, 0.0F, 1.0F);
        vertex(matrix4f, matrix3f, vertexconsumer, startX, startY, BACK_Z, 1, 1, packedLight, 0.0F, 0.0F, 1.0F);
        vertex(matrix4f, matrix3f, vertexconsumer, startX, endY, BACK_Z, 1, 0, packedLight, 0.0F, 0.0F, 1.0F);
        vertex(matrix4f, matrix3f, vertexconsumer, endX, endY, BACK_Z, 0, 0, packedLight, 0.0F, 0.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private static void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, int packedLight, float nx, float ny, float nz) {
        vertexConsumer.vertex(matrix4f, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(matrix3f, nx, ny, nz)
                .endVertex();
    }
}
