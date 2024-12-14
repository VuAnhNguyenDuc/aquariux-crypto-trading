package com.vuanhnguyenduc.aquariux.crypto.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "crypto_wallet")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoWallet implements Serializable {

    @Id
    @Column(name = "wallet_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int walletId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CryptoUser cryptoUser;

    @Column(name = "crypto_currency")
    private String cryptoCurrency;

    @Column(name = "balance")
    private double balance;

}
