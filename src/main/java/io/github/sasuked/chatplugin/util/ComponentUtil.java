package io.github.sasuked.chatplugin.util;

import io.github.sasuked.chatplugin.util.pattern.ColorHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class ComponentUtil {

    public static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder()
      .hexColors()
      .character('§')
      .build();

    public static TextComponent text(@NotNull String message) {
        return LEGACY_COMPONENT_SERIALIZER.deserialize(ColorHelper.process(message));
    }


    @NotNull
    public static TextReplacementConfig replace(@NotNull String a, @NotNull String b) {
        return replaceComponent(a, text(b));
    }

    @NotNull
    public static TextReplacementConfig replaceComponent(@NotNull String a, Component b) {
        return TextReplacementConfig.builder().match(a).replacement(b).build();
    }

}