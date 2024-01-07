package tech.chorume.bot.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import tech.chorume.bot.core.containers.BotContainer;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;
import java.util.*;

public class DiscordBot {
    public void start() {

        var botContainer = new BotContainer();

        Set<SlashCommandData> commands = new HashSet<>();
        Set<SlashCommandData> guildCommands = new HashSet<>();
        Map<String, SlashCommandHandler> handlers = new HashMap<>();

        var configuration = botContainer.loadBotConfiguration();
        var builders = botContainer.loadSlahCommandBuilders();
        var guildBuilders = botContainer.loadGuildSlahCommandBuilders();

        builders.forEach(builder -> {
            var command = builder.buildCommand();
            var handler = builder.buildHandler();
            handlers.put(command.getName(), handler);
            commands.add(command);
        });

        guildBuilders.forEach(builder -> {
            var command = builder.buildCommand();
            var handler = builder.buildHandler();
            handlers.put(command.getName(), handler);
            guildCommands.add(command);
        });

        try {
            // Low memory-consume profile JDA - https://docs.jda.wiki/net/dv8tion/jda/api/JDABuilder.html#createLight(java.lang.String)
            JDA jda = JDABuilder.createLight(configuration.getDiscordToken())
                    .enableIntents(configuration.getGatewayIntents())
                    .addEventListeners(configuration.getListeners(handlers).toArray())
                    .build();

            jda.updateCommands()
                    .addCommands(commands)
                    .queue();

            jda.awaitReady();

            // for now, just add all guild commands to all guilds to expedite development
            jda.getGuilds()
                    .stream()
                    .forEach(guild -> guild.updateCommands()
                                            .addCommands(guildCommands)
                                            .queue());

        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        cleanStartUP();
    }
    private void cleanStartUP() {
        System.gc();
    }
}
