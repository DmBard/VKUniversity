package com.dmbaryshev.vkschool.model.network;

import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.model.dto.VkUser;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    @GET ("/method/friends.get")
    Observable<Response<CommonResponse<VkUser>>> getFriendList(@Query ("user_id") int userId,
                                                               @Query ("order") String order,
                                                               @Query ("fields") String fields);

    @GET ("/method/friends.get")
    Observable<Response<CommonResponse<VkUser>>> getFriendList(@Query ("order") String order,
                                                               @Query ("fields") String fields);

    @GET ("/method/messages.getHistory")
    Observable<Response<CommonResponse<VkMessage>>> getMessageHistory(@Query ("offset") int offset,
                                                                      @Query ("count") int count,
                                                                      @Query ("user_id") int userId,
                                                                      @Query ("start_message_id ") int startMessageId);

    @GET ("/method/messages.getHistory")
    Observable<Response<CommonResponse<VkMessage>>> getMessageHistory(@Query ("count") int count,
                                                                      @Query ("user_id") int userId,
                                                                      @Query ("start_message_id ") int startMessageId);

    @GET ("/method/messages.getHistory")
    Observable<Response<CommonResponse<VkMessage>>> getMessageHistory(@Query ("count") int count,
                                                                      @Query ("user_id") int userId);

    @POST ("/method/messages.send")
    Observable<Void> sendMessage(@Query ("user_id") int userId, @Query ("message") String message);
}
