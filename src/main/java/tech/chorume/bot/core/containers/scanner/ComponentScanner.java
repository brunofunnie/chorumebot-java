package tech.chorume.bot.core.containers.scanner;

import com.sun.tools.javac.Main;
import tech.chorume.bot.core.containers.BotContainer;
import tech.chorume.bot.core.interfaces.ComponentFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ComponentScanner{
    Logger logger = Logger.getLogger(BotContainer.class.getName());
    ComponentFilter filter;
    Path  baseResourcePath;
    public ComponentScanner(ComponentFilter filter) {
        this.filter = filter;
        this.baseResourcePath =  Paths.get(Main.class
                .getClassLoader()
                .getResource("")
                .getPath());
    }

    public Collection<Class<?>> scan(){

        try {
            logger.info("Loading classes from " + baseResourcePath);

            var getAllFiles = getAllClassFiles(baseResourcePath);

            return getAllFiles
                   .stream()
                   .map(this::toFullQualifiedClassName)
                   .map(t-> {
                       try {
                           return Class.forName(t);
                       } catch (ClassNotFoundException e) {
                           logger.warning("Skipping " + t + "class not found");
                       }
                       return null;
                   })
                   .filter(Objects::nonNull)
                   .filter(t->filter.match(t))
                   .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public List<Path> getAllClassFiles(Path basePath) throws IOException {
        try(var stream = Files.walk(basePath)){
            return stream.filter(f-> f.toString().endsWith(".class")).collect(Collectors.toList());
        }
    }

    protected String toFullQualifiedClassName(Path target) {
        return target
                .toString()
                .replace(baseResourcePath.toString(), "")
                .replace(".class", "")
                .replace(System.getProperty("file.separator"), ".")
                .replaceAll("^\\.", "");
    }
}
