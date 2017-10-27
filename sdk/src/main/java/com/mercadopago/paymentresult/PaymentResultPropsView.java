package com.mercadopago.paymentresult;

import com.mercadopago.model.Instruction;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.mvp.MvpView;
import com.mercadopago.paymentresult.model.AmountFormat;
import com.mercadopago.preferences.PaymentResultScreenPreference;

public interface PaymentResultPropsView extends MvpView {

    void setPropPaymentResult(final PaymentResult paymentResult, final PaymentResultScreenPreference paymentResultScreenPreference);

    void setPropInstruction(final Instruction instruction, final AmountFormat amountFormat);
}
