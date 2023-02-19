package io.github.sasuked.chatplugin.command;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.lang.LanguageKeys;
import io.github.sasuked.chatplugin.message.ChatChannelMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static java.util.Arrays.asList;

public class ChatMessageCommand extends Command {

    private final ChatPlugin plugin;

    public ChatMessageCommand(@NotNull ChatPlugin plugin, @NotNull String... commands) {
        super(commands[0]);
        this.plugin = plugin;
        this.setAliases(asList(commands));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            plugin.getLanguageManager()
              .sendLocalizedMessage(sender, LanguageKeys.PLAYER_ONLY_COMMAND);
            return false;
        }


        var channel = plugin.getChatChannelManager().getChannelFromCommand(commandLabel);
        if (channel == null) {
            plugin.getLanguageManager()
              .sendLocalizedMessage(player, LanguageKeys.CHANNEL_NOT_FOUND, "%channel%", commandLabel);
            return false;
        }

        if (!channel.isPlayerPermitted(player)) {
            plugin.getLanguageManager()
              .sendLocalizedMessage(player, LanguageKeys.CHANNEL_NOT_PERMITTED, "%channel%", commandLabel);
            return false;
        }

        if (args.length == 0) {
            plugin.getLanguageManager()
              .sendLocalizedMessage(player, LanguageKeys.COMMAND_USAGE, "%command-usage%", commandLabel + " <message>");
            return false;
        }

        var message = StringUtils.join(args, " ");
        var receivers = Bukkit.getOnlinePlayers()
          .stream()
          .filter(receiver -> channel.canReceiveMessage(player, receiver))
          .toList();

        var chatMessage = new ChatChannelMessage(player, message, channel.messageSound(), receivers, channel);
        plugin.getChatMessageController().sendChatMessage(chatMessage);
        return false;
    }


}
