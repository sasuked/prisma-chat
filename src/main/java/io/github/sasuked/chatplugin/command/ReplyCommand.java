package io.github.sasuked.chatplugin.command;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.lang.LanguageKeys;
import io.github.sasuked.chatplugin.message.WhisperMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReplyCommand extends Command {

    private final ChatPlugin plugin;

    public ReplyCommand(ChatPlugin plugin) {
        super("reply");
        this.plugin = plugin;
    }


    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        if (!player.hasPermission("prismachat.reply")) {
            plugin.getLanguageManager().sendLocalizedMessage(player, LanguageKeys.NO_PERMISSION);
            return false;
        }

        if (args.length == 0) {
            plugin.getLanguageManager()
              .sendLocalizedMessage(player, LanguageKeys.COMMAND_USAGE, "%command-usage%", "r <message>");
            return false;
        }

        var lastMessageSender = plugin.getWhisperManager().getLastMessageSender(player);
        if (lastMessageSender == null) {
            plugin.getLanguageManager()
              .sendLocalizedMessage(player, LanguageKeys.NO_RECENT_MESSAGES);
            return false;
        }

        var content = String.join(" ", args);
        var message = new WhisperMessage(player, content, plugin.getWhisperManager().getWhisperSound(), lastMessageSender);

        if (plugin.getChatMessageController().sendChatMessage(message)) {
            plugin.getWhisperManager().setLastMessageSender(lastMessageSender, player);
        }

        return false;
    }
}
