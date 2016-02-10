package com.dmbaryshev.vkschool.model.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkError {

    @SerializedName ("error_code")
    @Expose
    public int    errorCode;

    @SerializedName ("error_msg")
    @Expose
    public String errorMsg;
}