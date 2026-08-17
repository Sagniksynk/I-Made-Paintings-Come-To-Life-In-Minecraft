package com.shiraken.template_mod.event;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.entity.FirstPaintingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TemplateMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(TemplateMod.FIRST_PAINTING.get(), FirstPaintingEntity.createAttributes().build());
        event.put(TemplateMod.SECOND_PAINTING.get(), com.shiraken.template_mod.entity.SecondPaintingEntity.createAttributes().build());
        event.put(TemplateMod.THIRD_PAINTING.get(), com.shiraken.template_mod.entity.ThirdPaintingEntity.createAttributes().build());
        event.put(TemplateMod.FOURTH_PAINTING.get(), com.shiraken.template_mod.entity.FourthPaintingEntity.createAttributes().build());
        event.put(TemplateMod.FIFTH_ROOM.get(), com.shiraken.template_mod.entity.FifthRoomEntity.createAttributes().build());
        event.put(TemplateMod.FIFTH_DOOR.get(), com.shiraken.template_mod.entity.FifthDoorEntity.createAttributes().build());
        event.put(TemplateMod.SIXTH_PAINTING.get(), com.shiraken.template_mod.entity.SixthPaintingEntity.createAttributes().build());
        event.put(TemplateMod.SEVENTH_PAINTING.get(), com.shiraken.template_mod.entity.SeventhPaintingEntity.createAttributes().build());
        event.put(TemplateMod.EIGHTH_PAINTING.get(), com.shiraken.template_mod.entity.EighthPaintingEntity.createAttributes().build());
        event.put(TemplateMod.NINTH_PAINTING.get(), com.shiraken.template_mod.entity.NinthPaintingEntity.createAttributes().build());
        event.put(TemplateMod.TENTH_PAINTING.get(), com.shiraken.template_mod.entity.TenthPaintingEntity.createAttributes().build());
    }
}

