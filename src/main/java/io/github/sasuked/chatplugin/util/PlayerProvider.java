package io.github.sasuked.chatplugin.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerProvider {

    @Nullable
    public static Player getPlayer(@NotNull String playerName) {
        return Bukkit.getOnlinePlayers()
          .stream()
          .filter(player -> player.getName().equalsIgnoreCase(playerName))
          .findFirst()
          .orElse(null);
    }
}
