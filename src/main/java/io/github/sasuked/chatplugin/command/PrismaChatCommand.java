package io.github.sasuked.chatplugin.command;

import io.github.sasuked.chatplugin.ChatPlugin;
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
        var audience = plugin.getAdventure().sender(sender);
        if (!sender.hasPermission("prismachat.admin")) {
            audience.sendMessage(plugin.getLanguageManager().getMessageComponent("no-permission"));
            return false;
        }

        plugin.reloadConfig();
        plugin.setupComponents();
        plugin.setupChatChannels();

        audience.sendMessage(plugin.getLanguageManager().getMessageComponent("reload-message"));
        return false;
    }
}
