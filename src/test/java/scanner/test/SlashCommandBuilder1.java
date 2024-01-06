package scanner.test;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import tech.chorume.bot.core.annotations.CommandBuilder;
import tech.chorume.bot.core.interfaces.SlashCommandBuilder;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;

@CommandBuilder
public class SlashCommandBuilder1 implements SlashCommandBuilder {

    @Override
    public SlashCommandData buildCommand() {
        return null;
    }

    @Override
    public SlashCommandHandler buildHandler() {
        return null;
    }
}
