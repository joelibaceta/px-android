package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;

import com.mercadopago.components.Mutator;
import com.mercadopago.components.MutatorPropsListener;
import com.mercadopago.model.Instruction;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.paymentresult.model.AmountFormat;
import com.mercadopago.paymentresult.props.PaymentResultProps;
import com.mercadopago.preferences.PaymentResultScreenPreference;

/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultPropsMutator implements Mutator, PaymentResultPropsView {

    private MutatorPropsListener propsListener;

    //Component props with default values
    private PaymentResultProps props = new PaymentResultProps();

    @Override
    public void setPropsListener(MutatorPropsListener listener) {
        this.propsListener = listener;
    }


    @Override
    public void setPropPaymentResult(@NonNull final PaymentResult paymentResult,
                                     @NonNull final PaymentResultScreenPreference paymentResultScreenPreference) {
        props = props.toBuilder()
                .setPaymentResult(paymentResult)
                .setPreference(paymentResultScreenPreference)
                .setHeaderMode("wrap")
                .setLoading(false)
                .build();
        notifyPropsChanged();
    }

    @Override
    public void setPropInstruction(@NonNull final Instruction instruction,
                                   @NonNull final AmountFormat amountFormat) {
        props = props.toBuilder()
                .setInstruction(instruction)
                .setAmountFormat(amountFormat)
                .setLoading(false)
                .build();
        notifyPropsChanged();
    }

    private void notifyPropsChanged() {
        if (propsListener != null) {
            propsListener.onProps(props);
        }
    }

    public void renderDefaultProps() {
        notifyPropsChanged();
    }
}
