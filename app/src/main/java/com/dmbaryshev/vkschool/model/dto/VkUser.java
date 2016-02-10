package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkUser {
    @SerializedName ("id")
    @Expose
    public int      id;
    @SerializedName ("first_name")
    @Expose
    public String   firstName;
    @SerializedName ("last_name")
    @Expose
    public String   lastName;
    @SerializedName ("photo_100")
    @Expose
    public String   photo100;
    @SerializedName ("online")
    @Expose
    public int      online;
    @SerializedName ("last_seen")
    @Expose
    public LastSeen lastSeen;
}
