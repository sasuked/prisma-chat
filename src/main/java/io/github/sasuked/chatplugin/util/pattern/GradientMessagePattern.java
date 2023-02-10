package io.github.sasuked.chatplugin.util.pattern;

import java.awt.*;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;


/**
 * Forked from <a href="https://github.com/Iridium-Development/IridiumColorAPI">IridiumColorAPI</a>
 */
public class GradientMessagePattern extends MessagePattern {


    // <gradient:#FFFFFF> msg </gradient:#000000>
    public GradientMessagePattern() {
        super(compile("<gradient:#([0-9A-Fa-f]{6})>(.*?)</gradient:#([0-9A-Fa-f]{6})>"));
    }

    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String start = matcher.group(1);
            String end = matcher.group(3);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), ColorHelper.color(content, new Color(Integer.parseInt(start, 16)), new Color(Integer.parseInt(end, 16))));
        }
        return string;
    }

}