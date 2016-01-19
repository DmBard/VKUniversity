package com.dmbaryshev.vkschool.network;

import android.content.Context;

import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.PreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ApiHelper {
    private static final String TAG = ApiHelper.class.getSimpleName();

    private static final int TIMEOUT = 25;

    public static ApiService createService(final Context context) {
        DLog.i(TAG, "createService: ");

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return addConstatnlyParameters(chain, context);
            }
        });
        addLogging(okHttpClient);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(NetworkConstants.BASE_URL)
                                                  .client(okHttpClient)
                                                  .addConverterFactory(GsonConverterFactory.create(
                                                          gson))
                                                  .build();

        return retrofit.create(ApiService.class);
    }

    private static Response addConstatnlyParameters(Interceptor.Chain chain, Context context) throws
                                                                                              IOException {
        if (chain.request() == null) { return null; }
        final String token = PreferencesHelper.getInstance(context).getToken();
        if (token == null) { return null; }
        Request request = chain.request();
        HttpUrl url = request.httpUrl()
                             .newBuilder()
                             .addQueryParameter(NetworkConstants.PARAM_ACCESS_TOKEN, token)
                             .addQueryParameter(NetworkConstants.PARAM_API_VERSION,
                                                NetworkConstants.API_VERSION)
                             .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }

    private static void addLogging(OkHttpClient okHttpClient) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.interceptors().add(logging);
    }
}
