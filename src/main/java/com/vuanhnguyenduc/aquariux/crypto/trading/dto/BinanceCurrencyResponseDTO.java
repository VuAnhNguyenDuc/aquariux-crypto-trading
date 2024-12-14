package com.vuanhnguyenduc.aquariux.crypto.trading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceCurrencyResponseDTO {
    @JsonProperty(value = "symbol")
    private String symbol;

    @JsonProperty(value = "bidPrice")
    private String bidPrice;

    @JsonProperty(value = "askPrice")
    private String askPrice;

}