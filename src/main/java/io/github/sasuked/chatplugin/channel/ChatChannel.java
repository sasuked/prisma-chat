package io.github.sasuked.chatplugin.channel;

import io.github.sasuked.chatplugin.util.sound.ConfigurableSound;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public record ChatChannel(
  String id,
  String displayName,
  String permission,
  String chatFormat,
  boolean defaultChat,
  boolean globalChat,
  double range,
  ConfigurableSound messageSound,
  String... commands
) {


    public static ChatChannel fromSection(@NotNull ConfigurationSection section) {
        var id = section.getName();
        var displayName = section.getString("display-name");
        var permission = section.getString("permission");
        var chatFormat = section.getString("format");
        var defaultChat = section.getBoolean("default-chat");
        var globalChat = section.getBoolean("global-chat");
        var range = section.getDouble("range");
        var messageSound = ConfigurableSound.fromSection(section.getConfigurationSection("message-sound"));
        var commands = section.getStringList("commands").toArray(new String[0]);

        return new ChatChannel(
          id,
          displayName,
          permission,
          chatFormat,
          defaultChat,
          globalChat,
          range,
          messageSound,
          commands
        );
    }

    public boolean canReceiveMessage(Player sender, Player receiver) {
        return isPlayerPermitted(receiver) && isInRange(receiver, sender.getLocation());
    }

    private boolean isInRange(Player player, Location location) {
        if (!Objects.requireNonNull(location.getWorld()).equals(player.getWorld())) {
            return false;
        }

        double distance = location.distance(player.getLocation());
        return range > 0 && distance <= range;
    }

    public boolean isPlayerPermitted(Player player) {
        return player.hasPermission(permission);
    }

    public boolean containsCommand(String command) {
        return Arrays.stream(commands).anyMatch(cmd -> cmd.equalsIgnoreCase(command));
    }
}
