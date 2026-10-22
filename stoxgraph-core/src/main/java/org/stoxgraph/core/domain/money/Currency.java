package org.stoxgraph.core.domain.money;

public enum Currency {
    USD("US Dollar"),
    EUR("Euro"),
    JPY("Japanese Yen"),
    GBP("British Pound"),
    CNY("Chinese Yuan"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    HKD("Hong Kong Dollar"),
    SGD("Singapore Dollar"),
    SEK("Swedish Krona"),
    KRW("South Korean Won");

    private final String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String isoCode() {
        return name();
    }

    public String fullName() {
        return fullName;
    }
}
