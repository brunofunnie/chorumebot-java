package tech.chorume.bot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue
    private long id;
    
    @Column(name = "discord_user_name")
    private String discordUserName;

    @Column(name = "discord_user_id")
    private String discordUserId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "received_initial_coins")
    private boolean receivedInitialCoins;
    
    public Users( String discordUserName, String discordUserId) {
        this.discordUserName = discordUserName;
        this.discordUserId = discordUserId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.receivedInitialCoins = false;
    }

    public Users (){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDiscordUserName() {
        return discordUserName;
    }

    public void setDiscordUserName(String discordUserName) {
        this.discordUserName = discordUserName;
    }

    public String getDiscordUserId() {
        return discordUserId;
    }

    public void setDiscordUserId(String discordUserId) {
        this.discordUserId = discordUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public boolean getReceivedInitialCoins() {
        return receivedInitialCoins;
    }
    
    public void setReceivedInitialCoins(boolean receivedInitialCoins) {
        this.receivedInitialCoins = receivedInitialCoins;
    }    
}
