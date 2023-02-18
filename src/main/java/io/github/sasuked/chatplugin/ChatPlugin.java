package io.github.sasuked.chatplugin;

import io.github.sasuked.chatplugin.channel.ChatChannelManager;
import io.github.sasuked.chatplugin.command.ClearChatCommand;
import io.github.sasuked.chatplugin.command.PrismaChatCommand;
import io.github.sasuked.chatplugin.command.ReplyCommand;
import io.github.sasuked.chatplugin.command.TellCommand;
import io.github.sasuked.chatplugin.component.CustomComponentRegistry;
import io.github.sasuked.chatplugin.lang.LanguageManager;
import io.github.sasuked.chatplugin.listener.ChatListener;
import io.github.sasuked.chatplugin.message.ChatMessageController;
import io.github.sasuked.chatplugin.util.CommandMapProvider;
import io.github.sasuked.chatplugin.whisper.WhisperManager;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

@Getter
public final class ChatPlugin extends JavaPlugin {

    private BukkitAudiences adventure;
    private LanguageManager languageManager;
    private ChatChannelManager chatChannelManager;
    private CustomComponentRegistry customComponentRegistry;
    private WhisperManager whisperManager;
    private ChatMessageController chatMessageController;

    @Override
    public void onEnable() {
        adventure = BukkitAudiences.create(this);

        saveDefaultConfig();
        setupLanguage();
        setupChatChannels();
        setupComponents();
        setupWhisperManager();

        chatMessageController = new ChatMessageController(this);


        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);

        CommandMapProvider.getCommandMap().registerAll("prisma-chat", Arrays.asList(
          new PrismaChatCommand(this),
          new TellCommand(this),
          new ReplyCommand(this),
          new ClearChatCommand(this)
        ));
    }

    private void setupWhisperManager() {
        whisperManager = new WhisperManager(this);
        whisperManager.loadFromConfig();
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

    public void setupLanguage() {
        File file = new File(getDataFolder(), "lang/messages_en_US.yml");
        if (!file.exists()) {
            saveResource("lang/messages_en_US.yml", false);
        }

        languageManager = LanguageManager.resolve(this, getConfig().getString("language", "en_US"));
    }
}
