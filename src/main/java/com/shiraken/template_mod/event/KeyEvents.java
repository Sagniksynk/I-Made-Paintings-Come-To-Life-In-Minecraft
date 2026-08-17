package com.shiraken.template_mod.event;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.network.NetworkHandler;
import com.shiraken.template_mod.network.packet.ActivatePaintingPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class KeyEvents {
    
    // Register the key mapping to the game's Controls menu (Mod event bus)
    @Mod.EventBusSubscriber(modid = TemplateMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBindings.ACTIVATE_PAINTING_KEY);
            event.register(KeyBindings.ACTIVATE_PAINTING_ALT_KEY);
            event.register(KeyBindings.ACTIVATE_ROOM_KEY);
        }
    }

    // Listen for the actual key press during gameplay (Forge event bus)
    @Mod.EventBusSubscriber(modid = TemplateMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            while (KeyBindings.ACTIVATE_PAINTING_KEY.consumeClick()) {
                // Key pressed! Send signal to server to wake up paintings.
                NetworkHandler.INSTANCE.sendToServer(new ActivatePaintingPacket());
            }
            while (KeyBindings.ACTIVATE_PAINTING_ALT_KEY.consumeClick()) {
                // Alt key pressed! Send alt signal.
                NetworkHandler.INSTANCE.sendToServer(new com.shiraken.template_mod.network.packet.ActivatePaintingAltPacket());
            }
            while (KeyBindings.ACTIVATE_ROOM_KEY.consumeClick()) {
                NetworkHandler.INSTANCE.sendToServer(new com.shiraken.template_mod.network.packet.ActivateRoomPacket());
            }
        }
    }
}
