package com.dmbaryshev.vkschool.network;

import com.dmbaryshev.vkschool.network.model.VkMessage;
import com.dmbaryshev.vkschool.network.model.VkUser;
import com.dmbaryshev.vkschool.network.model.common.CommonResponse;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ApiService {
    @GET ("/method/friends.get")
    Call<CommonResponse<VkUser>> getFriendList(@Query ("user_id") int userId,
                                               @Query ("order") String order,
                                               @Query ("fields") String fields);

    @GET ("/method/friends.get")
    Call<CommonResponse<VkUser>> getFriendList(@Query ("order") String order,
                                               @Query ("fields") String fields);

    @GET ("/method/messages.getHistory")
    Call<CommonResponse<VkMessage>> getMessageHistory(@Query ("offset") int offset,
                                                      @Query ("count") int count,
                                                      @Query ("user_id") int userId,
                                                      @Query ("start_message_id ") int startMessageId);

    @GET ("/method/messages.getHistory")
    Call<CommonResponse<VkMessage>> getMessageHistory(@Query ("count") int count,
                                                      @Query ("user_id") int userId,
                                                      @Query ("start_message_id ") int startMessageId);

    @GET ("/method/messages.getHistory")
    Call<CommonResponse<VkMessage>> getMessageHistory(@Query ("count") int count,
                                                      @Query ("user_id") int userId);

    @POST ("/method/messages.send")
    Call<ResponseBody> sendMessage(@Query ("user_id") int userId,
                                   @Query ("message") String message);
}
