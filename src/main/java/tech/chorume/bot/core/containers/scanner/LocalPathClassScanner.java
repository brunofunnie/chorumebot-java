package tech.chorume.bot.core.containers.scanner;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LocalPathClassScanner extends ClassScanner {

    Logger logger = Logger.getLogger(JarClassScanner.class.getName());

    public List<String> load() {

        try {
            var location = getMainClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation();

            var basePath = new File(location.toURI()).toPath();

            logger.info("Loading class names from local path: " + basePath);

            try (var stream = Files.walk(basePath)) {
                return stream
                        .filter(f -> f.toString().endsWith(".class"))
                        .map(t -> this.toFullQualifiedClassName(basePath, t))
                        .collect(Collectors.toList());
            }
        } catch (Exception exception) {
            logger.info("Unable to load classes from local path");
            return List.of();
        }
    }
}
