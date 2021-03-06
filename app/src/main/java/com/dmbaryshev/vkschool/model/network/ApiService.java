package com.dmbaryshev.vkschool.model.network;

import com.dmbaryshev.vkschool.model.dto.VkAudio;
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
    Observable<Response<CommonResponse<VkMessage>>> getMessageHistory(@Query ("user_id") int userId,
                                                                      @Query ("offset") int offset,
                                                                      @Query ("count") int count,
                                                                      @Query ("start_message_id") int startMessageId);

    @GET ("/method/messages.getHistory")
    Observable<Response<CommonResponse<VkMessage>>> getMessageHistory(@Query ("user_id") int userId,
                                                                      @Query ("count") int count,
                                                                      @Query ("offset") int offset);

    @GET ("/method/messages.getHistory")
    Observable<Response<CommonResponse<VkMessage>>> getMessageHistory(@Query ("user_id") int userId,
                                                                      @Query ("count") int count);

    @POST ("/method/messages.send")
    Observable<Void> sendMessage(@Query ("user_id") int userId, @Query ("message") String message);

    @POST ("/method/audio.add")
    Observable<Void> addAudio(@Query ("audio_id") int audioId, @Query ("owner_id") int ownerId);

    @GET ("/method/audio.get")
    Observable<Response<CommonResponse<VkAudio>>> getAudio(@Query ("count") int count, @Query ("offset") int offset);

    @GET ("/method/audio.getRecommendations")
    Observable<Response<CommonResponse<VkAudio>>> getAudioRecommendation(@Query ("target_audio") String targetAudio, @Query ("count") int count, @Query ("offset") int offset);


}
