package com.shiraken.template_mod.network;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.network.packet.ActivatePaintingPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TemplateMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, ActivatePaintingPacket.class, ActivatePaintingPacket::encode, ActivatePaintingPacket::new, ActivatePaintingPacket::handle);
        INSTANCE.registerMessage(id++, com.shiraken.template_mod.network.packet.ActivateRoomPacket.class, com.shiraken.template_mod.network.packet.ActivateRoomPacket::encode, com.shiraken.template_mod.network.packet.ActivateRoomPacket::new, com.shiraken.template_mod.network.packet.ActivateRoomPacket::handle);
        INSTANCE.registerMessage(id++, com.shiraken.template_mod.network.packet.ActivatePaintingAltPacket.class, com.shiraken.template_mod.network.packet.ActivatePaintingAltPacket::encode, com.shiraken.template_mod.network.packet.ActivatePaintingAltPacket::new, com.shiraken.template_mod.network.packet.ActivatePaintingAltPacket::handle);
    }
}
