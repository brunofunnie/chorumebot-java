package tech.chorume.bot.core.containers;

import tech.chorume.bot.core.annotations.CommandBuilder;
import tech.chorume.bot.core.annotations.GuildCommandBuilder;
import tech.chorume.bot.core.containers.loader.ComponentLoader;
import tech.chorume.bot.core.containers.loader.strategies.AnnotationFilter;
import tech.chorume.bot.core.containers.loader.strategies.CompositeFilter;
import tech.chorume.bot.core.containers.loader.strategies.InterfaceFilter;
import tech.chorume.bot.core.interfaces.DiscordBotConfiguration;
import tech.chorume.bot.core.interfaces.SlashCommandBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BotContainer {
    Logger logger = Logger.getLogger(BotContainer.class.getName());
    public DiscordBotConfiguration loadBotConfiguration() {
        var scanner = new ComponentLoader(new InterfaceFilter(DiscordBotConfiguration.class));
        try {
            var discordBotConfigClass = scanner.scan();
            return (DiscordBotConfiguration) discordBotConfigClass
                    .stream()
                    .findFirst()
                    .get()
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Unable to load bot configuration: " + exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    public Collection<SlashCommandBuilder> loadSlahCommandBuilders() {

        var filters = List.of(
                new AnnotationFilter(CommandBuilder.class),
                new InterfaceFilter(SlashCommandBuilder.class)
        );

        var scanner = new ComponentLoader(new CompositeFilter(filters));

        try {
            var commandBuilderList = scanner.scan();
            return commandBuilderList
                    .stream()
                    .map(t -> {
                                try {
                                    return (SlashCommandBuilder) t.getDeclaredConstructor().newInstance();
                                } catch (Exception exception) {
                                    logger.warning("Skipping - Unable to load bot slash command " + t.getName() + " because no-args constructor was not found.");
                                }
                                return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Unable to load bot slash commands: " + exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    public Collection<SlashCommandBuilder>  loadGuildSlahCommandBuilders() {

        var filters = List.of(
                new AnnotationFilter(GuildCommandBuilder.class),
                new InterfaceFilter(SlashCommandBuilder.class)
        );

        var scanner = new ComponentLoader(new CompositeFilter(filters));

        try {
            var commandBuilderList = scanner.scan();
            return commandBuilderList
                    .stream()
                    .map(t -> {
                        try {
                            return (SlashCommandBuilder) t.getDeclaredConstructor().newInstance();
                        } catch (Exception exception) {
                            logger.warning("Skipping - Unable to load bot slash command " + t.getName() + " because no-args constructor was not found.");
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Unable to load bot slash commands: " + exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }
}
