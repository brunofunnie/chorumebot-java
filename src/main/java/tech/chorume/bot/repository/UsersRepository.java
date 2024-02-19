package tech.chorume.bot.repository;
import java.util.List;
import tech.chorume.bot.entities.Users;


public interface UsersRepository {
    Users findById(long id);
    Users findByDiscordUserId(String discordUserName, String discordUserId);
    List<Users> findAll();
    Users save(Users user);
}
