package tech.chorume.bot.core.containers;

import tech.chorume.bot.core.containers.scanner.ComponentScanner;
import tech.chorume.bot.core.containers.scanner.filters.BotSlashCommandBuilderFilter;
import tech.chorume.bot.core.containers.scanner.filters.InterfaceImplFilter;
import tech.chorume.bot.core.interfaces.DiscordBotConfiguration;
import tech.chorume.bot.core.interfaces.SlashCommandBuilder;

import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BotContainer {
    Logger logger = Logger.getLogger(BotContainer.class.getName());
    public DiscordBotConfiguration loadBotConfiguration() {
        var scanner = new ComponentScanner(new InterfaceImplFilter(DiscordBotConfiguration.class));
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
        var scanner = new ComponentScanner(new BotSlashCommandBuilderFilter());
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
