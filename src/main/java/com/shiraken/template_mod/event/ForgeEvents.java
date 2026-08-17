package com.shiraken.template_mod.event;

import com.shiraken.template_mod.TemplateMod;
import com.shiraken.template_mod.command.RemovePaintingsCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TemplateMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        RemovePaintingsCommand.register(event.getDispatcher());
    }
}
