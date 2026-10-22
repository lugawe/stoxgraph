package org.stoxgraph.server.adapters.in.api.json;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonQuote {

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("exchange")
    private String exchange;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("open")
    private BigDecimal open;

    @JsonProperty("high")
    private BigDecimal high;

    @JsonProperty("low")
    private BigDecimal low;

    @JsonProperty("previous_close")
    private BigDecimal previousClose;

    @JsonProperty("change")
    private BigDecimal change;

    @JsonProperty("percent_change")
    private BigDecimal percentChange;

    @JsonProperty("volume")
    private long volume;

    @JsonProperty("market_open")
    private boolean marketOpen;

    public JsonQuote() {}

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(BigDecimal percentChange) {
        this.percentChange = percentChange;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public boolean isMarketOpen() {
        return marketOpen;
    }

    public void setMarketOpen(boolean marketOpen) {
        this.marketOpen = marketOpen;
    }
}
