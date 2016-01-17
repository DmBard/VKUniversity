package com.dmbaryshev.vkschool.network;

import com.dmbaryshev.vkschool.network.model.VkUserResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    @GET ("/method/friends.get")
    Call<VkUserResponse> getFriendList(@Query ("user_id") int userId,
                                       @Query ("order") String order,
                                       @Query ("fields") String fields);

    @GET ("/method/friends.get")
    Call<VkUserResponse> getFriendList(@Query ("order") String order,
                                       @Query ("fields") String fields);
}
