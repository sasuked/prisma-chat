package io.github.sasuked.chatplugin.command;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.lang.LanguageKeys;
import io.github.sasuked.chatplugin.lang.LanguageManager;
import io.github.sasuked.chatplugin.util.sound.ConfigurableSound;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClearChatCommand extends Command {

    private static final String CLEAR_CHAT_LINES_PATH = "clear-chat.lines";
    private static final String CLEAR_CHAT_SOUND_PATH = "clear-chat.sound";

    private final ChatPlugin plugin;
    private final LanguageManager languageManager;

    public ClearChatCommand(@NotNull ChatPlugin plugin) {
        super("clearchat");
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission("prismachat.clearchat")) {
            plugin.getLanguageManager().sendLocalizedMessage(sender, LanguageKeys.NO_PERMISSION);
            return false;
        }

        // TODO: Add a permission to clear chat anonymously
        var anonymous = args.length > 0 && args[0].equalsIgnoreCase("-a");
        if (anonymous && !sender.hasPermission("prismachat.clearchat.anonymous")) {
            plugin.getLanguageManager().sendLocalizedMessage(sender, LanguageKeys.NO_PERMISSION);
            return false;
        }

        var clearChatLines = plugin.getConfig().getInt(CLEAR_CHAT_LINES_PATH, 100);
        for (int i = 0; i < clearChatLines; i++) {
            String empty = StringUtils.EMPTY;
            Bukkit.broadcastMessage(empty);
        }

        plugin.getLanguageManager()
          .broadcastLocalizedMessage(anonymous ? LanguageKeys.CLEAR_CHAT_ANONYMOUS : LanguageKeys.CLEAR_CHAT);

        var configurableSound = ConfigurableSound.fromSection(plugin, CLEAR_CHAT_SOUND_PATH);
        configurableSound.play();
        return false;
    }
}
