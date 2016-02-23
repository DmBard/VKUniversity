package com.dmbaryshev.vkschool.model.repository;

import com.dmbaryshev.vkschool.model.dto.common.CommonError;
import com.dmbaryshev.vkschool.model.dto.common.CommonResponse;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.dto.common.VkResponse;
import com.dmbaryshev.vkschool.model.network.ErrorUtils;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.mapper.BaseMapper;
import com.dmbaryshev.vkschool.model.view_model.IViewModel;
import com.dmbaryshev.vkschool.utils.DLog;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

public abstract class BaseRepo<V, VM extends IViewModel> {
    private static final String TAG = DLog.makeLogTag(BaseRepo.class);
    protected BaseMapper<V, VM> mMapper;

    public BaseRepo() {
    }

    protected Observable<ResponseAnswer<VM>> getResponseAnswer(Observable<Response<CommonResponse<V>>> observable) {
        return observable.map(this::initResponseAnswer).cache();
    }

    private ResponseAnswer<VM> initResponseAnswer(Response<CommonResponse<V>> response) {
        List<V> answer = null;
        VkError vkError = null;
        if (response == null) {
            return null;
        }
        CommonResponse<V> commonResponse = response.body();
        if (commonResponse == null) {
            return null;
        }

        VkResponse<V> vkResponse = commonResponse.mVkResponse;
        if (vkResponse == null) {
            CommonError commonError = ErrorUtils.parseError(response);
            vkError = commonError.mVkError;
        } else {
            answer = vkResponse.items;
        }

        mMapper = initMapper();

        return mMapper.execute(new ResponseAnswer<>(answer, vkError));
    }

    protected abstract BaseMapper<V, VM> initMapper();
}
