package tech.chorume.bot.core.containers.loader.strategies;

import tech.chorume.bot.core.interfaces.ComponentFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Predefined composite filter for Bot commands. Could be turned into a class_and_interface filter
 * if this same logic needs to be applied somewhere else.
 */
public class CompositeFilter implements ComponentFilter {

    List<ComponentFilter> filters = new ArrayList<>();

    public CompositeFilter(List<ComponentFilter> filters) {
        this.filters.addAll(filters);
    }

    @Override
    public boolean match(Class<?> clazz) {
        return filters.stream().allMatch(filter -> filter.match(clazz));
    }
}
