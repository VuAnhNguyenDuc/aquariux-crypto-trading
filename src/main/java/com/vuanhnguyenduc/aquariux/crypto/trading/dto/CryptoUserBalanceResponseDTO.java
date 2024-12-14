package com.vuanhnguyenduc.aquariux.crypto.trading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoUserBalanceResponseDTO {
    @JsonProperty(value = "remaining_balance")
    private double remainingBalance;

    @JsonProperty(value = "crypto_wallets")
    private List<CryptoWalletResponseDTO> cryptoWallets;
}
