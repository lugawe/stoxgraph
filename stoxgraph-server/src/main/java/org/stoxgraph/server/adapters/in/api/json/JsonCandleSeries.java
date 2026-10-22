package org.stoxgraph.server.adapters.in.api.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCandleSeries {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("interval")
    private String interval;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("aggregate")
    private JsonCandle aggregate;

    @JsonProperty("candles")
    private List<JsonCandle> candles;

    public JsonCandleSeries() {}

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public JsonCandle getAggregate() {
        return aggregate;
    }

    public void setAggregate(JsonCandle aggregate) {
        this.aggregate = aggregate;
    }

    public List<JsonCandle> getCandles() {
        return candles;
    }

    public void setCandles(List<JsonCandle> candles) {
        this.candles = candles;
    }
}
