package io.github.sasuked.chatplugin.util.pattern;

import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * Forked from <a href="https://github.com/Iridium-Development/IridiumColorAPI">IridiumColorAPI</a>
 */
public class SolidMessagePattern extends MessagePattern {

    // <#FFFFFF> msg
    public SolidMessagePattern() {
        super(compile("<#([0-9A-Fa-f]{6})>|#\\{([0-9A-Fa-f]{6})}"));
    }

    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String color = matcher.group(1);
            if (color == null) {
                color = matcher.group(2);
            }

            string = string.replace(matcher.group(), ColorHelper.getColor(color) + "");
        }
        return string;
    }

}