package com.shiraken.template_mod.event;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.client.CustomPhotoRenderer;
import com.shiraken.template_mod.entity.client.FirstPaintingRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TemplateMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TemplateMod.FIRST_PAINTING.get(), FirstPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.CUSTOM_PHOTO.get(), CustomPhotoRenderer::new);
        event.registerEntityRenderer(TemplateMod.SECOND_PAINTING.get(), com.shiraken.template_mod.entity.client.SecondPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.SECOND_PHOTO.get(), com.shiraken.template_mod.entity.client.SecondPhotoRenderer::new);
        event.registerEntityRenderer(TemplateMod.THIRD_PAINTING.get(), com.shiraken.template_mod.entity.client.ThirdPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.THIRD_PHOTO.get(), com.shiraken.template_mod.entity.client.ThirdPhotoRenderer::new);
        event.registerEntityRenderer(TemplateMod.FOURTH_PAINTING.get(), com.shiraken.template_mod.entity.client.FourthPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.FOURTH_PHOTO.get(), com.shiraken.template_mod.entity.client.FourthPhotoRenderer::new);
        event.registerEntityRenderer(TemplateMod.FIFTH_ROOM.get(), com.shiraken.template_mod.entity.client.FifthRoomRenderer::new);
        event.registerEntityRenderer(TemplateMod.FIFTH_DOOR.get(), com.shiraken.template_mod.entity.client.FifthDoorRenderer::new);
        event.registerEntityRenderer(TemplateMod.SIXTH_PAINTING.get(), com.shiraken.template_mod.entity.client.SixthPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.SIXTH_PHOTO.get(), com.shiraken.template_mod.entity.client.SixthPhotoRenderer::new);
        event.registerEntityRenderer(TemplateMod.SEVENTH_PAINTING.get(), com.shiraken.template_mod.entity.client.SeventhPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.SEVENTH_PHOTO.get(), com.shiraken.template_mod.entity.client.SeventhPhotoRenderer::new);
        event.registerEntityRenderer(TemplateMod.EIGHTH_PAINTING.get(), com.shiraken.template_mod.entity.client.EighthPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.EIGHTH_PHOTO.get(), com.shiraken.template_mod.entity.client.EighthPhotoRenderer::new);
        event.registerEntityRenderer(TemplateMod.NINTH_PAINTING.get(), com.shiraken.template_mod.entity.client.NinthPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.NINTH_PHOTO.get(), com.shiraken.template_mod.entity.client.NinthPhotoRenderer::new);
        event.registerEntityRenderer(TemplateMod.TENTH_PAINTING.get(), com.shiraken.template_mod.entity.client.TenthPaintingRenderer::new);
        event.registerEntityRenderer(TemplateMod.TENTH_PHOTO.get(), com.shiraken.template_mod.entity.client.TenthPhotoRenderer::new);
    }
}

