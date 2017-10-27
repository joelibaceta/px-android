package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;

import com.mercadopago.callbacks.FailureRecovery;
import com.mercadopago.components.Action;
import com.mercadopago.components.ActionsListener;
import com.mercadopago.exceptions.MercadoPagoError;
import com.mercadopago.model.Instruction;
import com.mercadopago.model.Instructions;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.model.Site;
import com.mercadopago.mvp.MvpPresenter;
import com.mercadopago.mvp.OnResourcesRetrievedCallback;
import com.mercadopago.paymentresult.model.AmountFormat;
import com.mercadopago.preferences.PaymentResultScreenPreference;
import com.mercadopago.util.ApiUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentResultPresenter extends MvpPresenter<PaymentResultPropsView, PaymentResultProvider> implements ActionsListener {
    private Boolean discountEnabled;
    private PaymentResult paymentResult;
    private Site site;
    private BigDecimal amount;
    private PaymentResultScreenPreference paymentResultScreenPreference;
    private PaymentResultNavigator navigator;
    private FailureRecovery failureRecovery;

    public PaymentResultPresenter(@NonNull final PaymentResultNavigator navigator) {
        this.navigator = navigator;
    }

    public void initialize() {
        try {
            validateParameters();
            onValidStart();
        } catch (IllegalStateException exception) {
            navigator.showError(new MercadoPagoError(exception.getMessage(), false), "");
        }
    }

    private void validateParameters() {
        if (!isPaymentResultValid()) {
            throw new IllegalStateException("payment result is invalid");
        } else if (!isPaymentMethodValid()) {
            throw new IllegalStateException("payment data is invalid");
        } else if (isPaymentMethodOff()) {
            if (!isPaymentIdValid()) {
                throw new IllegalStateException("payment id is invalid");
            } else if (!isSiteValid()) {
                throw new IllegalStateException("site is invalid");
            }
        }
    }

    protected void onValidStart() {
        getView().setPropPaymentResult(paymentResult, paymentResultScreenPreference);
        checkGetInstructions();
    }

    private boolean isPaymentResultValid() {
        return paymentResult != null && paymentResult.getPaymentStatus() != null && paymentResult.getPaymentStatusDetail() != null;
    }

    private boolean isPaymentMethodValid() {
        return paymentResult != null && paymentResult.getPaymentData() != null && paymentResult.getPaymentData().getPaymentMethod() != null &&
                paymentResult.getPaymentData().getPaymentMethod().getId() != null && !paymentResult.getPaymentData().getPaymentMethod().getId().isEmpty() &&
                paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId() != null && !paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId().isEmpty() &&
                paymentResult.getPaymentData().getPaymentMethod().getName() != null && !paymentResult.getPaymentData().getPaymentMethod().getName().isEmpty();
    }

    private boolean isPaymentIdValid() {
        return paymentResult.getPaymentId() != null;
    }

    private boolean isSiteValid() {
        return site != null && site.getCurrencyId() != null && !site.getCurrencyId().isEmpty();
    }

    private boolean isPaymentMethodOff() {
        String paymentStatus = paymentResult.getPaymentStatus();
        String paymentStatusDetail = paymentResult.getPaymentStatusDetail();
        return paymentStatus.equals(Payment.StatusCodes.STATUS_PENDING) && paymentStatusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT);
    }

    public void setDiscountEnabled(Boolean discountEnabled) {
        this.discountEnabled = discountEnabled;
    }

    public void setPaymentResult(PaymentResult paymentResult) {
        this.paymentResult = paymentResult;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentResultScreenPreference(PaymentResultScreenPreference paymentResultScreenPreference) {
        this.paymentResultScreenPreference = paymentResultScreenPreference;
    }

    private void checkGetInstructions() {
        if (isPaymentMethodOff()) {
            getInstructionsAsync(paymentResult.getPaymentId(), paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId());
        }
    }

    private void getInstructionsAsync(final Long paymentId, final String paymentTypeId) {
        getResourcesProvider().getInstructionsAsync(paymentId, paymentTypeId, new OnResourcesRetrievedCallback<Instructions>() {
            @Override
            public void onSuccess(Instructions instructions) {
                List<Instruction> instructionsList
                        = instructions.getInstructions() == null ? new ArrayList<Instruction>() : instructions.getInstructions();
                if (instructionsList.isEmpty()) {
                    navigator.showError(new MercadoPagoError(getResourcesProvider().getStandardErrorMessage(), false), ApiUtil.RequestOrigin.GET_INSTRUCTIONS);
                } else {
                    resolveInstructions(instructionsList);
                }
            }

            @Override
            public void onFailure(MercadoPagoError error) {
                //TODO revisar
                if (navigator != null) {
                    navigator.showError(error, ApiUtil.RequestOrigin.GET_INSTRUCTIONS);
                    setFailureRecovery(new FailureRecovery() {
                        @Override
                        public void recover() {
                            getInstructionsAsync(paymentId, paymentTypeId);
                        }
                    });
                }
            }
        });
    }

    public void recoverFromFailure() {
        if (failureRecovery != null) {
            failureRecovery.recover();
        }
    }

    private void setFailureRecovery(FailureRecovery failureRecovery) {
        this.failureRecovery = failureRecovery;
    }

    private void resolveInstructions(List<Instruction> instructionsList) {
        Instruction instruction = getInstruction(instructionsList);
        if (instruction == null) {
            navigator.showError(new MercadoPagoError(getResourcesProvider().getStandardErrorMessage(), false), ApiUtil.RequestOrigin.GET_INSTRUCTIONS);
        } else {
            getView().setPropInstruction(instruction, new AmountFormat(site.getCurrencyId(), amount));
        }
//        stopLoading();
    }

    private Instruction getInstruction(List<Instruction> instructions) {
        Instruction instruction;
        if (instructions.size() == 1) {
            instruction = instructions.get(0);
        } else {
            instruction = getInstructionForType(instructions, paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId());
        }
        return instruction;
    }

    private Instruction getInstructionForType(List<Instruction> instructions, String paymentTypeId) {
        Instruction instructionForType = null;
        for (Instruction instruction : instructions) {
            if (instruction.getType().equals(paymentTypeId)) {
                instructionForType = instruction;
                break;
            }
        }
        return instructionForType;
    }

    @Override
    public void onAction(Action action) {

    }

//    private void onValidStart() {
//        if (paymentResult.getPaymentStatusDetail() != null && paymentResult.getPaymentStatusDetail().equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT)) {
////            getView().showInstructions(site, amount, paymentResult);
//        } else if (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_IN_PROCESS) ||
//                paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_PENDING)) {
////            getView().showPending(paymentResult);
//        } else if (isCardOrAccountMoney()) {
//            startPaymentsOnResult();
//        } else if (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_REJECTED)) {
////            getView().showRejection(paymentResult);
//        }
//    }

//    private boolean isCardOrAccountMoney() {
//        return MercadoPagoUtil.isCard(paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId()) ||
//                paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId().equals(PaymentTypes.ACCOUNT_MONEY);
//    }
//
//    private void startPaymentsOnResult() {
//        if (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_APPROVED)) {
////            getView().showCongrats(site, amount, paymentResult, discountEnabled);
//        } else if (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_REJECTED)) {
//            if (isStatusDetailValid() && paymentResult.getPaymentStatusDetail().equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE)) {
////                getView().showCallForAuthorize(site, paymentResult);
//            } else {
////                getView().showRejection(paymentResult);
//            }
//        } else {
//            getView().showError(getResourcesProvider().getStandardErrorMessage());
//        }
//    }

}
