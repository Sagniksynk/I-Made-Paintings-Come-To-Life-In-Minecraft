package com.shiraken.template_mod.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY_MOD = "key.category.template_mod.general";
    public static final String KEY_ACTIVATE_PAINTING = "key.template_mod.activate_painting";

    public static final KeyMapping ACTIVATE_PAINTING_KEY = new KeyMapping(
            KEY_ACTIVATE_PAINTING,
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT, // Requires ALT modifier
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_1, // The '1' key
            KEY_CATEGORY_MOD
    );

    public static final String KEY_ACTIVATE_PAINTING_ALT = "key.template_mod.activate_painting_alt";
    public static final KeyMapping ACTIVATE_PAINTING_ALT_KEY = new KeyMapping(
            KEY_ACTIVATE_PAINTING_ALT,
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT, // Requires ALT modifier
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_2, // The '2' key
            KEY_CATEGORY_MOD
    );

    public static final String KEY_ACTIVATE_ROOM = "key.template_mod.activate_room";
    public static final KeyMapping ACTIVATE_ROOM_KEY = new KeyMapping(
            KEY_ACTIVATE_ROOM,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_PERIOD, // The '.' key
            KEY_CATEGORY_MOD
    );
}
