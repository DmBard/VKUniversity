package com.dmbaryshev.vkschool.model.repository;

import com.dmbaryshev.vkschool.model.network.ApiHelper;
import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageRepo extends BaseRepo<VkMessage> {

    private int mIdUser;

    public MessageRepo(int idUser) {mIdUser = idUser;}

    @Override
    protected Observable<Response<CommonResponse<VkMessage>>> initObservable() {
        return ApiHelper.createService()
                        .getMessageHistory(20, mIdUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        //                        .compose(applySchedulers());
    }
}
