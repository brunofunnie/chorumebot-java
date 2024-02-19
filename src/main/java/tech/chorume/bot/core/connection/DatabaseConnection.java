package tech.chorume.bot.core.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConnection {
    private static final String PERSISTENCE_UNIT_NAME = "ChorumeBotPU"; // Nome da unidade de persistência

    private static EntityManagerFactory entityManagerFactory;

    // Método para obter uma instância do EntityManager
    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return entityManagerFactory.createEntityManager();
    }

    // Método para fechar a fábrica de gerenciadores de entidade (EntityManagerFactory)
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
