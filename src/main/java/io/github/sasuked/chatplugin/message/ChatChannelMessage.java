package io.github.sasuked.chatplugin.message;

import io.github.sasuked.chatplugin.channel.ChatChannel;
import io.github.sasuked.chatplugin.formatter.ChatChannelMessageFormatter;
import io.github.sasuked.chatplugin.util.sound.ConfigurableSound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ChatChannelMessage extends AbstractChatMessage {

    private final ChatChannel chatChannel;

    public ChatChannelMessage(
      @NotNull CommandSender sender,
      @NotNull String content,
      @NotNull ConfigurableSound sound,
      @NotNull Collection<? extends Player> receivers,
      @NotNull ChatChannel chatChannel
    ) {
        super(sender, content, sound, receivers, new ChatChannelMessageFormatter());
        this.chatChannel = chatChannel;
    }

    /**
     * Get the chat channel of the message
     *
     * @return the chat channel of the message
     */
    @NotNull
    public ChatChannel getChatChannel() {
        return chatChannel;
    }
}
