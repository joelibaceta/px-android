package com.mercadopago.paymentresult.props;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.mercadopago.paymentresult.model.AmountFormat;


/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultHeaderProps {

    public final String height;
    public final int background;
    public final int iconImage;
    public final int badgeImage;
    public final String title;
    public final String label;
    public final AmountFormat amountFormat;

    public PaymentResultHeaderProps(String height, int background, int iconImage,
                                    int badgeImage, String title, String label, AmountFormat formatter) {
        this.height = height;
        this.background = background;
        this.iconImage = iconImage;
        this.badgeImage = badgeImage;
        this.title = title;
        this.label = label;
        this.amountFormat = formatter;
    }

    public PaymentResultHeaderProps(@NonNull final Builder builder) {
        this.height = builder.height;
        this.background = builder.background;
        this.iconImage = builder.iconImage;
        this.badgeImage = builder.badgeImage;
        this.title = builder.title;
        this.label = builder.label;
        this.amountFormat = builder.amountFormat;
    }

    public Builder toBuilder() {
        return new Builder()
                .setHeight(this.height)
                .setBackground(this.background)
                .setIconImage(this.iconImage)
                .setBadgeImage(this.badgeImage)
                .setTitle(this.title)
                .setLabel(this.label)
                .setAmountFormat(this.amountFormat);
    }

    public static class Builder {

        //TODO definir los valores default

        public String height;
        public int background;
        public int iconImage;
        public int badgeImage;
        public String title;
        public String label;
        public AmountFormat amountFormat;

        public Builder setBackground(int background) {
            this.background = background;
            return this;
        }

        public Builder setIconImage(int iconImage) {
            this.iconImage = iconImage;
            return this;
        }

        public Builder setBadgeImage(int badgeImage) {
            this.badgeImage = badgeImage;
            return this;
        }

        public Builder setHeight(String height) {
            this.height = height;
            return this;
        }

        public Builder setTitle(@NonNull final String title) {
            this.title = title;
            return this;
        }

        public Builder setLabel(@NonNull final String label) {
            this.label = label;
            return this;
        }

        public Builder setAmountFormat(AmountFormat amountFormat) {
            this.amountFormat = amountFormat;
            return this;
        }

        public PaymentResultHeaderProps build() {
            return new PaymentResultHeaderProps(this);
        }
    }
}
