package com.example.armorhud;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class ArmorHudClient implements ClientModInitializer {
    public static final String MOD_ID = "armorhud";

    /** 기본 Tab: HUD 표시 + 상세정보 열기 */
    public static KeyMapping detailKey;
    /** 기본 V: HUD 전체 숨김 */
    public static KeyMapping hideKey;

    public static boolean detailVisible = false;
    public static boolean hudHidden = false;

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(id("main"));

        detailKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.armorhud.detail", InputConstants.Type.KEYBOARD,
                InputConstants.KEY_TAB, category));

        hideKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.armorhud.hide", InputConstants.Type.KEYBOARD,
                InputConstants.KEY_V, category));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (detailKey.consumeClick()) {
                hudHidden = false;
                detailVisible = true;
            }
            while (hideKey.consumeClick()) {
                hudHidden = true;
                detailVisible = false;
            }
        });

        HudElementRegistry.attachElementAfter(
                VanillaHudElements.HOTBAR, id("durability"), ArmorHud::extract);
    }
}
