package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VkMessage {
    @SerializedName ("id")
    @Expose
    public int id;
    @SerializedName ("body")
    @Expose
    public String  body;
    @SerializedName ("user_id")
    @Expose
    public int userId;
    @SerializedName ("from_id")
    @Expose
    public int fromId;
    @SerializedName ("date")
    @Expose
    public int date;
    @SerializedName ("read_state")
    @Expose
    public int readState;
    @SerializedName ("out")
    @Expose
    public int out;
    @SerializedName ("attachments")
    @Expose
    public List<VkAttachment> attachments = new ArrayList<>();
}
