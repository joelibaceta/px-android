package com.mercadopago.paymentresult.props;

import android.support.annotation.NonNull;
import android.text.Spanned;

import com.mercadopago.model.Instruction;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.paymentresult.PaymentResultScreenPreference;
import com.mercadopago.util.CurrenciesUtil;

/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultProps {

    public final PaymentResult paymentResult;
    public final PaymentResultScreenPreference paymentResultScreenPreference;
    public final Instruction instruction;
    public final String headerMode; //"wrap", "stretch"

    public PaymentResultProps() {
        this.paymentResult = null;
        this.paymentResultScreenPreference = null;
        this.instruction = null;
        this.headerMode = "wrap";
    }

    public PaymentResultProps(PaymentResult paymentResult, PaymentResultScreenPreference paymentResultScreenPreference, Instruction instruction, String headerMode) {
        this.paymentResult = paymentResult;
        this.paymentResultScreenPreference = paymentResultScreenPreference;
        this.instruction = instruction;
        this.headerMode = headerMode;
    }

    public PaymentResultProps(@NonNull final Builder builder) {
        this.paymentResult = builder.paymentResult;
        this.headerMode = builder.headerMode;
        this.paymentResultScreenPreference = builder.paymentResultScreenPreference;
        this.instruction = builder.instruction;
    }

    public Builder toBuilder() {
        return new Builder()
                .setPaymentResult(this.paymentResult)
                .setPreference(this.paymentResultScreenPreference)
                .setHeaderMode(this.headerMode)
                .setInstruction(this.instruction);
    }

    public boolean hasCustomizedTitle() {
        //TODO revisar que para el status del pago, la preferencia tenga un titulo seteado
        return false;
    }

    public String getPreferenceTitle() {
        //TODO devolver el titulo para el estado del pago
        return paymentResultScreenPreference.getApprovedTitle();
    }

    public boolean hasInstructions() {
        return instruction != null;
    }

    public String getInstructionsTitle() {
        return instruction.getTitle();
    }

    public class Builder {

        public PaymentResult paymentResult;
        public PaymentResultScreenPreference paymentResultScreenPreference;
        public Instruction instruction;
        public String headerMode;

        public Builder setPaymentResult(PaymentResult paymentResult) {
            this.paymentResult = paymentResult;
            return this;
        }

        public Builder setHeaderMode(String headerMode) {
            this.headerMode = headerMode;
            return this;
        }

        public Builder setPreference(PaymentResultScreenPreference paymentResultScreenPreference) {
            this.paymentResultScreenPreference = paymentResultScreenPreference;
            return this;
        }

        public Builder setInstruction(Instruction instruction) {
            this.instruction = instruction;
            return this;
        }

        public PaymentResultProps build() {
            return new PaymentResultProps(this);
        }
    }
}
