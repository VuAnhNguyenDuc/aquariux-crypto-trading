package com.vuanhnguyenduc.aquariux.crypto.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Entity(name = "crypto_currency")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "currency_id")
    private int currencyId;

    @Column(name = "currency_name", unique = true, nullable = false)
    private String currencyName;

    @Column(name = "bid_price")
    private double bidPrice;

    @Column(name = "bid_source")
    @Enumerated(EnumType.STRING)
    private PriceSource bidSource;

    @Column(name = "ask_price")
    private double askPrice;

    @Column(name = "ask_source")
    @Enumerated(EnumType.STRING)
    private PriceSource askSource;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @PrePersist
    private void preSave() {
        this.createdOn = new Timestamp(Instant.now().toEpochMilli());
        this.updatedOn = new Timestamp(Instant.now().toEpochMilli());
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedOn = new Timestamp(Instant.now().toEpochMilli());
    }
}
