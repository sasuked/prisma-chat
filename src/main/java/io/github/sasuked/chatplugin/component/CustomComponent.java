package io.github.sasuked.chatplugin.component;

import io.github.sasuked.chatplugin.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent.Action;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;

import static net.kyori.adventure.text.event.ClickEvent.clickEvent;

public record CustomComponent(
  @NotNull String id,
  @NotNull String text,
  @NotNull List<String> hoverText,
  @Nullable Action clickEventAction,
  @Nullable String clickEventValue
) {

    public static CustomComponent fromSection(ConfigurationSection section) {
        if (section == null) return null;

        var id = section.getName();
        var text = section.getString("text");
        if (text == null || text.isEmpty()) {
            text = StringUtils.EMPTY;
        }

        var hoverText = section.getStringList("hover-text");

        ConfigurationSection clickSection = section.getConfigurationSection("click-event");

        Action clickEventAction = null;
        String clickEventValue = null;

        if (clickSection != null) {
            clickEventAction = actionFromName(clickSection.getString("action"));
            clickEventValue = clickSection.getString("value");
        }

        return new CustomComponent(id, text, hoverText, clickEventAction, clickEventValue);
    }


    private static Action actionFromName(String name) {
        if (name == null) {
            return null;
        }

        for (Action value : Action.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public Component generateComponent() {
        return generateComponent(UnaryOperator.identity());
    }

    public Component generateComponent(UnaryOperator<String> transformer) {
        var component = ComponentUtil.text(text);

        if (clickEventAction != null && clickEventValue != null) {
            component = component.clickEvent(clickEvent(clickEventAction, transformer.apply(clickEventValue)));
        }

        Component hoverComponent = Component.empty();

        if (!hoverText.isEmpty()) {
            Iterator<String> textIterator = hoverText.iterator();
            while (textIterator.hasNext()) {
                String text = transformer.apply(textIterator.next());
                hoverComponent = hoverComponent.append(ComponentUtil.text(text));
                if (textIterator.hasNext()) {
                    hoverComponent = hoverComponent.append(Component.newline());
                }
            }
        }

        return component.hoverEvent(hoverComponent);
    }
}
