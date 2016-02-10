package com.dmbaryshev.vkschool.model.repository;

import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;
import com.dmbaryshev.vkschool.model.network.ApiHelper;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageRepo extends BaseRepo<VkMessage> {

    public Observable<Void> sendMessage(int userId, String messageText) {
        return ApiHelper.createService()
                        .sendMessage(userId, messageText)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResponseAnswer<VkMessage>> getMessages(int userId, int messagesCount) {
        Observable<Response<CommonResponse<VkMessage>>> observable = ApiHelper.createService()
                                                                              .getMessageHistory(
                                                                                      userId,
                                                                                      messagesCount)
                                                                              .subscribeOn(
                                                                                      Schedulers.io())
                                                                              .observeOn(
                                                                                      AndroidSchedulers
                                                                                              .mainThread());
        return getResponseAnswer(observable);
    }

    public Observable<ResponseAnswer<VkMessage>> getMessages(int userId,
                                                             int messagesCount,
                                                             int messageId) {
        Observable<Response<CommonResponse<VkMessage>>> observable = ApiHelper.createService()
                                                                              .getMessageHistory(
                                                                                      userId,
                                                                                      messagesCount,
                                                                                      messageId)
                                                                              .subscribeOn(
                                                                                      Schedulers.io())
                                                                              .observeOn(
                                                                                      AndroidSchedulers
                                                                                              .mainThread());
        return getResponseAnswer(observable);
    }
}
