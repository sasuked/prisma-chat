package io.github.sasuked.chatplugin.command;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.channel.ChatChannel;
import io.github.sasuked.chatplugin.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static java.util.Arrays.asList;

public class ChatMessageCommand extends Command {

    private static final Component CHANNEL_NOT_FOUND = ComponentUtil.text("&cChannel not found!");

    private final ChatPlugin plugin;

    public ChatMessageCommand(@NotNull ChatPlugin plugin, @NotNull String... commands) {
        super(commands[0]);
        this.plugin = plugin;
        this.setAliases(asList(commands));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }

        ChatChannel channel = plugin.getChatChannelManager().getChannelFromCommand(commandLabel);
        if (channel == null) {

            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("Usage: /" + commandLabel + " <message>");
            return false;
        }

        String message = StringUtils.join(args, " ", 0, args.length);

        plugin.getChatChannelManager().handlePlayerMessage(player, channel, message);
        return false;
    }
}
