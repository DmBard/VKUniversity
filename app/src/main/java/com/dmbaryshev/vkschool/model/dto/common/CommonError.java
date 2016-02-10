package com.dmbaryshev.vkschool.model.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonError {
    @SerializedName ("error")
    @Expose
    public VkError mVkError;
}
