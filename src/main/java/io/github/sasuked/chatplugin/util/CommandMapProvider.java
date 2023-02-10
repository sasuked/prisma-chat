package io.github.sasuked.chatplugin.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class CommandMapProvider {


    private static CommandMap COMMAND_MAP;

    public static CommandMap getCommandMap() {
        if (COMMAND_MAP != null) {
            return COMMAND_MAP;
        }

        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);

            return (COMMAND_MAP = (CommandMap) commandMap.get(Bukkit.getServer()));
        } catch (Exception e) {
            throw new IllegalStateException("Could not get command map", e);
        }
    }
}
