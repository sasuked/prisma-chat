package io.github.sasuked.chatplugin.message;

import io.github.sasuked.chatplugin.formatter.MessageFormatter;
import io.github.sasuked.chatplugin.util.sound.ConfigurableSound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ChatMessage {

    /**
     * Get the sender of the message
     *
     * @return the sender of the message
     */
    @NotNull
    CommandSender getSender();

    /**
     * Get the receivers of the message
     *
     * @return the receivers of the message
     */
    @NotNull
    Collection<? extends Player> getReceivers();

    /**
     * Set the receivers of the message
     *
     * @param receivers the receivers of the message
     */
    void setReceivers(@NotNull Collection<? extends Player> receivers);

    /**
     * Get the content of the message
     *
     * @return the content of the message
     */
    @NotNull
    String getContent();

    /**
     * Set the content of the message
     *
     * @param content the content of the message
     */
    void setContent(@NotNull String content);

    /**
     * Get the sound of the message
     *
     * @return the sound of the message if it exists, null otherwise
     */
    @Nullable
    ConfigurableSound getSound();

    /**
     * Set the sound of the message
     *
     * @param sound the sound of the message
     */
    void setSound(@Nullable ConfigurableSound sound);

    /**
     * Get the message formatter of the message
     *
     * @return the message formatter of the message
     */
    @NotNull
    MessageFormatter getMessageFormatter();
}
