package io.github.sasuked.chatplugin.formatter;

import io.github.sasuked.chatplugin.message.ChatMessage;
import net.kyori.adventure.text.Component;

public interface MessageFormatter {
    Component formatMessage(ChatMessage message);
}
