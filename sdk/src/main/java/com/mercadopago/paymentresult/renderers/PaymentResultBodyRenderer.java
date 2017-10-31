package com.mercadopago.paymentresult.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;
import com.mercadopago.paymentresult.components.PaymentResultBodyComponent;

/**
 * Created by vaserber on 10/23/17.
 */

public class PaymentResultBodyRenderer extends Renderer<PaymentResultBodyComponent> {

    @Override
    public View render() {
        final View bodyView = LayoutInflater.from(context).inflate(R.layout.mpsdk_payment_result_body, null, false);
        final ViewGroup bodyContainer = (FrameLayout) bodyView.findViewById(R.id.mpsdkPaymentResultContainerBody);
        final TextView textView = (TextView) bodyView.findViewById(R.id.bodyText);
        stretchHeight(bodyContainer);
        textView.setText(component.getProps().status);
        return bodyView;
    }

}
