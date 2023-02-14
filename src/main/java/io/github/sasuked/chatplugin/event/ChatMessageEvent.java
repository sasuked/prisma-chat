package io.github.sasuked.chatplugin.event;

import io.github.sasuked.chatplugin.message.ChatMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter

public class ChatMessageEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final ChatMessage chatMessage;

    @Setter
    private boolean cancelled;

    public ChatMessageEvent(ChatMessage chatMessage) {
        super(!Bukkit.isPrimaryThread());
        this.chatMessage = chatMessage;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
