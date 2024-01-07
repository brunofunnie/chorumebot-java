package tech.chorume.bot.core.containers.scanner;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public abstract class ClassScanner {
    public abstract List<String> load();
    public static List<ClassScanner> getClassScanners(){
        return Arrays.asList(new JarClassScanner(), new LocalPathClassScanner());
    }

    public static Class<?> getMainClass() throws Exception {
        var className = Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(t->t.getMethodName().equals("main"))
                .findFirst()
                .map(StackTraceElement::getClassName)
                .get();
        return Class.forName(className);
    }

    protected String toFullQualifiedClassName(Path baseResourcePath, Path target) {
        var className = target.toString();

        if(baseResourcePath != null)
            className = className.replace(baseResourcePath.toString(), "");

        return className.replace(".class", "")
                .replace(System.getProperty("file.separator"), ".")
                .replaceAll("^\\.", "");
    }

}
