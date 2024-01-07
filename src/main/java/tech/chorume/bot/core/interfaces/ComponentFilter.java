package tech.chorume.bot.core.interfaces;

public interface ComponentFilter {
    boolean match(Class<?> clazz);
}
