package scanner.test;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import tech.chorume.bot.core.annotations.CommandBuilder;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;

@CommandBuilder
public class CommandNoInterfaceForClassScanningTest {

    public SlashCommandData buildCommand() {
        return Commands.slash("/testcommand", "ommand to test class scanning");
    }

    public SlashCommandHandler buildHandler() {
        return event -> event.reply("Command to test class scanning");
    }
}
