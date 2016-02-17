package com.dmbaryshev.vkschool.model.repository;

import com.dmbaryshev.vkschool.model.dto.VkAudio;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;
import com.dmbaryshev.vkschool.model.network.ApiHelper;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AudioRepo extends BaseRepo<VkAudio> {
    public Observable<ResponseAnswer<VkAudio>> getAudio() {
        Observable<Response<CommonResponse<VkAudio>>> observable = ApiHelper.createService()
                                                                           .getAudio(30)
                                                                           .subscribeOn(Schedulers.io())
                                                                           .observeOn(
                                                                                   AndroidSchedulers
                                                                                           .mainThread());
        return getResponseAnswer(observable);
    }
}
