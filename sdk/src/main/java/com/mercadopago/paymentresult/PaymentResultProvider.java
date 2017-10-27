package com.mercadopago.paymentresult;

import com.mercadopago.model.Instructions;
import com.mercadopago.mvp.OnResourcesRetrievedCallback;
import com.mercadopago.mvp.ResourcesProvider;

public interface PaymentResultProvider extends ResourcesProvider {

    void getInstructionsAsync(Long paymentId, String paymentTypeId, final OnResourcesRetrievedCallback<Instructions> onResourcesRetrievedCallback);

    String getStandardErrorMessage();

    String getApprovedTitle();

    String getPendingTitle();

    String getRejectedOtherReasonTitle(String paymentMethodName);

    String getRejectedInsufficientAmountTitle(String paymentMethodName);

    String getRejectedDuplicatedPaymentTitle(String paymentMethodName);

    String getRejectedCardDisabledTitle(String paymentMethodName);

    String getRejectedBadFilledCardTitle(String paymentMethodName);

    String getRejectedHighRiskTitle();

    String getRejectedMaxAttemptsTitle();

    String getRejectedInsufficientDataTitle();

    String getRejectedBadFilledOther();

    String getEmptyText();

    String getPendingLabel();

    String getRejectionLabel();
}
