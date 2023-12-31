package tech.chorume.bot.core.containers.scanner.filters;

import tech.chorume.bot.core.interfaces.ComponentFilter;

public class InterfaceImplFilter implements ComponentFilter {
    Class<?> implementedInterface;
    public InterfaceImplFilter(Class<?> implementedInterface) {
        this.implementedInterface = implementedInterface;
    }
    public boolean match(Class<?> clazz) {
        return this.implementedInterface.isAssignableFrom(clazz) && ! clazz.isInterface();
    }
}
