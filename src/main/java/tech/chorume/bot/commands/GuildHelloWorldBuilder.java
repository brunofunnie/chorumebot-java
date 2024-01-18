package tech.chorume.bot.commands;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import tech.chorume.bot.core.annotations.GuildCommandBuilder;
import tech.chorume.bot.core.interfaces.SlashCommandBuilder;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;

@GuildCommandBuilder
public class GuildHelloWorldBuilder implements SlashCommandBuilder {
    @Override
    public SlashCommandData buildCommand() {
        return Commands.slash("welcome", "Seja bem vindo!");
    }
    @Override
    public SlashCommandHandler buildHandler() {
        return event -> event.reply("Olá Commando! Seja bem-vindo").queue();
    }
}
