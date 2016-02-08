package com.dmbaryshev.vkschool.model.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dmbaryshev.vkschool.App;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.PreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private static final String TAG = ApiHelper.class.getSimpleName();

    private static final int TIMEOUT = 25;

    public static ApiService createService() {
        DLog.i(TAG, "createService: ");

        Retrofit retrofit = getRetrofit();

        return retrofit.create(ApiService.class);
    }

    @NonNull
    public static Retrofit getRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                                                      .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                                                      .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                                                      .addInterceptor(
                                                              addConstatntlyParamsInterceptor(App.getAppContext()))
                                                      .addInterceptor(new HttpLoggingInterceptor().setLevel(
                                                              HttpLoggingInterceptor.Level.BODY))
                                                      .build();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return new Retrofit.Builder().baseUrl(NetworkConstants.BASE_URL)
                                     .client(okHttpClient)
                                     .addConverterFactory(GsonConverterFactory.create(gson))
                                     .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                     .build();
    }

    @NonNull
    public static Interceptor addConstatntlyParamsInterceptor(final Context context) {
        return chain->{
            if (chain.request() == null) { return null; }
            final String token = PreferencesHelper.getInstance(context).getToken();
            if (token == null) { return null; }
            Request request = chain.request();
            HttpUrl url = request.url()
                                 .newBuilder()
                                 .addQueryParameter(NetworkConstants.PARAM_ACCESS_TOKEN, token)
                                 .addQueryParameter(NetworkConstants.PARAM_API_VERSION,
                                                    NetworkConstants.API_VERSION)
                                 .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };
    }
}
