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
    private static final Component USAGE_MESSAGE = ComponentUtil.text("&cUsage: /<channel> <message>");

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
            plugin.getAdventure().player(player).sendMessage(CHANNEL_NOT_FOUND);
            return false;
        }

        if (args.length == 0) {
            plugin.getAdventure().player(player).sendMessage(USAGE_MESSAGE
              .replaceText(ComponentUtil.replace("<channel>", commandLabel))
            );
            return false;
        }

        plugin.getChatChannelManager().handlePlayerMessage(player, channel, StringUtils.join(args, " "));
        return false;
    }
}
