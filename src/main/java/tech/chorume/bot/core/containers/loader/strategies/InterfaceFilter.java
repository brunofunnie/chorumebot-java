package tech.chorume.bot.core.containers.loader.strategies;

import tech.chorume.bot.core.interfaces.ComponentFilter;

public class InterfaceFilter implements ComponentFilter {
    Class<?> implementedInterface;
    public InterfaceFilter(Class<?> implementedInterface) {
        this.implementedInterface = implementedInterface;
    }
    public boolean match(Class<?> clazz) {
        return this.implementedInterface.isAssignableFrom(clazz) && ! clazz.isInterface();
    }
}
