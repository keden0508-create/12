package com.example.armorhud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ArmorHud {
    private static final EquipmentSlot[] SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS,
            EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND
    };
    private static final int ROW_H = 20;
    private static final int X = 6;

    private record Row(ItemStack stack, String shortText, String detailText, int color) {}

    private ArmorHud() {}

    public static void extract(GuiGraphicsExtractor g, DeltaTracker dt) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || ArmorHudClient.hudHidden) return;

        List<Row> rows = new ArrayList<>(6);
        for (EquipmentSlot slot : SLOTS) {
            ItemStack s = mc.player.getItemBySlot(slot);
            if (s.isEmpty()) continue;

            String name = s.getHoverName().getString();
            if (s.isDamageableItem()) {
                int max = s.getMaxDamage();
                int left = max - s.getDamageValue();
                int pct = max > 0 ? left * 100 / max : 0;
                rows.add(new Row(s, pct + "%",
                        name + "  " + left + "/" + max + " (" + pct + "%)",
                        0xFF000000 | s.getBarColor()));
            } else {
                rows.add(new Row(s, "", name, 0xFFFFFFFF));
            }
        }
        if (rows.isEmpty()) return;

        int screenH = mc.getWindow().getGuiScaledHeight();
        int y0 = screenH / 2 - rows.size() * ROW_H / 2;

        if (ArmorHudClient.detailVisible) {
            int maxW = 0;
            for (Row r : rows) maxW = Math.max(maxW, mc.font.width(r.detailText()));
            g.fill(X - 3, y0 - 3, X + 22 + maxW + 4,
                    y0 + rows.size() * ROW_H + 1, 0x90000000);
        }

        int y = y0;
        for (Row r : rows) {
            g.item(r.stack(), X, y);
            g.itemDecorations(mc.font, r.stack(), X, y);
            String text = ArmorHudClient.detailVisible ? r.detailText() : r.shortText();
            if (!text.isEmpty()) {
                g.text(mc.font, text, X + 22, y + 4, r.color(), true);
            }
            y += ROW_H;
        }
    }
}
