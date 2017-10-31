package com.mercadopago.paymentresult.model;

import java.math.BigDecimal;

/**
 * Created by vaserber on 10/27/17.
 */

public class AmountFormat {

    private String currencyId;
    private BigDecimal amount;
    private String paymentMethodName;

    public AmountFormat(String currencyId, BigDecimal amount) {
        this.currencyId = currencyId;
        this.amount = amount;
    }

    public AmountFormat(String currencyId, BigDecimal amount, String paymentMethodName) {
        this.currencyId = currencyId;
        this.amount = amount;
        this.paymentMethodName = paymentMethodName;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }
}
