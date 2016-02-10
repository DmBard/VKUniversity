package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VkMessage {
    public static final int OUT = 1;
    public static final int IN = 0;
    
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

    private String photo;


    public String getPhoto() {
        if (photo != null) {return photo;}
        String photoUrl = null;
        if (attachments == null || attachments.size() == 0) {return null;}
        for (VkAttachment vkAttachment : attachments) {
            if (!vkAttachment.type.equals("photo")) continue;
            photoUrl = vkAttachment.photo.photo130;
        }
        photo = photoUrl;
        return photoUrl;
    }

    @Override
    public String toString() {
        return "id = " + id;
    }
}
