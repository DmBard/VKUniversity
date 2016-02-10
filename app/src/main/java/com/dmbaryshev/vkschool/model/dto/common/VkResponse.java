package com.dmbaryshev.vkschool.model.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VkResponse<T> {
    @SerializedName ("count")
    @Expose
    public int count;
    @SerializedName ("items")
    @Expose
    public List<T> items = new ArrayList<>();
}
