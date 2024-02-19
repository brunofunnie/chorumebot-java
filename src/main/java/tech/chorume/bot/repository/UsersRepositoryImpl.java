package tech.chorume.bot.repository;

import tech.chorume.bot.entities.Users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UsersRepositoryImpl implements UsersRepository{

    private final EntityManager entityManager;

    public UsersRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Users findById(long id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    public Users findByDiscordUserId(String discordUserName, String discordUserId) {
        Users user = null;
        try {
            user = entityManager.createQuery("SELECT u FROM Users u WHERE u.discordUserId = :discordUserId", Users.class)
                    .setParameter("discordUserId", discordUserId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            user = new Users(discordUserName, discordUserId);
            save(user);
        }
        return user;
    }

    @Override
    public List<Users> findAll() {
        TypedQuery<Users> query = entityManager.createQuery("SELECT u FROM Users u", Users.class);
        return query.getResultList();
    }

    @Override
    public Users save(Users user) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        return user;
    }
}
