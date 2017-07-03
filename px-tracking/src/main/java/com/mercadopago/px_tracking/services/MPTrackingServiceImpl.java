package com.mercadopago.px_tracking.services;

import android.content.Context;
import android.util.Log;

import com.mercadopago.px_tracking.model.EventTrackIntent;
import com.mercadopago.px_tracking.model.PaymentIntent;
import com.mercadopago.px_tracking.model.TrackingIntent;
import com.mercadopago.px_tracking.utils.HttpClientUtil;
import com.mercadopago.px_tracking.utils.JsonConverter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vaserber on 6/5/17.
 */

public class MPTrackingServiceImpl implements MPTrackingService {

    private static final String BASE_URL = "https://api.mercadopago.com/";

    private Retrofit getRetrofit(Context context) {
        return new Retrofit.Builder()
                .client(HttpClientUtil.getClient(context))
                .addConverterFactory(GsonConverterFactory.create(JsonConverter.getInstance().getGson()))
                .baseUrl(BASE_URL)
                .build();
    }

    @Override
    public void trackToken(TrackingIntent trackingIntent, Context context) {

        Retrofit retrofit = getRetrofit(context);
        TrackingService service = retrofit.create(TrackingService.class);

        Call<Void> call = service.trackToken(trackingIntent);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 400) {
                    Log.e("Failure","Error 400, parameter invalid");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Failure","Service failure");
            }
        });
    }

    @Override
    public void trackPaymentId(PaymentIntent paymentIntent, Context context) {

        Retrofit retrofit = getRetrofit(context);
        TrackingService service = retrofit.create(TrackingService.class);

        Call<Void> call = service.trackPaymentId(paymentIntent);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 400) {
                    Log.e("Failure","Error 400, parameter invalid");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Failure","Service failure");
            }
        });
    }

    @Override
    public void trackEvent(EventTrackIntent eventTrackIntent, Context context) {
        Retrofit retrofit = getRetrofit(context);
        TrackingService service = retrofit.create(TrackingService.class);

        Call<Void> call = service.trackEvents(eventTrackIntent);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 400) {
                    Log.e("Failure","Error 400, parameter invalid");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Failure","Service failure");
            }
        });
    }
}
