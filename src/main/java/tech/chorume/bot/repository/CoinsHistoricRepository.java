package tech.chorume.bot.repository;

import tech.chorume.bot.entities.CoinsHistoric;

public interface CoinsHistoricRepository {
    void saveTransaction(CoinsHistoric coinsHistoric);

    Double getTotalCoinsByUserId(Long userId);
}
