package tech.chorume.bot.core;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;

import java.util.Map;

public class DispatcherListener extends ListenerAdapter {
    private Map<String, SlashCommandHandler> handlers;
    public DispatcherListener(Map<String, SlashCommandHandler> handlers) {
        this.handlers = handlers;
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String name = event.getName();
        SlashCommandHandler handler = handlers.get(name);
        if (handler == null) {
            System.err.println(name + " has not handler defined");
            event.getInteraction().reply("Não achei o comando que você queria :(")
                    .queue();
            return;
        }
        handler.handle(event);
    }
}
