package com.dmbaryshev.vkschool.model.repository;

import com.dmbaryshev.vkschool.model.dto.VkAudio;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;
import com.dmbaryshev.vkschool.model.network.ApiHelper;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.mapper.AudioMapper;
import com.dmbaryshev.vkschool.model.repository.mapper.BaseMapper;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AudioRepo extends BaseRepo<VkAudio, AudioVM> {
    public Observable<ResponseAnswer<AudioVM>> getAudio(int count, int offset) {
        Observable<Response<CommonResponse<VkAudio>>> observable = ApiHelper.createService()
                                                                            .getAudio(count, offset)
                                                                            .subscribeOn(Schedulers.io())
                                                                            .observeOn(
                                                                                    AndroidSchedulers
                                                                                            .mainThread());
        return getResponseAnswer(observable);
    }

    public Observable<ResponseAnswer<AudioVM>> getAudioRecommendation(String targetAudio,
                                                                      int count,
                                                                      int offset) {
        Observable<Response<CommonResponse<VkAudio>>> observable = ApiHelper.createService()
                                                                            .getAudioRecommendation(
                                                                                    targetAudio,
                                                                                    count,
                                                                                    offset)
                                                                            .subscribeOn(Schedulers.io())
                                                                            .observeOn(
                                                                                    AndroidSchedulers
                                                                                            .mainThread());
        return getResponseAnswer(observable);
    }

    public Observable<Void> addAudio(int audioId, int ownerId) {
        return ApiHelper.createService()
                        .addAudio(audioId, ownerId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected BaseMapper<VkAudio, AudioVM> initMapper() {
        return new AudioMapper();
    }
}
