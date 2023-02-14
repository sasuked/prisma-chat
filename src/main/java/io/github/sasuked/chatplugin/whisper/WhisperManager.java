package io.github.sasuked.chatplugin.whisper;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.util.sound.ConfigurableSound;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class WhisperManager {

    private final ChatPlugin plugin;

    // This is the format that will be used when a player sends a private message
    private String whisperFormat;

    // This is the sound that will be played when a player receives a private message
    private ConfigurableSound whisperSound;

    // This is a cache of the last player that sent a private message to a player
    private final Map<UUID, UUID> lastMessageCache = new HashMap<>();

    public void loadFromConfig() {
        this.whisperFormat = plugin.getConfig().getString("whisper.whisper-format");
        this.whisperSound = ConfigurableSound.fromSection(plugin.getConfig().getConfigurationSection("whisper.whisper-sound"));
    }
    public void setLastMessageSender(Player receiver, Player sender) {
        lastMessageCache.put(receiver.getUniqueId(), sender.getUniqueId());
    }

    public Player getLastMessageSender(Player receiver) {
        UUID uuid = lastMessageCache.get(receiver.getUniqueId());
        if (uuid == null) {
            return null;
        }

        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            lastMessageCache.remove(receiver.getUniqueId());
        }

        return player;
    }
}
