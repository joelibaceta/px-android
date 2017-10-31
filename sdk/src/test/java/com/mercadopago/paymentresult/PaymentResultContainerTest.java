package com.mercadopago.paymentresult;

import com.mercadopago.components.Action;
import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.mocks.PaymentMethods;
import com.mercadopago.model.Instruction;
import com.mercadopago.model.Instructions;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentData;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.mvp.OnResourcesRetrievedCallback;
import com.mercadopago.paymentresult.components.PaymentResultContainer;
import com.mercadopago.paymentresult.model.AmountFormat;
import com.mercadopago.paymentresult.props.PaymentResultProps;
import com.mercadopago.util.CurrenciesUtil;

import junit.framework.Assert;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by vaserber on 10/31/17.
 */

public class PaymentResultContainerTest {

    @Test
    public void whenPaymentResultStatusIsApprovedThenGetBackgroundColorGreen() {
        MockedActionDispatcher dispatcher = new MockedActionDispatcher();
        MockedProvider provider = new MockedProvider();
        PaymentResultContainer container = new PaymentResultContainer(dispatcher, provider);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOff());

        Instruction instruction = com.mercadopago.mocks.Instructions.getInstructionForBoleto();

        AmountFormat amountFormat = new AmountFormat(CurrenciesUtil.CURRENCY_ARGENTINA, new BigDecimal(100));

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus(Payment.StatusCodes.STATUS_APPROVED)
                .setPaymentStatusDetail(Payment.StatusCodes.STATUS_DETAIL_ACCREDITED)
                .setPaymentData(paymentData)
                .build();
        PaymentResultProps paymentResultProps =  new PaymentResultProps(paymentResult, null,
        instruction, "wrap", amountFormat, false);

        container.applyProps(paymentResultProps);

        Assert.assertEquals(container.getBackground(paymentResult), PaymentResultContainer.GREEN_BACKGROUND_COLOR);
    }

    private class MockedActionDispatcher implements ActionDispatcher {

        @Override
        public void dispatch(Action action) {

        }
    }

    private class MockedProvider implements PaymentResultProvider {

        private String STANDARD_ERROR_MESSAGE = "Algo salió mal";

        @Override
        public void getInstructionsAsync(Long paymentId, String paymentTypeId, OnResourcesRetrievedCallback<Instructions> onResourcesRetrievedCallback) {

        }

        @Override
        public String getStandardErrorMessage() {
            return STANDARD_ERROR_MESSAGE;
        }

        @Override
        public String getApprovedTitle() {
            return null;
        }

        @Override
        public String getPendingTitle() {
            return null;
        }

        @Override
        public String getRejectedOtherReasonTitle(String paymentMethodName) {
            return null;
        }

        @Override
        public String getRejectedInsufficientAmountTitle(String paymentMethodName) {
            return null;
        }

        @Override
        public String getRejectedDuplicatedPaymentTitle(String paymentMethodName) {
            return null;
        }

        @Override
        public String getRejectedCardDisabledTitle(String paymentMethodName) {
            return null;
        }

        @Override
        public String getRejectedBadFilledCardTitle(String paymentMethodName) {
            return null;
        }

        @Override
        public String getRejectedHighRiskTitle() {
            return null;
        }

        @Override
        public String getRejectedMaxAttemptsTitle() {
            return null;
        }

        @Override
        public String getRejectedInsufficientDataTitle() {
            return null;
        }

        @Override
        public String getRejectedBadFilledOther() {
            return null;
        }

        @Override
        public String getRejectedCallForAuthorizeTitle() {
            return null;
        }

        @Override
        public String getEmptyText() {
            return null;
        }

        @Override
        public String getPendingLabel() {
            return null;
        }

        @Override
        public String getRejectionLabel() {
            return null;
        }
    }
}
