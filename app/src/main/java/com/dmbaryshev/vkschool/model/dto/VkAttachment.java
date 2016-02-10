package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkAttachment {
    @SerializedName ("type")
    @Expose
    public String  type;
    @SerializedName ("photo")
    @Expose
    public VkPhoto photo;
}
