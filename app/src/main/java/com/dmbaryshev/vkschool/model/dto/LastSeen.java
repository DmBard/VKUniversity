package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmitry on 10.02.2016.
 */
public class LastSeen {
    @SerializedName ("time")
    @Expose
    public int time;
    @SerializedName ("platform")
    @Expose
    public int platform;
}
