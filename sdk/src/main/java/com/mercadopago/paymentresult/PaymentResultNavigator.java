package com.mercadopago.paymentresult;

import com.mercadopago.exceptions.MercadoPagoError;
import com.mercadopago.model.ApiException;

/**
 * Created by vaserber on 10/27/17.
 */

public interface PaymentResultNavigator {

    void showApiExceptionError(ApiException exception, String requestOrigin);

    void showError(MercadoPagoError error, String requestOrigin);
}
