package io.github.sasuked.chatplugin.message;

import io.github.sasuked.chatplugin.formatter.MessageFormatter;
import io.github.sasuked.chatplugin.util.sound.ConfigurableSound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class AbstractChatMessage implements ChatMessage {

    private final CommandSender sender;
    private String content;
    private ConfigurableSound sound;
    private Collection<? extends Player> receivers;
    private final MessageFormatter messageFormatter;

    public AbstractChatMessage(
      @NotNull CommandSender sender,
      @NotNull String content,
      @NotNull ConfigurableSound sound,
      @NotNull Collection<? extends Player> receivers,
      @NotNull MessageFormatter messageFormatter
    ) {
        this.sender = sender;
        this.content = content;
        this.sound = sound;
        this.receivers = receivers;
        this.messageFormatter = messageFormatter;
    }

    @NotNull
    @Override
    public CommandSender getSender() {
        return sender;
    }

    @NotNull
    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public ConfigurableSound getSound() {
        return sound;
    }

    @Override
    public void setSound(ConfigurableSound sound) {
        this.sound = sound;
    }

    @NotNull
    @Override
    public Collection<? extends Player> getReceivers() {
        return receivers;
    }

    @Override
    public void setReceivers(Collection<? extends Player> receivers) {
        this.receivers = receivers;
    }

    @NotNull
    @Override
    public MessageFormatter getMessageFormatter() {
        return messageFormatter;
    }
}
