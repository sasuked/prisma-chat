# My Spigot Chat Plugin

This is a Spigot chat plugin for Minecraft version 1.17.1 and above, written in Java 17.

## Features

- Multi configurable chat channels
- Configurable chat formats (message, prefix, suffix, etc..)
- Configurable custom components (with hover and click events)
- Placeholder API support
- Kyori Adventure support
- Tell and Reply commands
- RGB Color support
- Multi-language support

## Configuration

The plugin comes with a `config.yml` file that allows you to configure the chat channels, custom components, and more.
Make sure to edit this file to your liking before starting your server.

You can also configure all command message in the messages file, you can configure
with the language you want changing the field `language` in the config file (en_US by default).

## Commands
- `/prismachat reload` - reloads the plugin configuration.
- `/tell <player> <message>` - Sends a private message to a player
- `/reply <message>` - Replies to the last private message received
- `/<channel> <mesage>` - Sends a message to a channel
- `/clearchat [-a]` - Clears the chat for the player or all players (`-a` if you want to clear it anonymously)

## Placeholder API
This plugin supports the Placeholder API, which allows you to use placeholders in your chat messages. For more information on how to use the Placeholder API, please visit [the Placeholder API wiki](https://github.com/PlaceholderAPI/PlaceholderAPI/wiki).

## Kyori Adventure
This plugin supports the Kyori Adventure library, which allows you to use components in your chat messages. For more information on how to use the Kyori Adventure library, please visit 
[the Kyori Adventure documentation](https://docs.adventure.kyori.net/)

## Issues and Contributions
If you encounter any issues with the plugin or would like to contribute, please see the [Contributing](https://github.com/sasuked/prisma-chat/blob/master/CONTRIBUTING.md) file and open an issue or pull request. 

## License
This plugin is licensed under the MIT License. Please see the `LICENSE` file for more information.
