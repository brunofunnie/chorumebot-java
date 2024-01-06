package tech.chorume.bot.core.containers.scanner;

import java.nio.file.Paths;
import java.util.List;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Scan java classes from executing jar file
 */
public class JarClassScanner extends ClassScanner {

    Logger logger = Logger.getLogger(JarClassScanner.class.getName());

    public List<String> load(){
        try {
            var mainClassName = getMainClass();

            var jarPath = mainClassName
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation();

            var file = Paths.get(jarPath.toURI().getPath()).toFile();

            if(! file.getName().endsWith(".jar")){
                throw new RuntimeException("Not a jar file: " + file.getName());
            }

            logger.info("Loading class names from jar file: " + jarPath);

            try (JarFile jarFile = new JarFile(file)) {
                return jarFile.stream()
                        .filter(f -> f.getName().endsWith(".class"))
                        .map(f -> Paths.get(f.getName()))
                        .map(t -> this.toFullQualifiedClassName(null, t))
                        .collect(Collectors.toList());
            }
        } catch(Exception e){
            logger.info("Unable to load classes from jar: " + e.getMessage());
            return List.of();
        }
    }
}
