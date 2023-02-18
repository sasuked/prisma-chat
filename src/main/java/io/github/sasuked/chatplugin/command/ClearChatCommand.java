package io.github.sasuked.chatplugin.command;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.lang.LanguageManager;
import io.github.sasuked.chatplugin.util.sound.ConfigurableSound;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

public class ClearChatCommand extends Command {

    private static final String CLEAR_CHAT_LINES_KEY = "clear-chat.lines";
    private static final String CLEAR_CHAT_SOUND_KEY = "clear-chat.sound";

    private static final String CLEAR_CHAT_ANONYMOUS_KEY = "clear-chat-anonymous";
    private static final String CLEAR_CHAT_KEY = "clear-chat";

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
            plugin.getAdventure().sender(sender)
              .sendMessage(languageManager.getMessageComponent("no-permission"));
            return false;
        }

        var anonymous = args.length > 0 && args[0].equalsIgnoreCase("-a");
        var clearChatLines = plugin.getConfig().getInt(CLEAR_CHAT_LINES_KEY, 100);

        IntStream.range(0, clearChatLines)
          .mapToObj(i -> StringUtils.EMPTY)
          .forEach(Bukkit::broadcastMessage);

        plugin.getAdventure()
          .players()
          .sendMessage(languageManager.getMessageComponent(anonymous ? CLEAR_CHAT_ANONYMOUS_KEY : CLEAR_CHAT_KEY));

        var configurableSound = ConfigurableSound.fromSection(plugin , CLEAR_CHAT_SOUND_KEY);
        configurableSound.play();
        return false;
    }
}
