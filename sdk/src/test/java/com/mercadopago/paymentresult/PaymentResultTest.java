package com.mercadopago.paymentresult;

import com.mercadopago.constants.Sites;
import com.mercadopago.exceptions.MercadoPagoError;
import com.mercadopago.mocks.PaymentMethods;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.Instruction;
import com.mercadopago.model.Instructions;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentData;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.mvp.OnResourcesRetrievedCallback;
import com.mercadopago.paymentresult.model.AmountFormat;
import com.mercadopago.preferences.PaymentResultScreenPreference;

import junit.framework.Assert;

import org.junit.Test;

import java.math.BigDecimal;

public class PaymentResultTest {

    @Test
    public void whenPaymentWithCardApprovedThenShowCongrats() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOnVisa());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus(Payment.StatusCodes.STATUS_APPROVED)
                .setPaymentData(paymentData)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        //TODO fix
//        Assert.assertTrue(mockedView.congratsShown);
    }

    @Test
    public void whenPaymentWithCardRejectedThenShowRejection() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOnVisa());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus(Payment.StatusCodes.STATUS_REJECTED)
                .setPaymentStatusDetail(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_OTHER_REASON)
                .setPaymentData(paymentData)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        //TODO fix
//        Assert.assertTrue(mockedView.rejectionShown);
    }

    @Test
    public void whenCallForAuthNeededThenShowCallForAuthScreen() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOnVisa());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus(Payment.StatusCodes.STATUS_REJECTED)
                .setPaymentStatusDetail(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE)
                .setPaymentData(paymentData)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        //TODO fix
//        Assert.assertTrue(mockedView.callForAuthorizeShown);
    }

    @Test
    public void whenPaymentOffPendingThenShowInstructions() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOff());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus(Payment.StatusCodes.STATUS_PENDING)
                .setPaymentStatusDetail(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT)
                .setPaymentData(paymentData)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        //TODO fix
//        Assert.assertTrue(mockedView.instructionsShown);
    }

    @Test
    public void whenPaymentOnInProcessThenShowPendingScreen() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOnVisa());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus(Payment.StatusCodes.STATUS_IN_PROCESS)
                .setPaymentData(paymentData)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        //TODO fix
//        Assert.assertTrue(mockedView.pendingShown);
    }

    @Test
    public void whenUnknownStatusThenShowError() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOnVisa());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus("UNKNOWN")
                .setPaymentData(paymentData)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        Assert.assertTrue(navigator.errorShown);
    }

    @Test
    public void whenPaymentDataIsNullThenShowError() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOnVisa());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus("UNKNOWN")
                .setPaymentData(null)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        Assert.assertTrue(navigator.errorShown);
    }

    @Test
    public void whenPaymentResultIsNullThenShowError() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        presenter.setPaymentResult(null);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        Assert.assertTrue(navigator.errorShown);
    }

    @Test
    public void whenPaymentResultStatusIsNullThenShowError() {
        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOnVisa());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus(null)
                .setPaymentData(null)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();

        Assert.assertTrue(navigator.errorShown);
    }

    @Test
    public void whenPaymentOffRejectedThenShowRejection() {

        MockedNavigator navigator = new MockedNavigator();
        PaymentResultPresenter presenter = new PaymentResultPresenter(navigator);

        PaymentData paymentData = new PaymentData();
        paymentData.setPaymentMethod(PaymentMethods.getPaymentMethodOff());

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentStatus(Payment.StatusCodes.STATUS_REJECTED)
                .setPaymentData(paymentData)
                .build();

        presenter.setPaymentResult(paymentResult);
        presenter.setAmount(new BigDecimal("100"));
        presenter.setSite(Sites.ARGENTINA);
        presenter.setDiscountEnabled(Boolean.TRUE);

        MockedPropsView mockedView = new MockedPropsView();
        MockedProvider mockedProvider = new MockedProvider();

        presenter.attachView(mockedView);
        presenter.attachResourcesProvider(mockedProvider);

        presenter.initialize();
        //TODO fix
//        Assert.assertTrue(mockedView.rejectionShown);
    }

    private class MockedPropsView implements PaymentResultPropsView {

        @Override
        public void setPropPaymentResult(PaymentResult paymentResult, PaymentResultScreenPreference paymentResultScreenPreference) {

        }

        @Override
        public void setPropInstruction(Instruction instruction, AmountFormat amountFormat) {

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

    private class MockedNavigator implements PaymentResultNavigator {

        private boolean errorShown = false;

        @Override
        public void showApiExceptionError(ApiException exception, String requestOrigin) {
            this.errorShown = true;
        }

        @Override
        public void showError(MercadoPagoError error, String requestOrigin) {
            this.errorShown = true;
        }
    }
}
