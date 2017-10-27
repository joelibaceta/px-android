package com.mercadopago.paymentresult.model;

import java.math.BigDecimal;

/**
 * Created by vaserber on 10/27/17.
 */

public class AmountFormat {

    private String currencyId;
    private BigDecimal amount;

    public AmountFormat(String currencyId, BigDecimal amount) {
        this.currencyId = currencyId;
        this.amount = amount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
