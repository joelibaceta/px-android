package com.mercadopago.paymentresult.components;

import android.support.annotation.NonNull;

import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;
import com.mercadopago.paymentresult.props.IconProps;
import com.mercadopago.paymentresult.props.HeaderProps;

/**
 * Created by vaserber on 10/20/17.
 */

public class HeaderComponent extends Component<HeaderProps> {

    private IconComponent iconComponent;

    public HeaderComponent(@NonNull final HeaderProps props,
                           @NonNull final ActionDispatcher dispatcher) {
        super(props, dispatcher);
    }

    @Override
    public void applyProps(@NonNull final HeaderProps props) {
        IconProps iconProps = new IconProps.Builder()
                .setIconImage(props.iconImage)
                .setBadgeImage(props.badgeImage)
                .build();
        this.iconComponent = new IconComponent(iconProps, getDispatcher());
    }

    public IconComponent getIconComponent() {
        return iconComponent;
    }
}