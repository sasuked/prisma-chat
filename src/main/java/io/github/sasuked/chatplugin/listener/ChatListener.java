package io.github.sasuked.chatplugin.listener;

import io.github.sasuked.chatplugin.ChatPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final ChatPlugin plugin;

    public ChatListener(ChatPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        var defaultChannel = this.plugin.getChatChannelManager().getDefaultChannel();
        if (defaultChannel == null) {
            return;
        }

        plugin.getChatChannelManager().handlePlayerMessage(event.getPlayer(), defaultChannel, event.getMessage());
    }
}
