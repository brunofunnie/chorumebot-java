package tech.chorume.bot.core.containers.loader;

import tech.chorume.bot.core.containers.scanner.ClassScanner;
import tech.chorume.bot.core.interfaces.ComponentFilter;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class ComponentLoader {
    Logger logger = Logger.getLogger(ComponentLoader.class.getName());
    ComponentFilter filter;
    List<String> classNames;

    public ComponentLoader(ComponentFilter filter) {
        this.filter = filter;

        this.classNames = ClassScanner
                .getClassScanners()
                .stream()
                .flatMap(t-> t.load().stream())
                .toList();
    }


    public Collection<Class<?>> scan(){

        try {
            var mainPackage = ClassScanner
                    .getMainClass()
                    .getPackage()
                    .getName();

            return classNames
                   .stream()
                   .filter(t -> t.contains(mainPackage))
                   .map(t-> {
                       try {
                           return Class.forName(t);
                       } catch (ClassNotFoundException e) {
                           logger.warning("Skipping " + t + "class not found");
                       }
                       return null;
                   })
                   .filter(Objects::nonNull)
                   .filter(t->filter.match(t))      // actual scan strategy
                   .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
