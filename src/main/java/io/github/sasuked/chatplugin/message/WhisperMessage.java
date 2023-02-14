package io.github.sasuked.chatplugin.message;

import io.github.sasuked.chatplugin.formatter.WhisperMessageFormatter;
import io.github.sasuked.chatplugin.util.sound.ConfigurableSound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class WhisperMessage extends AbstractChatMessage {
    public WhisperMessage(
      @NotNull CommandSender sender,
      @NotNull String content,
      @NotNull ConfigurableSound sound,
      @NotNull Player receiver
    ) {
        super(sender, content, sound, Collections.singleton(receiver), new WhisperMessageFormatter());
    }
}
