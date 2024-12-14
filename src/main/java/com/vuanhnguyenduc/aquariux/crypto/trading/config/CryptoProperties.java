package com.vuanhnguyenduc.aquariux.crypto.trading.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "crypto")
public class CryptoProperties {
    private double userDefaultWallet;

    private List<String> supportedCurrencies;
}
