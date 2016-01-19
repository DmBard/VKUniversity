package com.dmbaryshev.vkschool.ui.common.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.dmbaryshev.vkschool.network.model.common.CommonResponse;
import com.dmbaryshev.vkschool.utils.DLog;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

public class VkLoader<T> extends AsyncTaskLoader<List<T>> {
    private static final String TAG = DLog.makeLogTag(VkLoader.class);
    private Call<CommonResponse<T>> mCall;

    public VkLoader(Context context, Call<CommonResponse<T>> call) {
        super(context);
        mCall = call;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<T> loadInBackground() {
        try {
            Response<CommonResponse<T>> response = mCall.execute();
            if (response == null) {return null;}
            CommonResponse<T> userCommonResponse = response.body();
            if (userCommonResponse == null) {
                return null;
            }
            return userCommonResponse.getVkResponse().getItems();
        } catch (IOException e) {
            DLog.e(TAG, "loadInBackground: ", e);
            return null;
        }
    }
}
