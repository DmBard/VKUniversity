package com.dmbaryshev.vkschool.model.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResponse<T> {
    @SerializedName ("response")
    @Expose
    private VkResponse<T> mVkResponse;

    /**
     * @return The mVkResponse
     */
    public VkResponse getVkResponse() {
        return mVkResponse;
    }

    /**
     * @param vkResponse The mVkResponse
     */
    public void setVkResponse(VkResponse<T> vkResponse) {
        this.mVkResponse = vkResponse;
    }
}
