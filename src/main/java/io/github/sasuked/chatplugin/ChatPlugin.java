package io.github.sasuked.chatplugin;

import io.github.sasuked.chatplugin.channel.ChatChannelManager;
import io.github.sasuked.chatplugin.command.PrismaChatCommand;
import io.github.sasuked.chatplugin.component.CustomComponentRegistry;
import io.github.sasuked.chatplugin.listener.ChatListener;
import io.github.sasuked.chatplugin.util.CommandMapProvider;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ChatPlugin extends JavaPlugin {

    private BukkitAudiences adventure;

    private ChatChannelManager chatChannelManager;

    private CustomComponentRegistry customComponentRegistry;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        adventure = BukkitAudiences.create(this);

        setupChatChannels();
        setupComponents();

        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);

        CommandMapProvider.getCommandMap().register("prisma-chat", new PrismaChatCommand(this));
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public void setupChatChannels() {
        chatChannelManager = new ChatChannelManager(this);
        chatChannelManager.loadFromConfig();
    }

    public void setupComponents() {
        customComponentRegistry = new CustomComponentRegistry(this);
        customComponentRegistry.loadComponents();
    }

    public BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }


}
