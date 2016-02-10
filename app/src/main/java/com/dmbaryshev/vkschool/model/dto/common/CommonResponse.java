package com.dmbaryshev.vkschool.model.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResponse<T> {
    @SerializedName ("response")
    @Expose
    public VkResponse<T> mVkResponse;
}
