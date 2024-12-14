package com.vuanhnguyenduc.aquariux.crypto.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Entity(name = "crypto_transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoTransaction implements Serializable {
    @Id
    @Column(name = "transaction_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int transactionId;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "crypto_currency")
    private String cryptoCurrency;

    @Column(name = "amount")
    private double amount;

    @Column(name = "price")
    private double price;

    @Column(name = "total")
    private double total;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "last_updated_on")
    private Timestamp lastUpdatedOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CryptoUser cryptoUser;

    @Column(name = "error_info")
    private String errorInfo;

    @PrePersist
    private void preSave() {
        this.createdOn = new Timestamp(Instant.now().toEpochMilli());
        this.lastUpdatedOn = new Timestamp(Instant.now().toEpochMilli());
    }

    @PreUpdate
    private void preUpdate() {
        this.lastUpdatedOn = new Timestamp(Instant.now().toEpochMilli());
    }
}
