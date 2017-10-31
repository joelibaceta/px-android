package com.mercadopago.paymentresult.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;
import com.mercadopago.components.RendererFactory;
import com.mercadopago.paymentresult.components.PaymentResultContainer;

/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultRenderer extends Renderer<PaymentResultContainer> {

    @Override
    public View render() {
        View containerView;
        if (component.getProps().loading) {
            containerView = LayoutInflater.from(context).inflate(R.layout.mpsdk_loading_spinner, null, false);
        } else {
            containerView = LayoutInflater.from(context).inflate(R.layout.mpsdk_payment_result_container, null, false);
            final ViewGroup parentViewGroup = (ViewGroup) containerView.findViewById(R.id.mpsdkPaymentResultContainer);

            final Renderer headerRenderer = RendererFactory.create(context, component.headerComponent);
            final View header = headerRenderer.render();
            parentViewGroup.addView(header);

            final Renderer bodyRenderer = RendererFactory.create(context, component.bodyComponent);
            final View body = bodyRenderer.render();
            parentViewGroup.addView(body);

            final Renderer footerRenderer = RendererFactory.create(context, component.footerComponent);
            final View footer = footerRenderer.render();
            parentViewGroup.addView(footer);
        }
        return containerView;
    }
}
