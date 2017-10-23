package com.mercadopago.paymentresult.props;

import android.support.annotation.NonNull;

/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultHeaderProps {

    public final String status;
    public final String height;
    public final Integer iconProductId;
    public final Integer iconBadgeId;

    public PaymentResultHeaderProps(String status, String height, Integer iconProductId, Integer iconBadgeId) {
        this.status = status;
        this.height = height;
        this.iconProductId = iconProductId;
        this.iconBadgeId = iconBadgeId;
    }

    public PaymentResultHeaderProps(@NonNull final Builder builder) {
        this.status = builder.status;
        this.height = builder.height;
        this.iconProductId = builder.iconProductId;
        this.iconBadgeId = builder.iconBadgeId;
    }

    public Builder toBuilder() {
        return new Builder()
                .setStatus(this.status)
                .setHeight(this.height)
                .setIconProductId(this.iconProductId)
                .setIconBadgeId(this.iconBadgeId);
    }

    public class Builder {

        public String status;
        public String height;
        public Integer iconProductId;
        public Integer iconBadgeId;

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setHeight(String height) {
            this.height = height;
            return this;
        }

        public Builder setIconProductId(Integer iconProductId) {
            this.iconProductId = iconProductId;
            return this;
        }

        public Builder setIconBadgeId(Integer iconBadgeId) {
            this.iconBadgeId = iconBadgeId;
            return this;
        }

        public PaymentResultHeaderProps build() {
            return new PaymentResultHeaderProps(this);
        }
    }
}
