package tech.chorume.bot.repository;

import tech.chorume.bot.entities.CoinsHistoric;
import jakarta.persistence.EntityManager;

public class CoinsHistoricRepositoryImpl implements CoinsHistoricRepository {
    private final EntityManager entityManager;

    public CoinsHistoricRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveTransaction(CoinsHistoric coinsHistoric) {
        // Lógica para salvar a transação no banco de dados usando o EntityManager
        entityManager.getTransaction().begin();
        entityManager.persist(coinsHistoric);
        entityManager.getTransaction().commit();
    }

    @Override
    public Double getTotalCoinsByUserId(Long userId) {
        // Lógica para obter o total de moedas de um usuário do banco de dados usando o EntityManager
        return entityManager.createQuery("SELECT COALESCE(SUM(CASE WHEN ch.type = 'credit' THEN ch.amount ELSE -ch.amount END), 0) " +
                "FROM CoinsHistoric ch " +
                "WHERE ch.user.id = :userId", Double.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}