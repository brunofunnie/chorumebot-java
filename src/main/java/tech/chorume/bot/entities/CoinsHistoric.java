package tech.chorume.bot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "coins_historic")
public class CoinsHistoric {

    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "amount")
    private Double amount;

    @Column(name = "description")
    private String description;

    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public CoinsHistoric(Users user, String type, Double amount, String description) {
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
    
    public CoinsHistoric() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    private static final int DECIMAL_PLACES = 2;
    
    public void setAmount(Double amount) {
        if (amount != null) {
            BigDecimal roundedAmount = BigDecimal.valueOf(amount).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
            this.amount = roundedAmount.doubleValue();
        } else {
            this.amount = null;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
