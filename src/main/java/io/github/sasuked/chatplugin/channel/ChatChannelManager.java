package io.github.sasuked.chatplugin.channel;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.command.ChatMessageCommand;
import io.github.sasuked.chatplugin.util.CommandMapProvider;
import io.github.sasuked.chatplugin.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatChannelManager {


    private static final Component NO_PERMISSION = ComponentUtil.text("&cYou do not have permission to use this channel!");

    private final ChatPlugin plugin;
    private final Map<String, ChatChannel> chatChannelMap = new HashMap<>();

    public ChatChannelManager(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadFromConfig() {
        chatChannelMap.clear();

        ConfigurationSection channels = plugin.getConfig().getConfigurationSection("channels");
        if (channels == null) return;

        long count = channels.getKeys(false)
          .stream()
          .map(channels::getConfigurationSection)
          .filter(Objects::nonNull)
          .map(ChatChannel::fromSection)
          .peek(this::registerChatChannel)
          .count();

        Bukkit.getConsoleSender().sendMessage("[Prisma Chat] Loaded " + count + " chat channels");
    }

    public void registerChatChannel(ChatChannel chatChannel) {
        chatChannelMap.put(chatChannel.id(), chatChannel);

        CommandMapProvider.getCommandMap().register(
          "prisma-chat",
          new ChatMessageCommand(plugin, chatChannel.commands())
        );
    }

    public ChatChannel getChannelFromCommand(String command) {
        return chatChannelMap.values()
          .stream()
          .filter(channel -> channel.containsCommand(command))
          .findFirst()
          .orElse(null);
    }

    @Nullable
    public ChatChannel getDefaultChannel() {
        return chatChannelMap.values()
          .stream()
          .filter(ChatChannel::defaultChat)
          .findFirst()
          .orElse(null);
    }
}
