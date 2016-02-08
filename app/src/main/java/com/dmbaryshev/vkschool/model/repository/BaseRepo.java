package com.dmbaryshev.vkschool.model.repository;

import com.dmbaryshev.vkschool.model.dto.common.CommonError;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.dto.common.VkResponse;
import com.dmbaryshev.vkschool.model.network.ErrorUtils;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.utils.DLog;

import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseRepo<T> {
    private static final String TAG = DLog.makeLogTag(BaseRepo.class);

    protected Observable<Response<CommonResponse<T>>> mObservable;
    protected Observable.Transformer        schedulersTransformer;

    public BaseRepo() {
//        schedulersTransformer = o->o.subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread());
                                    /*.unsubscribeOn(Schedulers.io())*/ // TODO: remove when https://github.com/square/okhttp/issues/1592 is fixed
    }

    public Observable<ResponseAnswer<T>> getResponseAnswer() {
        mObservable = initObservable();
        return mObservable.map(this::initResponseAnswer);
    }

    public ResponseAnswer<T> initResponseAnswer(Response<CommonResponse<T>> response) {
        List<T> answer = null;
        VkError vkError = null;
        if (response == null) {
            return null;
        }
        CommonResponse<T> commonResponse = response.body();
        if (commonResponse == null) {
            return null;
        }

        VkResponse vkResponse = commonResponse.getVkResponse();
        if (vkResponse == null) {
            CommonError commonError = ErrorUtils.parseError(response);
            vkError = commonError.getVkError();
        } else {
            answer = vkResponse.getItems();
        }

        return new ResponseAnswer<>(answer, vkError);
    }

    @SuppressWarnings ("unchecked")
    protected <E> Observable.Transformer<E, E> applySchedulers() {
        return (Observable.Transformer<E, E>) schedulersTransformer;
    }

    protected abstract Observable<Response<CommonResponse<T>>> initObservable();
}
