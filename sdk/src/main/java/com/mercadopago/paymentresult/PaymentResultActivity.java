package com.mercadopago.paymentresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mercadopago.components.Component;
import com.mercadopago.components.ComponentManager;
import com.mercadopago.components.RendererFactory;
import com.mercadopago.core.MercadoPagoCheckout;
import com.mercadopago.core.MercadoPagoComponents;
import com.mercadopago.exceptions.MercadoPagoError;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.model.Site;
import com.mercadopago.paymentresult.components.IconComponent;
import com.mercadopago.paymentresult.components.PaymentResultBodyComponent;
import com.mercadopago.paymentresult.components.PaymentResultContainer;
import com.mercadopago.paymentresult.components.PaymentResultFooterComponent;
import com.mercadopago.paymentresult.components.PaymentResultHeaderComponent;
import com.mercadopago.paymentresult.renderers.IconRenderer;
import com.mercadopago.paymentresult.renderers.PaymentResultBodyRenderer;
import com.mercadopago.paymentresult.renderers.PaymentResultFooterRenderer;
import com.mercadopago.paymentresult.renderers.PaymentResultHeaderRenderer;
import com.mercadopago.paymentresult.renderers.PaymentResultRenderer;
import com.mercadopago.preferences.PaymentResultScreenPreference;
import com.mercadopago.preferences.ServicePreference;
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.ErrorUtil;
import com.mercadopago.util.JsonUtil;

import java.math.BigDecimal;

public class PaymentResultActivity extends AppCompatActivity implements PaymentResultNavigator {

    public static final String PAYER_ACCESS_TOKEN_BUNDLE = "merchantPublicKey";
    public static final String MERCHANT_PUBLIC_KEY_BUNDLE = "payerAccessToken";

    public static final String CONGRATS_DISPLAY_BUNDLE = "congratsDisplay";
    public static final String PAYMENT_RESULT_SCREEN_PREFERENCE_BUNDLE = "paymentResultScreenPreference";
    public static final String SERVICE_PREFERENCE_BUNDLE = "servicePreference";

    public static final String PRESENTER_BUNDLE = "presenter";

    private PaymentResultPresenter presenter;

    private String merchantPublicKey;
    private String payerAccessToken;
    private Integer congratsDisplay;
    private PaymentResultScreenPreference paymentResultScreenPreference;
    private ServicePreference servicePreference;

    private ComponentManager componentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PaymentResultPropsMutator mutator = new PaymentResultPropsMutator();

        presenter = new PaymentResultPresenter(this);
        getActivityParameters();

        PaymentResultProvider provider = new PaymentResultProviderImpl(this, merchantPublicKey, payerAccessToken);
        presenter.attachView(mutator);
        presenter.attachResourcesProvider(provider);


        componentManager = new ComponentManager(this);

        RendererFactory.register(PaymentResultContainer.class, PaymentResultRenderer.class);
        RendererFactory.register(PaymentResultHeaderComponent.class, PaymentResultHeaderRenderer.class);
        RendererFactory.register(PaymentResultBodyComponent.class, PaymentResultBodyRenderer.class);
        RendererFactory.register(PaymentResultFooterComponent.class, PaymentResultFooterRenderer.class);
        RendererFactory.register(IconComponent.class, IconRenderer.class);

        final Component root = new PaymentResultContainer(componentManager, provider);
        componentManager.setActionsListener(presenter);
        componentManager.setComponent(root);
        componentManager.setMutator(mutator);

        presenter.initialize();
    }

    @Override
    public void showApiExceptionError(ApiException exception, String requestOrigin) {
        ApiUtil.showApiExceptionError(this, exception, merchantPublicKey, requestOrigin);
    }

    @Override
    public void showError(MercadoPagoError error, String requestOrigin) {
        if (error != null && error.isApiException()) {
            showApiExceptionError(error.getApiException(), requestOrigin);
        } else {
            ErrorUtil.startErrorActivity(this, error, merchantPublicKey);
        }
    }

    //
//    @Override
//    public void showError(String errorMessage) {
//        ErrorUtil.startErrorActivity(this, getString(R.string.mpsdk_standard_error_message), false, merchantPublicKey);
//    }
//
//    @Override
//    public void showError(String errorMessage, String errorDetail) {
//        ErrorUtil.startErrorActivity(this, errorMessage, errorDetail, false, merchantPublicKey);
//    }


//    @Override
//    public void showCongrats(Site site, BigDecimal amount, PaymentResult paymentResult, Boolean discountEnabled) {
//        new MercadoPagoComponents.Activities.CongratsActivityBuilder()
//                .setMerchantPublicKey(merchantPublicKey)
//                .setActivity(this)
//                .setCongratsDisplay(congratsDisplay)
//                .setServicePreference(servicePreference)
//                .setPaymentResultScreenPreference(paymentResultScreenPreference)
//                .setSite(site)
//                .setAmount(amount)
//                .setDiscountEnabled(discountEnabled)
//                .setPaymentResult(paymentResult)
//                .startActivity();
//    }
//
//    @Override
//    public void showCallForAuthorize(Site site, PaymentResult paymentResult) {
//        new MercadoPagoComponents.Activities.CallForAuthorizeActivityBuilder()
//                .setMerchantPublicKey(merchantPublicKey)
//                .setActivity(this)
//                .setPaymentResultScreenPreference(paymentResultScreenPreference)
//                .setPaymentResult(paymentResult)
//                .setSite(site)
//                .startActivity();
//    }
//
//    @Override
//    public void showRejection(PaymentResult paymentResult) {
//        new MercadoPagoComponents.Activities.RejectionActivityBuilder()
//                .setMerchantPublicKey(merchantPublicKey)
//                .setActivity(this)
//                .setPaymentResultScreenPreference(paymentResultScreenPreference)
//                .setPaymentResult(paymentResult)
//                .startActivity();
//    }
//
//    @Override
//    public void showPending(PaymentResult paymentResult) {
//        new MercadoPagoComponents.Activities.PendingActivityBuilder()
//                .setMerchantPublicKey(merchantPublicKey)
//                .setActivity(this)
//                .setPaymentResultScreenPreference(paymentResultScreenPreference)
//                .setPaymentResult(paymentResult)
//                .startActivity();
//    }
//
//    @Override
//    public void showInstructions(Site site, BigDecimal amount, PaymentResult paymentResult) {
//        new MercadoPagoComponents.Activities.InstructionsActivityBuilder()
//                .setMerchantPublicKey(merchantPublicKey)
//                .setPayerAccessToken(payerAccessToken)
//                .setActivity(this)
//                .setServicePreference(servicePreference)
//                .setPaymentResultScreenPreference(paymentResultScreenPreference)
//                .setSite(site)
//                .setAmount(amount)
//                .setPaymentResult(paymentResult)
//                .startActivity();
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PRESENTER_BUNDLE, JsonUtil.getInstance().toJson(presenter));

        outState.putString(MERCHANT_PUBLIC_KEY_BUNDLE, merchantPublicKey);
        outState.putString(PAYER_ACCESS_TOKEN_BUNDLE, payerAccessToken);

        outState.putInt(CONGRATS_DISPLAY_BUNDLE, congratsDisplay);
        outState.putString(PAYMENT_RESULT_SCREEN_PREFERENCE_BUNDLE, JsonUtil.getInstance().toJson(paymentResultScreenPreference));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        presenter = JsonUtil.getInstance().fromJson(savedInstanceState.getString(PRESENTER_BUNDLE), PaymentResultPresenter.class);

        merchantPublicKey = savedInstanceState.getString(MERCHANT_PUBLIC_KEY_BUNDLE);
        payerAccessToken = savedInstanceState.getString(PAYER_ACCESS_TOKEN_BUNDLE);

        congratsDisplay = savedInstanceState.getInt(CONGRATS_DISPLAY_BUNDLE, -1);
        servicePreference = JsonUtil.getInstance().fromJson(savedInstanceState.getString(SERVICE_PREFERENCE_BUNDLE), ServicePreference.class);

        paymentResultScreenPreference = JsonUtil.getInstance().fromJson(savedInstanceState.getString(PAYMENT_RESULT_SCREEN_PREFERENCE_BUNDLE), PaymentResultScreenPreference.class);
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void getActivityParameters() {

        Boolean discountEnabled = getIntent().getExtras().getBoolean("discountEnabled", true);
        Site site = JsonUtil.getInstance().fromJson(getIntent().getExtras().getString("site"), Site.class);
        BigDecimal amount = null;
        if (getIntent().getStringExtra("amount") != null) {
            amount = new BigDecimal(getIntent().getStringExtra("amount"));
        }
        PaymentResult paymentResult = JsonUtil.getInstance().fromJson(getIntent().getExtras().getString("paymentResult"), PaymentResult.class);

        presenter.setDiscountEnabled(discountEnabled);
        presenter.setSite(site);
        presenter.setAmount(amount);
        presenter.setPaymentResult(paymentResult);

        merchantPublicKey = getIntent().getStringExtra("merchantPublicKey");
        payerAccessToken = getIntent().getStringExtra("payerAccessToken");
        congratsDisplay = getIntent().getIntExtra("congratsDisplay", -1);
        paymentResultScreenPreference = JsonUtil.getInstance().fromJson(getIntent().getExtras().getString("paymentResultScreenPreference"), PaymentResultScreenPreference.class);
        servicePreference = JsonUtil.getInstance().fromJson(getIntent().getExtras().getString("servicePreference"), ServicePreference.class);

        presenter.setPaymentResultScreenPreference(paymentResultScreenPreference);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == MercadoPagoCheckout.TIMER_FINISHED_RESULT_CODE) {
            resolveTimerObserverResult(resultCode);
        } else if (requestCode == MercadoPagoComponents.Activities.CONGRATS_REQUEST_CODE) {
            finishWithOkResult(resultCode, data);
        } else if (requestCode == MercadoPagoComponents.Activities.PENDING_REQUEST_CODE) {
            resolveRequest(resultCode, data);
        } else if (requestCode == MercadoPagoComponents.Activities.REJECTION_REQUEST_CODE) {
            resolveRequest(resultCode, data);
        } else if (requestCode == MercadoPagoComponents.Activities.CALL_FOR_AUTHORIZE_REQUEST_CODE) {
            resolveRequest(resultCode, data);
        } else if (requestCode == MercadoPagoComponents.Activities.INSTRUCTIONS_REQUEST_CODE) {
            finishWithOkResult(resultCode, data);
        } else {
            finishWithCancelResult(data);
        }
    }

    private void resolveTimerObserverResult(int resultCode) {
        setResult(resultCode);
        finish();
    }

    private void resolveRequest(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED && data != null) {
            finishWithCancelResult(data);
        } else {
            finishWithOkResult(resultCode, data);
        }
    }

    private void finishWithCancelResult(Intent data) {
        setResult(RESULT_CANCELED, data);
        finish();
    }

    private void finishWithOkResult(int resultCode, Intent data) {
        setResult(resultCode, data);
        finish();
    }
}
