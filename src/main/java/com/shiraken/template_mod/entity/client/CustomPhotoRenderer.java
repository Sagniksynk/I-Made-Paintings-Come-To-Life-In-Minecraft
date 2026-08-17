package com.shiraken.template_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.CustomPhotoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class CustomPhotoRenderer extends EntityRenderer<CustomPhotoEntity> {
    // TWEAK THIS NUMBER: This defines exactly how many ticks to wait before swapping the texture!
    // 20 ticks = 1.0 second. 40 ticks = 2.0 seconds. 60 ticks = 3.0 seconds.
    // 12 ticks corresponds to 0.4 seconds on the Blockbench timeline at 0.666 animation speed.
    public static int SWAP_TIME_IN_TICKS = 12;

    private static final ResourceLocation SKULL_TEXTURE = new ResourceLocation(TemplateMod.MODID, "textures/entity/burning_skull.png");
    private static final ResourceLocation SKULL_AFTER_TEXTURE = new ResourceLocation(TemplateMod.MODID, "textures/entity/burning_skull_after.png");

    public CustomPhotoRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CustomPhotoEntity entity) {
        if (entity.isActivated()) {
            int ticksSinceActivation = entity.tickCount - entity.getActivationTick();
            // It waits the exact amount of ticks defined above to perfectly match the speed of the skeleton leaving
            if (ticksSinceActivation >= SWAP_TIME_IN_TICKS) {
                return SKULL_AFTER_TEXTURE;
            }
        }
        return SKULL_TEXTURE;
    }

    @Override
    public void render(CustomPhotoEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Rotate to match the block face
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F - entityYaw));
        
        int widthBlocks = entity.getPhotoWidth();
        int heightBlocks = entity.getPhotoHeight();

        // The entity origin sits at the CENTER of the air block in front of the wall.
        // After the 180-degree Y rotation, local +Z points toward the wall.
        // So to sit flush: back face at +0.5 (wall surface), front face at +0.5 - 0.0625 = +0.4375.
        // We bake this directly into the vertex Z values.
        // The exact middle point between 0.0625 (outside) and 0.5 (inside)
        // is 0.28125.
        final float FRONT_Z = 0.25f; 
        final float BACK_Z  = 0.3125f;        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entitySolid(this.getTextureLocation(entity)));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        // Centered around the middle of the painting
        float startX = -widthBlocks / 2.0f;
        float endX = widthBlocks / 2.0f;
        float startY = -heightBlocks / 2.0f;
        float endY = heightBlocks / 2.0f;

        // Draw Front Face (Visible part - facing outward from wall)
        vertex(matrix4f, matrix3f, vertexconsumer, startX, startY, FRONT_Z, 0, 1, packedLight);
        vertex(matrix4f, matrix3f, vertexconsumer, endX, startY, FRONT_Z, 1, 1, packedLight);
        vertex(matrix4f, matrix3f, vertexconsumer, endX, endY, FRONT_Z, 1, 0, packedLight);
        vertex(matrix4f, matrix3f, vertexconsumer, startX, endY, FRONT_Z, 0, 0, packedLight);

        vertex(matrix4f, matrix3f, vertexconsumer, endX, startY, BACK_Z, 0, 1, packedLight);
        vertex(matrix4f, matrix3f, vertexconsumer, startX, startY, BACK_Z, 1, 1, packedLight);
        vertex(matrix4f, matrix3f, vertexconsumer, startX, endY, BACK_Z, 1, 0, packedLight);
        vertex(matrix4f, matrix3f, vertexconsumer, endX, endY, BACK_Z, 0, 0, packedLight);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private static void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, int packedLight) {
        vertexConsumer.vertex(matrix4f, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(matrix3f, 0.0F, 0.0F, -1.0F)
                .endVertex();
    }
}
