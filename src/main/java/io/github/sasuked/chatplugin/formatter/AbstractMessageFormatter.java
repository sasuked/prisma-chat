package io.github.sasuked.chatplugin.formatter;

import io.github.sasuked.chatplugin.ChatPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractMessageFormatter implements MessageFormatter {

    protected static final String GLOBAL_COMPONENT_FORMAT = "@%s@";

    // TODO find a way to get the plugin instance without using this method.
    protected final ChatPlugin plugin = JavaPlugin.getPlugin(ChatPlugin.class);
}
