package io.github.sasuked.chatplugin.formatter;

import io.github.sasuked.chatplugin.component.CustomComponent;
import io.github.sasuked.chatplugin.message.ChatMessage;
import io.github.sasuked.chatplugin.message.WhisperMessage;
import io.github.sasuked.chatplugin.util.ComponentUtil;
import net.kyori.adventure.text.Component;

import static io.github.sasuked.chatplugin.util.ComponentUtil.replace;
import static io.github.sasuked.chatplugin.util.ComponentUtil.replaceComponent;

public class WhisperMessageFormatter extends AbstractMessageFormatter {
    @Override
    public Component formatMessage(ChatMessage message) {
        if (!(message instanceof WhisperMessage)) {
            throw new IllegalArgumentException("Message is not a WhisperMessage");
        }

        var sender = message.getSender();
        var format = plugin.getWhisperManager().getWhisperFormat();

        var formattedMessage = ComponentUtil.text(format
          .replace("%player-name%", message.getSender().getName())
          .replace("%receiver%", message.getReceivers().iterator().next().getName())
          .replace("%message%", message.getContent()));

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


        return formattedMessage.replaceText(replace("%player-name%", sender.getName()))
          .replaceText(replace("%message%", message.getContent()));
    }
}
