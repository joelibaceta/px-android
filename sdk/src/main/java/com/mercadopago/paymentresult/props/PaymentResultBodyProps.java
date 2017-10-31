package com.mercadopago.paymentresult.props;

import android.support.annotation.NonNull;

/**
 * Created by vaserber on 10/23/17.
 */

public class PaymentResultBodyProps {

    public final String status;
    public final String statusDetail;

    public PaymentResultBodyProps(String status, String statusDetail) {
        this.status = status;
        this.statusDetail = statusDetail;
    }

    public PaymentResultBodyProps(@NonNull final Builder builder) {
        this.status = builder.status;
        this.statusDetail = builder.statusDetail;
    }

    public Builder toBuilder() {
        return new Builder()
                .setStatus(this.status)
                .setStatusDetail(this.statusDetail);
    }

    public class Builder {

        public String status;
        public String statusDetail;

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setStatusDetail(String statusDetail) {
            this.statusDetail = statusDetail;
            return this;
        }

        public PaymentResultBodyProps build() {
            return new PaymentResultBodyProps(this);
        }
    }
}
