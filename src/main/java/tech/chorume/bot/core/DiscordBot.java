package tech.chorume.bot.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import tech.chorume.bot.core.containers.BotContainer;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;
import java.util.*;

public class DiscordBot {
    public void start() {

        var botContainer = new BotContainer();

        Set<SlashCommandData> commands = new HashSet<>();
        Map<String, SlashCommandHandler> handlers = new HashMap<>();

        var configuration = botContainer.loadBotConfiguration();
        var builders = botContainer.loadSlahCommandBuilders();

        builders.forEach(builder -> {
            var command = builder.buildCommand();
            var handler = builder.buildHandler();
            handlers.put(command.getName(), handler);
            commands.add(command);
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
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        cleanStartUP();
    }
    private void cleanStartUP() {
        System.gc();
    }
}
