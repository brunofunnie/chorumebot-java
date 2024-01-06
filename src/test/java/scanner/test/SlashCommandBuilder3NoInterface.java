package scanner.test;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import tech.chorume.bot.core.annotations.CommandBuilder;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;

@CommandBuilder
public class SlashCommandBuilder3NoInterface {

    public SlashCommandData buildCommand() {
        return null;
    }

    public SlashCommandHandler buildHandler() {
        return null;
    }
}
