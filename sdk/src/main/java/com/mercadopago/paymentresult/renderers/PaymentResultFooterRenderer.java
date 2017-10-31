package com.mercadopago.paymentresult.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;
import com.mercadopago.paymentresult.components.PaymentResultFooterComponent;

/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultFooterRenderer extends Renderer<PaymentResultFooterComponent> {

    @Override
    public View render() {
        final View footerView = LayoutInflater.from(context).inflate(R.layout.mpsdk_payment_result_footer, null);
        final TextView textView = (TextView) footerView.findViewById(R.id.footerText);
        //textView.setText(component.getProps());
        return footerView;
    }
}
