package com.dmbaryshev.vkschool.ui.friends.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.dmbaryshev.vkschool.network.ApiHelper;
import com.dmbaryshev.vkschool.network.ApiService;
import com.dmbaryshev.vkschool.network.model.VkUser;
import com.dmbaryshev.vkschool.network.model.VkUserResponse;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

public class FriendsLoader extends AsyncTaskLoader<List<VkUser>> {

    public FriendsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<VkUser> loadInBackground() {
        ApiService apiService = ApiHelper.createService(getContext());
        Call<VkUserResponse> call = apiService.getFriendList("hints",
                                                             "photo_100");

        try {
            Response<VkUserResponse> response = call.execute();
            if (response == null) {return null;}
            VkUserResponse vkUserResponse = response.body();
            if (vkUserResponse == null) {
                return null;
            }
            return vkUserResponse.getVkUsers();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
