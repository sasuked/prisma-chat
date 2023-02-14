package io.github.sasuked.chatplugin.listener;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.message.ChatChannelMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final ChatPlugin plugin;

    public ChatListener(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST) // use HIGHEST priority to support punishment plugins.
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        var defaultChannel = this.plugin.getChatChannelManager().getDefaultChannel();
        if (defaultChannel == null) {
            return;
        }

        var sender = event.getPlayer();
        var receivers = Bukkit.getOnlinePlayers()
          .stream()
          .filter(player -> defaultChannel.canReceiveMessage(sender, player))
          .toList();

        plugin.getChatMessageController().sendChatMessage(new ChatChannelMessage(
          sender,
          event.getMessage(),
          defaultChannel.messageSound(),
          receivers,
          defaultChannel
        ));
    }
}
