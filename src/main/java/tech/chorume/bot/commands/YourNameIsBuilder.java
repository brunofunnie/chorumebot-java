package tech.chorume.bot.commands;

import jakarta.persistence.EntityManager;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import tech.chorume.bot.core.annotations.CommandBuilder;
import tech.chorume.bot.core.connection.DatabaseConnection;
import tech.chorume.bot.core.interfaces.SlashCommandBuilder;
import tech.chorume.bot.core.interfaces.SlashCommandHandler;
import tech.chorume.bot.entities.Users;
import tech.chorume.bot.repository.UsersRepository;
import tech.chorume.bot.repository.UsersRepositoryImpl;

@CommandBuilder
public class YourNameIsBuilder implements SlashCommandBuilder {

    @Override
    public SlashCommandData buildCommand() {
        return Commands.slash("yournameis", "Qual o seu nome?");
    }

    @Override
    public SlashCommandHandler buildHandler() {
        return event -> {
            try {
                EntityManager entityManager = DatabaseConnection.getEntityManager();
                String globalName = event.getMember().getEffectiveName();
                String userId = event.getMember().getId();

                UsersRepository usersRepository = new UsersRepositoryImpl(entityManager);
                Users usuario = usersRepository.findByDiscordUserId(globalName, userId);

                event.reply("Seu nome é " + usuario.getDiscordUserName()).queue();
            } catch (Exception e) {
                e.printStackTrace();
                event.reply("Ocorreu um erro ao processar sua solicitação.").queue();
            } finally {
                DatabaseConnection.closeEntityManagerFactory(); 
            }
        };
    }
}
