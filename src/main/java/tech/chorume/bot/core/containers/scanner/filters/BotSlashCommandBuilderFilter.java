package tech.chorume.bot.core.containers.scanner.filters;

import tech.chorume.bot.core.annotations.CommandBuilder;
import tech.chorume.bot.core.interfaces.ComponentFilter;
import tech.chorume.bot.core.interfaces.SlashCommandBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Predefined composite filter for Bot commands. Could be turned into a class_and_interface filter
 * if this same logic needs to be applied somewhere else.
 */
public class BotSlashCommandBuilderFilter implements ComponentFilter {

    List<ComponentFilter> filters = new ArrayList<>();
    public BotSlashCommandBuilderFilter() {
        filters.add(new AnnotationFilter(CommandBuilder.class));
        filters.add(new InterfaceImplFilter(SlashCommandBuilder.class));
    }
    @Override
    public boolean match(Class<?> clazz) {
        return filters.stream().allMatch(filter -> filter.match(clazz));
    }
}
