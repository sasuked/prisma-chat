package io.github.sasuked.chatplugin.message;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.event.ChatMessageEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatPlugin plugin;

    public boolean sendChatMessage(ChatMessage chatMessage) {
        var event = new ChatMessageEvent(chatMessage);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        var message = chatMessage.getMessageFormatter().formatMessage(event.getChatMessage());
        // TODO add permission to send colored messages
        // TODO add filters (anti-spam, anti-advertising, etc)

        var sound = event.getChatMessage().getSound();

        var bukkitAudiences = plugin.getAdventure();
        for (Player player : chatMessage.getReceivers()) {
            Audience receiver = bukkitAudiences.player(player);
            receiver.sendMessage(message);

            if (sound != null) {
                sound.play(player);
            }
        }

        return true;
    }
}
