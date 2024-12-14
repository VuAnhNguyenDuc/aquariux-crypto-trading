package com.vuanhnguyenduc.aquariux.crypto.trading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.PriceSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoCurrencyResponseDTO {
    @JsonProperty("currency_name")
    private String currencyName;

    @JsonProperty("bid_price")
    private double bidPrice;

    @JsonProperty("bid_source")
    private PriceSource bidSource;

    @JsonProperty("ask_price")
    private double askPrice;

    @JsonProperty("ask_source")
    private PriceSource askSource;

    @JsonProperty("last_updated_on")
    private Timestamp lastUpdatedOn;
}
