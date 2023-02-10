package io.github.sasuked.chatplugin.component;

import io.github.sasuked.chatplugin.ChatPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class CustomComponentRegistry {

    private final ChatPlugin plugin;

    @Getter
    private final Map<String, CustomComponent> customComponentMap = new ConcurrentHashMap<>();

    public void registerComponent(CustomComponent component) {
        customComponentMap.put(component.id(), component);
    }

    public CustomComponent getComponent(String id) {
        return customComponentMap.get(id);
    }

    public void loadComponents() {
        ConfigurationSection root = plugin.getConfig().getConfigurationSection("custom-components");
        if (root == null) return;

        long count = root.getKeys(false)
          .stream()
          .map(root::getConfigurationSection)
          .filter(Objects::nonNull)
          .map(CustomComponent::fromSection)
          .peek(this::registerComponent)
          .count();

        Bukkit.getConsoleSender().sendMessage("[Prisma Chat] Loaded " + count + " custom components");
    }


    public Collection<CustomComponent> getComponents() {
        return customComponentMap.values();
    }
}
