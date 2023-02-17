package io.github.sasuked.chatplugin.command;

import io.github.sasuked.chatplugin.ChatPlugin;
import io.github.sasuked.chatplugin.message.WhisperMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static io.github.sasuked.chatplugin.util.ComponentUtil.replace;

public class TellCommand extends Command {

    private final ChatPlugin plugin;

    public TellCommand(ChatPlugin plugin) {
        super("tell");
        setAliases(List.of("msg", "w", "whisper", "m", "t", "message"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        var audience = plugin.getAdventure().sender(sender);
        if (!sender.hasPermission("prismachat.tell")) {
            audience.sendMessage(plugin.getLanguageManager().getMessageComponent("no-permission"));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("§c/tell <player> <message>");
            return false;
        }


        var receiver = Bukkit.getPlayer(args[0]);
        if (receiver == null) {
            audience.sendMessage(plugin.getLanguageManager()
              .getMessageComponent("player-not-found")
              .replaceText(replace("%player-name%", args[0]))
            );
            return false;
        }


        var content = StringUtils.join(args, " ", 1, args.length);
        var message = new WhisperMessage(sender, content, plugin.getWhisperManager().getWhisperSound(), receiver);

        if (plugin.getChatMessageController().sendChatMessage(message) && sender instanceof Player player) {
            plugin.getWhisperManager().setLastMessageSender(receiver, player);
        }

        return false;
    }


}
