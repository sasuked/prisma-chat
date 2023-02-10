package io.github.sasuked.chatplugin.channel;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.component.CustomComponent;
import io.github.sasuked.chatplugin.event.ChatChannelMessageEvent;
import io.github.sasuked.chatplugin.util.ComponentUtil;
import io.github.sasuked.chatplugin.util.pattern.ColorHelper;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ChatChannelManager extends ConcurrentHashMap<String, ChatChannel> {

    private static final String CUSTOM_COMPONENT_FORMAT = "@%s@";

    private static final Component NO_PERMISSION = ComponentUtil.text("&cYou do not have permission to use this channel!");

    private final ChatPlugin plugin;

    public ChatChannelManager(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadFromConfig() {
        ConfigurationSection channels = plugin.getConfig().getConfigurationSection("channels");
        if (channels == null) return;

        channels.getKeys(false)
          .stream()
          .map(channels::getConfigurationSection)
          .filter(Objects::nonNull)
          .map(ChatChannel::fromSection)
          .forEach(channel -> this.put(channel.id(), channel));
    }

    public ChatChannel getChannelFromCommand(String command) {
        return this.values()
          .stream()
          .filter(channel -> channel.containsCommand(command))
          .findFirst()
          .orElse(null);
    }

    @Nullable
    public ChatChannel getDefaultChannel() {
        return this.values()
          .stream()
          .filter(ChatChannel::defaultChat)
          .findFirst()
          .orElse(null);
    }

    public void handlePlayerMessage(
      @NotNull Player player,
      @NotNull ChatChannel chatChannel,
      @NotNull String message
    ) {
        if (!chatChannel.isPlayerPermitted(player)) {
            plugin.getAdventure().player(player).sendMessage(NO_PERMISSION);
            return;
        }

        if (!player.hasPermission("prismachat.color")) {
            message = ColorHelper.stripColorFormatting(message);
        }


        var event = new ChatChannelMessageEvent(chatChannel, player, message);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        var format = chatChannel.chatFormat();
        Component formattedMessage = ComponentUtil.text(PlaceholderAPI.setPlaceholders(player, format));

        for (CustomComponent component : plugin.getCustomComponentRegistry().getCustomComponentMap().values()) {
            var placeholderIdentifier = String.format(CUSTOM_COMPONENT_FORMAT, component.id());
            if (!format.contains(placeholderIdentifier)) {
                continue;
            }

            formattedMessage = formattedMessage.replaceText(ComponentUtil.replaceComponent(
              placeholderIdentifier,
              component.generateComponent(text -> applyGlobalPlaceholdersOnString(
                player,
                text,
                event.getMessage(),
                chatChannel.displayName()
              ))));
        }


        BukkitAudiences bukkitAudiences = plugin.getAdventure();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (chatChannel.canReceiveMessage(player, onlinePlayer)) {
                Audience audience = bukkitAudiences.player(onlinePlayer);
                audience.sendMessage(formattedMessage
                  .replaceText(ComponentUtil.replace("%player-name%", player.getName()))
                  .replaceText(ComponentUtil.replace("%message%", event.getMessage()))
                  .replaceText(ComponentUtil.replace("%channel%", chatChannel.displayName())
                  ));
            }
        }
    }

    private String applyGlobalPlaceholdersOnString(Player player, String string, String message, String channel) {
        return PlaceholderAPI.setPlaceholders(player, string
          .replace("%player-name%", player.getName())
          .replace("%message%", message)
          .replace("%channel%", channel)
        );
    }
}
