package com.mercadopago.paymentresult;

import android.content.Context;

import com.mercadopago.R;
import com.mercadopago.callbacks.Callback;
import com.mercadopago.core.MercadoPagoServices;
import com.mercadopago.exceptions.MercadoPagoError;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.Instructions;
import com.mercadopago.mvp.OnResourcesRetrievedCallback;
import com.mercadopago.util.ApiUtil;


public class PaymentResultProviderImpl implements PaymentResultProvider {
    private final Context context;
    private final MercadoPagoServices mercadoPago;

    public PaymentResultProviderImpl(Context context, String publicKey, String privateKey) {
        this.context = context;

        this.mercadoPago = new MercadoPagoServices.Builder()
                .setContext(context)
                .setPublicKey(publicKey)
                .setPrivateKey(privateKey)
                .build();
    }

    @Override
    public void getInstructionsAsync(Long paymentId, String paymentTypeId, final OnResourcesRetrievedCallback<Instructions> onResourcesRetrievedCallback) {
        mercadoPago.getInstructions(paymentId, paymentTypeId, new Callback<Instructions>() {
            @Override
            public void success(Instructions instructions) {
                onResourcesRetrievedCallback.onSuccess(instructions);
            }

            @Override
            public void failure(ApiException apiException) {
                onResourcesRetrievedCallback.onFailure(new MercadoPagoError(apiException, ApiUtil.RequestOrigin.GET_INSTRUCTIONS));
            }
        });
    }

    @Override
    public String getStandardErrorMessage() {
        return context.getString(R.string.mpsdk_standard_error_message);
    }

    @Override
    public String getApprovedTitle() {
        return context.getString(R.string.mpsdk_title_approved_payment);
    }

    @Override
    public String getPendingTitle() {
        return context.getString(R.string.mpsdk_title_pending_payment);
    }

    @Override
    public String getRejectedOtherReasonTitle(String paymentMethodName) {
        return String.format(context.getString(R.string.mpsdk_title_other_reason_rejection), paymentMethodName);
    }

    @Override
    public String getRejectedInsufficientAmountTitle(String paymentMethodName) {
        return String.format(context.getString(R.string.mpsdk_text_insufficient_amount), paymentMethodName);
    }

    @Override
    public String getRejectedDuplicatedPaymentTitle(String paymentMethodName) {
        return String.format(context.getString(R.string.mpsdk_title_other_reason_rejection), paymentMethodName);
    }

    @Override
    public String getRejectedCardDisabledTitle(String paymentMethodName) {
        return String.format(context.getString(R.string.mpsdk_text_active_card), paymentMethodName);
    }

    @Override
    public String getRejectedBadFilledCardTitle(String paymentMethodName) {
        return String.format(context.getString(R.string.mpsdk_text_some_card_data_is_incorrect), paymentMethodName);
    }

    @Override
    public String getRejectedHighRiskTitle() {
        return context.getString(R.string.mpsdk_title_rejection_high_risk);
    }

    @Override
    public String getRejectedMaxAttemptsTitle() {
        return context.getString(R.string.mpsdk_title_rejection_max_attempts);
    }

    @Override
    public String getRejectedInsufficientDataTitle() {
        return context.getString(R.string.mpsdk_bolbradesco_rejection);
    }

    @Override
    public String getRejectedBadFilledOther() {
        return context.getString(R.string.mpsdk_title_bad_filled_other);
    }

    @Override
    public String getRejectedCallForAuthorizeTitle() {
        return context.getString(R.string.mpsdk_title_activity_call_for_authorize);
    }

    @Override
    public String getEmptyText() {
        return context.getString(R.string.mpsdk_empty_string);
    }

    @Override
    public String getPendingLabel() {
        return context.getString(R.string.mpsdk_pending_label);
    }

    @Override
    public String getRejectionLabel() {
        return context.getString(R.string.mpsdk_rejection_label);
    }
}
