package io.github.sasuked.chatplugin.event;

import io.github.sasuked.chatplugin.channel.ChatChannel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter

public class ChatChannelMessageEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final ChatChannel chatChannel;
    private final Player player;

    @Setter
    private String message;

    @Setter
    private boolean cancelled;

    public ChatChannelMessageEvent(ChatChannel chatChannel, Player player, String message) {
        super(!Bukkit.isPrimaryThread());
        this.chatChannel = chatChannel;
        this.player = player;
        this.message = message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
