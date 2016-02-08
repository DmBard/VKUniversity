package com.dmbaryshev.vkschool.model.network;

import android.content.Context;

import com.dmbaryshev.vkschool.model.dto.common.CommonError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Dmitry on 08.02.2016.
 */
public class ErrorUtils {

    public static CommonError parseError(Response<?> response) {
        Converter<ResponseBody, CommonError> converter = ApiHelper.getRetrofit()
                                                                  .responseBodyConverter(CommonError.class,
                                                                                         new Annotation[0]);

        CommonError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new CommonError();
        }

        return error;
    }
}
