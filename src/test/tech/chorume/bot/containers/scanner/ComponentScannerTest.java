package tech.chorume.bot.containers.scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.chorume.bot.core.annotations.CommandBuilder;
import tech.chorume.bot.core.containers.scanner.ComponentScanner;
import tech.chorume.bot.core.containers.scanner.filters.AnnotationFilter;
import tech.chorume.bot.core.containers.scanner.filters.BotSlashCommandBuilderFilter;
import tech.chorume.bot.core.containers.scanner.filters.InterfaceImplFilter;
import tech.chorume.bot.core.interfaces.SlashCommandBuilder;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;

import java.lang.annotation.Annotation;
import java.util.Collection;


public class ComponentScannerTest {

    @Test
    @DisplayName("Should find annotated classes in running context")
    public void scanTestAnnotation() {
        AnnotationFilter annotationFilter = new AnnotationFilter(CommandBuilder.class);
        ComponentScanner scanner = new ComponentScanner(annotationFilter);
        Collection<Class<?>> classes = scanner.scan();
        Assertions.assertEquals(2, classes.size(), "Should find 2 classes annotated with CommandBuilder.class");
        Assertions.assertEquals(1, classes.stream().filter(clazz -> clazz.getName().contains("SlashCommandBuilder1")).count(), "Should find SlashCommandBuilder1 as annotated class");
        Assertions.assertEquals(1, classes.stream().filter(clazz -> clazz.getName().contains("SlashCommandBuilder3NoInterface")).count(), "Should find SlashCommandBuilder3NoInterface as annotated class");
    }

    @Test
    @DisplayName("Should find classes implementing interface in running context")
    public void scanTestInterface() {
        InterfaceImplFilter interfaceFilter = new InterfaceImplFilter(SlashCommandBuilder.class);
        ComponentScanner scanner = new ComponentScanner(interfaceFilter);
        Collection<Class<?>> classes = scanner.scan();
        Assertions.assertEquals(2, classes.size(), "Should find 2 classes implementing SlashCommandBuilder.class interface");
        Assertions.assertEquals(1, classes.stream().filter(clazz -> clazz.getName().contains("SlashCommandBuilder1")).count(), "Should find SlashCommandBuilder1 as an implementing class");
        Assertions.assertEquals(1, classes.stream().filter(clazz -> clazz.getName().contains("SlashCommandBuilder2NoAnnotation")).count(), "Should find SlashCommandBuilder2NoAnnotation as an implementing class");
    }

    @Test
    @DisplayName("Should find bot command builder classes in running context")
    public void scanTestBotCommand() {
        BotSlashCommandBuilderFilter botCommandFilter = new BotSlashCommandBuilderFilter();
        ComponentScanner scanner = new ComponentScanner(botCommandFilter);
        Collection<Class<?>> classes = scanner.scan();
        Assertions.assertEquals(1, classes.size(), "Should find 1 bot command builder class in running context");
        Assertions.assertEquals(1, classes.stream().filter(clazz -> clazz.getName().contains("SlashCommandBuilder1")).count(), "Should find SlashCommandBuilder1 as a command builder in running context");
    }

    @Test
    @DisplayName("Should not find anything while scanning running context")
    public void scanTestNoResults() {
        InterfaceImplFilter filter1 = new InterfaceImplFilter(SlashCommandHandler.class);
        ComponentScanner scanner1 = new ComponentScanner(filter1);

        AnnotationFilter filter2 = new AnnotationFilter(Annotation.class);
        ComponentScanner scanner2 = new ComponentScanner(filter2);

        Assertions.assertEquals(0, scanner1.scan().size(), "Should not find any class implementing SlashCommandHandler.class interface in running context");
        Assertions.assertEquals(0, scanner2.scan().size(), "Should not find any class annotated with SlashCommandHandler.class in running context");
    }

}
