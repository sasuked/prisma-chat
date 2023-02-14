package io.github.sasuked.chatplugin.formatter;

import io.github.sasuked.chatplugin.component.CustomComponent;
import io.github.sasuked.chatplugin.message.ChatChannelMessage;
import io.github.sasuked.chatplugin.message.ChatMessage;
import io.github.sasuked.chatplugin.util.ComponentUtil;
import net.kyori.adventure.text.Component;

import static io.github.sasuked.chatplugin.util.ComponentUtil.replace;
import static io.github.sasuked.chatplugin.util.ComponentUtil.replaceComponent;

public class ChatChannelMessageFormatter extends AbstractMessageFormatter {
    @Override
    public Component formatMessage(ChatMessage message) {
        if (!(message instanceof ChatChannelMessage channelMessage)) {
            throw new IllegalArgumentException("Message is not a ChatChannelMessage");
        }

        var channel = channelMessage.getChatChannel();
        var sender = message.getSender();
        var content = message.getContent();

        var format = channel.chatFormat();

        Component formattedMessage = ComponentUtil.text(format);

        for (CustomComponent component : plugin.getCustomComponentRegistry().getComponents()) {
            String placeholder = String.format(GLOBAL_COMPONENT_FORMAT, component.id());
            if (!format.contains(placeholder)) {
                continue;
            }

            formattedMessage = formattedMessage.replaceText(replaceComponent(
              placeholder,
              component.generateComponent(text -> text.replace("%player-name%", sender.getName()))
            ));
        }
        return formattedMessage
          .replaceText(replace("%player-name%", sender.getName()))
          .replaceText(replace("%message%", content));
    }
}
