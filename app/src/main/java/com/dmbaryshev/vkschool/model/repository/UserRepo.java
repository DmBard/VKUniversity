package com.dmbaryshev.vkschool.model.repository;

import com.dmbaryshev.vkschool.model.dto.VkUser;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;
import com.dmbaryshev.vkschool.model.network.ApiHelper;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserRepo extends BaseRepo<VkUser> {

    public Observable<ResponseAnswer<VkUser>> getFriends() {
        Observable<Response<CommonResponse<VkUser>>> observable = ApiHelper.createService()
                                                                           .getFriendList("hints",
                                                                                          "photo_100,last_seen")
                                                                           .subscribeOn(Schedulers.io())
                                                                           .observeOn(
                                                                                   AndroidSchedulers
                                                                                           .mainThread());
        return getResponseAnswer(observable);
    }
}
