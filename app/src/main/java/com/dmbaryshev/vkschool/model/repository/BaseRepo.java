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

    public BaseRepo() {
    }

    protected Observable<ResponseAnswer<T>> getResponseAnswer(Observable<Response<CommonResponse<T>>> observable) {
        return observable.map(this::initResponseAnswer);
    }

    private ResponseAnswer<T> initResponseAnswer(Response<CommonResponse<T>> response) {
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
}
