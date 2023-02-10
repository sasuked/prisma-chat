package io.github.sasuked.chatplugin.util.pattern;

import java.util.regex.Pattern;


/**
 * Forked from <a href="https://github.com/Iridium-Development/IridiumColorAPI">IridiumColorAPI</a>
 */
public abstract class MessagePattern {

    protected final Pattern pattern;

    public MessagePattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    protected abstract String process(String input);
}
