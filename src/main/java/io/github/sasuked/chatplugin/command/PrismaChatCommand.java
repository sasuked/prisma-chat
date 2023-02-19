package io.github.sasuked.chatplugin.command;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.lang.LanguageKeys;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PrismaChatCommand extends Command {

    private final ChatPlugin plugin;

    public PrismaChatCommand(ChatPlugin plugin) {
        super("prismachat");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission("prismachat.admin")) {
            plugin.getLanguageManager().sendLocalizedMessage(sender, LanguageKeys.NO_PERMISSION);
            return false;
        }

        if (args.length == 0) {
            plugin.getLanguageManager()
              .sendLocalizedMessage(sender, LanguageKeys.COMMAND_USAGE, "%command-usage%", "prismachat reload");
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.setupComponents();
            plugin.setupChatChannels();

            plugin.getLanguageManager().sendLocalizedMessage(sender, LanguageKeys.RELOAD_MESSAGE);
        }
        return false;
    }
}
