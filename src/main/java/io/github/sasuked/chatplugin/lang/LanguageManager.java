package io.github.sasuked.chatplugin.lang;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * This class is used to get messages from the language file.
 *
 * @author Sasuke
 * @since 1.0.0
 */
public class LanguageManager {

    /**
     * The file name format of the language file.
     */
    private static final String FILE_NAME_FORMAT = "lang/messages_%s.yml";


    /**
     * Resolve a language manager from the language file.
     *
     * @param plugin     The plugin.
     * @param localeName The locale name.
     * @return The language manager.
     */
    public static LanguageManager resolve(@NotNull ChatPlugin plugin, @NotNull String localeName) {
        if (localeName.isEmpty()) {
            throw new IllegalArgumentException("The locale name cannot be empty.");
        }

        var file = new File(plugin.getDataFolder(), String.format(FILE_NAME_FORMAT, localeName));
        if (!file.exists()) {
            throw new IllegalArgumentException("The language file does not exist.");
        }

        return new LanguageManager(plugin, YamlConfiguration.loadConfiguration(file));
    }


    private final ChatPlugin plugin;
    private final FileConfiguration configuration;

    /**
     * Create a new language manager.
     *
     * @param configuration The configuration to be used.
     */
    LanguageManager(ChatPlugin plugin, FileConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }


    /**
     * Get a message from the language file.
     *
     * @param key The key of the message.
     * @return The message if the message is not found, the key will be returned.
     */
    @NotNull
    public String getMessage(@NotNull String key) {
        return configuration.getString(key, key);
    }

    /**
     * Get a message from the language file.
     *
     * @param key The key of the message if the message is not found, the key will be returned.
     * @return The message.
     */
    @NotNull
    public Component getMessageComponent(@NotNull String key) {
        return ComponentUtil.text(this.getMessage(key));
    }

    /**
     * Send a message to the command sender.
     *
     * @param sender       The command sender.
     * @param key          The key of the message.
     * @param replacements The replacements.
     * @throws IllegalArgumentException If the replacements are not in pairs.
     */
    public void sendLocalizedMessage(@NotNull CommandSender sender, @NotNull String key, String... replacements) {
        var message = this.getMessage(key);

        if (replacements.length > 0) {
            if (replacements.length % 2 != 0) {
                throw new IllegalArgumentException("The replacements must be in pairs.");
            }

            for (int i = 0; i < replacements.length; i += 2) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }

        plugin.getAdventure()
          .sender(sender)
          .sendMessage(ComponentUtil.text(message));
    }

    public void broadcastLocalizedMessage(@NotNull String key, String... replacements) {
        var message = this.getMessage(key);

        if (replacements.length > 0) {
            if (replacements.length % 2 != 0) {
                throw new IllegalArgumentException("The replacements must be in pairs.");
            }

            for (int i = 0; i < replacements.length; i += 2) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }

        plugin.getAdventure()
          .players()
          .sendMessage(ComponentUtil.text(message));
    }

}

