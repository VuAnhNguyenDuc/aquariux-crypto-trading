package com.vuanhnguyenduc.aquariux.crypto.trading.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "crypto_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoUser implements Serializable {

    @Id
    @Column(name = "user_id", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "balance", nullable = false)
    private double balance;

    @OneToMany(mappedBy = "cryptoUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CryptoWallet> cryptoWallets = new HashSet<>();

    @OneToMany(mappedBy = "cryptoUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CryptoTransaction> cryptoTransactions = new HashSet<>();
}
