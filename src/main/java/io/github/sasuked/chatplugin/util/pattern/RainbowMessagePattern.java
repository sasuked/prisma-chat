package io.github.sasuked.chatplugin.util.pattern;


import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * Forked from <a href="https://github.com/Iridium-Development/IridiumColorAPI">IridiumColorAPI</a>
 */
public class RainbowMessagePattern extends MessagePattern {

    // <rainbow[saturation]> msg </rainbow>
    public RainbowMessagePattern() {
        super(compile("<rainbow\\[([0-9]{1,3})]>(.*?)</rainbow>"));
    }

    /**
     * Applies a rainbow pattern to the provided String.
     * Output might me the same as the input if this pattern is not present.
     *
     * @param string The String to which this pattern should be applied to
     * @return The new String with applied pattern
     */
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String saturation = matcher.group(1);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), ColorHelper.rainbow(content, Float.parseFloat(saturation)));
        }
        return string;
    }

}