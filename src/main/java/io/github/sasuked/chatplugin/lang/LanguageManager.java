package io.github.sasuked.chatplugin.lang;

import io.github.sasuked.chatplugin.util.ComponentUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.function.UnaryOperator;

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
    public static LanguageManager resolve(@NotNull Plugin plugin, @NotNull String localeName) {
        if (localeName.isEmpty()) {
            throw new IllegalArgumentException("The locale name cannot be empty.");
        }

        var file = new File(plugin.getDataFolder(), String.format(FILE_NAME_FORMAT, localeName));
        if (!file.exists()) {
            throw new IllegalArgumentException("The language file does not exist.");
        }

        return new LanguageManager(YamlConfiguration.loadConfiguration(file));
    }


    private final FileConfiguration configuration;

    public LanguageManager(FileConfiguration configuration) {
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
}

