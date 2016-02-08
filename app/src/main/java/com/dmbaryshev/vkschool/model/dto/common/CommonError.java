package com.dmbaryshev.vkschool.model.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonError {
    @SerializedName ("error")
    @Expose
    private VkError mVkError;

    /**
     * @return The mVkError
     */
    public VkError getVkError() {
        return mVkError;
    }

    /**
     * @param vkError The mVkError
     */
    public void setVkError(VkError vkError) {
        this.mVkError = vkError;
    }
}
