package com.dmbaryshev.vkschool.model.repository;

import com.dmbaryshev.vkschool.model.dto.VkUser;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;
import com.dmbaryshev.vkschool.model.network.ApiHelper;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserRepo extends BaseRepo<VkUser> {

    @Override
    protected Observable<Response<CommonResponse<VkUser>>> initObservable() {
        return ApiHelper.createService()
                        .getFriendList("hints", "photo_100")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        //                        .compose(applySchedulers());
    }
}
