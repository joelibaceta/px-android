package com.mercadopago.paymentresult.props;

import android.support.annotation.NonNull;

import com.mercadopago.model.Instruction;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.paymentresult.model.AmountFormat;
import com.mercadopago.preferences.PaymentResultScreenPreference;

/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultProps {

    public final PaymentResult paymentResult;
    public final PaymentResultScreenPreference paymentResultScreenPreference;
    public final Instruction instruction;
    public final String headerMode;
    public final AmountFormat amountFormat;
    public final boolean loading;

    public PaymentResultProps() {
        this.paymentResult = null;
        this.paymentResultScreenPreference = null;
        this.instruction = null;
        this.headerMode = "wrap";
        this.amountFormat = null;
        this.loading = true;
    }

    public PaymentResultProps(final PaymentResult paymentResult,
                              final PaymentResultScreenPreference paymentResultScreenPreference,
                              final Instruction instruction,
                              final String headerMode,
                              final AmountFormat amountFormat,
                              final boolean loading) {
        this.paymentResult = paymentResult;
        this.paymentResultScreenPreference = paymentResultScreenPreference;
        this.instruction = instruction;
        this.headerMode = headerMode;
        this.amountFormat = amountFormat;
        this.loading = loading;
    }

    public PaymentResultProps(@NonNull final Builder builder) {
        this.paymentResult = builder.paymentResult;
        this.headerMode = builder.headerMode;
        this.paymentResultScreenPreference = builder.paymentResultScreenPreference;
        this.instruction = builder.instruction;
        this.amountFormat = builder.amountFormat;
        this.loading = builder.loading;
    }

    public Builder toBuilder() {
        return new Builder()
                .setPaymentResult(this.paymentResult)
                .setPreference(this.paymentResultScreenPreference)
                .setHeaderMode(this.headerMode)
                .setInstruction(this.instruction)
                .setAmountFormat(this.amountFormat)
                .setLoading(this.loading);
    }

    public boolean hasCustomizedTitle() {
        if (paymentResultScreenPreference != null) {
            if (isApprovedTitleValidState()) {
                return paymentResultScreenPreference.getApprovedTitle() != null && !paymentResultScreenPreference.getApprovedTitle().isEmpty();
            } else if (isPendingTitleValidState()) {
                return paymentResultScreenPreference.getPendingTitle() != null && !paymentResultScreenPreference.getPendingTitle().isEmpty();
            } else if (isRejectedTitleValidState()) {
                return paymentResultScreenPreference.getRejectedTitle() != null && !paymentResultScreenPreference.getRejectedTitle().isEmpty();
            }
        }
        return false;
    }

    public String getPreferenceTitle() {
        if (paymentResultScreenPreference != null) {
            if (isApprovedTitleValidState()) {
                return paymentResultScreenPreference.getApprovedTitle();
            } else if (isPendingTitleValidState()) {
                return paymentResultScreenPreference.getPendingTitle();
            } else if (isRejectedTitleValidState()) {
                return paymentResultScreenPreference.getRejectedTitle();
            }
        }
        return "";
    }

    private boolean isApprovedTitleValidState() {
        return isStatusApproved();
    }

    private boolean isStatusApproved() {
        return paymentResult != null && paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_APPROVED);
    }

    private boolean isStatusRejected() {
        return paymentResult != null && paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_REJECTED);
    }

    private boolean isPendingTitleValidState() {
        return paymentResult != null && ((paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_PENDING)
                && !paymentResult.getPaymentStatusDetail().equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT)) ||
                paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_IN_PROCESS));
    }

    private boolean isRejectedTitleValidState() {
        return isStatusRejected();
    }

    public boolean hasCustomizedLabel() {
        if (paymentResultScreenPreference != null) {
            if (isApprovedLabelValidState()) {
                return paymentResultScreenPreference.getApprovedLabelText() != null && !paymentResultScreenPreference.getApprovedLabelText().isEmpty();
            } else if (isRejectedLabelValidState()) {
                return !paymentResultScreenPreference.isRejectedLabelTextEnabled();
            }
        }
        return false;
    }

    public String getPreferenceLabel() {
        if (paymentResultScreenPreference != null) {
            if (isApprovedLabelValidState()) {
                return paymentResultScreenPreference.getApprovedLabelText();
            } else if (isRejectedLabelValidState() && !paymentResultScreenPreference.isRejectedLabelTextEnabled()) {
                return "";
            }
        }
        return "";
    }

    private boolean isApprovedLabelValidState() {
        return isStatusApproved();
    }

    private boolean isRejectedLabelValidState() {
        return isRejectedTitleValidState();
    }

    public boolean hasCustomizedBadge() {
        if (paymentResultScreenPreference != null) {
            if (isApprovedBadgeValidState()) {
                return paymentResultScreenPreference.getApprovedBadge() != null && !paymentResultScreenPreference.getApprovedBadge().isEmpty();
            }
        }
        return false;
    }

    public String getPreferenceBadge() {
        if (paymentResultScreenPreference != null) {
            if (isApprovedBadgeValidState()) {
                return paymentResultScreenPreference.getApprovedBadge();
            }
        }
        return "";
    }

    private boolean isApprovedBadgeValidState() {
        return isStatusApproved();
    }

    public boolean hasCustomizedIcon() {
        if (paymentResultScreenPreference != null) {
            if (isApprovedIconValidState()) {
                return paymentResultScreenPreference.getApprovedIcon() != null;
            } else if (isPendingIconValidState()) {
                return paymentResultScreenPreference.getPendingIcon() != null;
            } else if (isRejectedIconValidState()) {
                return paymentResultScreenPreference.getRejectedIcon() != null;
            }
        }
        return false;
    }

    public int getPreferenceIcon() {
        if (paymentResultScreenPreference != null) {
            if (isApprovedIconValidState()) {
                return paymentResultScreenPreference.getApprovedIcon();
            } else if (isPendingIconValidState()) {
                return paymentResultScreenPreference.getPendingIcon();
            } else if (isRejectedIconValidState()) {
                return paymentResultScreenPreference.getRejectedIcon();
            }
        }
        return 0;
    }

    private boolean isApprovedIconValidState() {
        return isStatusApproved();
    }

    private boolean isPendingIconValidState() {
        return paymentResult != null && (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_PENDING)
                && paymentResult.getPaymentStatusDetail().equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT));
    }

    private boolean isRejectedIconValidState() {
        return isStatusRejected();
    }

    public boolean hasInstructions() {
        return instruction != null;
    }

    public String getInstructionsTitle() {
        if (hasInstructions()) {
            return instruction.getTitle();
        } else {
            return "";
        }
    }

    public class Builder {

        public PaymentResult paymentResult;
        public PaymentResultScreenPreference paymentResultScreenPreference;
        public Instruction instruction;
        public String headerMode;
        public AmountFormat amountFormat;
        public boolean loading;

        public Builder setPaymentResult(@NonNull final PaymentResult paymentResult) {
            this.paymentResult = paymentResult;
            return this;
        }

        public Builder setHeaderMode(@NonNull final String headerMode) {
            this.headerMode = headerMode;
            return this;
        }

        public Builder setPreference(@NonNull final PaymentResultScreenPreference paymentResultScreenPreference) {
            this.paymentResultScreenPreference = paymentResultScreenPreference;
            return this;
        }

        public Builder setInstruction(@NonNull final Instruction instruction) {
            this.instruction = instruction;
            return this;
        }

        public Builder setAmountFormat(@NonNull final AmountFormat amountFormat) {
            this.amountFormat = amountFormat;
            return this;
        }

        public Builder setLoading(final boolean loading) {
            this.loading = loading;
            return this;
        }

        public PaymentResultProps build() {
            return new PaymentResultProps(this);
        }
    }
}
