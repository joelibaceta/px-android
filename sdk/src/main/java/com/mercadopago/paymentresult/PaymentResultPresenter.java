package com.mercadopago.paymentresult;

import com.mercadopago.R;
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
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.ErrorUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentResultPresenter extends MvpPresenter<PaymentResultPropsView, PaymentResultProvider> implements ActionsListener {
    private Boolean discountEnabled;
    private PaymentResult paymentResult;
    private Site site;
    private BigDecimal amount;
    private PaymentResultScreenPreference paymentResultScreenPreference;

    public void initialize() {
        try {
            validateParameters();
            onValidStart();
        } catch (IllegalStateException exception) {
            onInvalidStart(exception.getMessage());
        }
    }

    private void validateParameters() {
        if (!isPaymentResultValid()) {
            throw new IllegalStateException("payment result is invalid");
        } else if (!isPaymentMethodValid()) {
            throw new IllegalStateException("payment data is invalid");
        } else if (!isPaymentMethodOffValid()) {
            throw new IllegalStateException("payment id is invalid");
        }
    }

    protected void onValidStart() {
        getView().setPropPaymentResult(paymentResult, paymentResultScreenPreference);
        checkGetInstructions();
    }

    protected void onInvalidStart(String errorDetail) {
        getView().showError(getResourcesProvider().getStandardErrorMessage(), errorDetail);
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

    private boolean isPaymentMethodOffValid() {
        return !isPaymentMethodOff() || paymentResult.getPaymentId() != null;
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

    private void getInstructionsAsync(Long paymentId, String paymentTypeId) {
        getResourcesProvider().getInstructionsAsync(paymentId, paymentTypeId, new OnResourcesRetrievedCallback<Instructions>() {
            @Override
            public void onSuccess(Instructions instructions) {
                List<Instruction> instructionsList
                        = instructions.getInstructions() == null ? new ArrayList<Instruction>() : instructions.getInstructions();
                if (instructionsList.isEmpty()) {
//                    ErrorUtil.startErrorActivity(mActivity, mActivity.getString(R.string.mpsdk_standard_error_message), INSTRUCTIONS_NOT_FOUND_FOR_TYPE + mPaymentTypeId, false, mMerchantPublicKey);
                } else {
                    resolveInstructions(instructionsList);
                }
            }

            @Override
            public void onFailure(MercadoPagoError error) {
                //TODO revisar
//                if (viewAttached()) {
//                    getView().showError(error, ApiUtil.RequestOrigin.GET_INSTRUCTIONS);
//
//                    setFailureRecovery(new FailureRecovery() {
//                        @Override
//                        public void recover() {
//                            getInstructionsAsync();
//                        }
//                    });
//                }
            }
        });
    }

    private void resolveInstructions(List<Instruction> instructionsList) {
        Instruction instruction = getInstruction(instructionsList);
        if (instruction == null) {
//            ErrorUtil.startErrorActivity(this, this.getString(R.string.mpsdk_standard_error_message), "instruction not found for type " + mPaymentTypeId, false, mMerchantPublicKey);
        } else {
            getView().setPropInstruction(instruction);
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
