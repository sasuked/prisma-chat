package io.github.sasuked.chatplugin.util.sound;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ConfigurableSound(boolean enabled, Sound sound, float volume, float pitch) {

    private static final ConfigurableSound DEFAULT = new ConfigurableSound(false, null, 0, 0);

    public static ConfigurableSound fromSection(@Nullable ConfigurationSection section) {
        if (section == null) {
            return DEFAULT;
        }

        boolean enabled = section.getBoolean("enabled");
        if (!enabled) {
            return DEFAULT;
        }

        Sound sound = Sound.valueOf(section.getString("sound"));
        float volume = (float) section.getDouble("volume");
        float pitch = (float) section.getDouble("pitch");
        return new ConfigurableSound(true, sound, volume, pitch);
    }

    public void play(@NotNull Player player) {
        if (sound == null || !enabled) {
            return;
        }

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public void play(@NotNull Location location) {
        if (sound == null || !enabled) {
            return;
        }

        World world = location.getWorld();
        if (world != null) {
            world.playSound(location, sound, volume, pitch);
        }
    }
}
