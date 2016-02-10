package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkPhoto {
    @SerializedName ("id")
    @Expose
    public int id;
    @SerializedName ("album_id")
    @Expose
    public int albumId;
    @SerializedName ("owner_id")
    @Expose
    public int ownerId;
    @SerializedName ("photo_75")
    @Expose
    public String  photo75;
    @SerializedName ("photo_130")
    @Expose
    public String  photo130;
    @SerializedName ("photo_604")
    @Expose
    public String  photo604;
    @SerializedName ("photo_807")
    @Expose
    public String  photo807;
    @SerializedName ("photo_1280")
    @Expose
    public String  photo1280;
    @SerializedName ("width")
    @Expose
    public int width;
    @SerializedName ("height")
    @Expose
    public int height;
    @SerializedName ("text")
    @Expose
    public String  text;
    @SerializedName ("date")
    @Expose
    public int date;
    @SerializedName ("access_key")
    @Expose
    public String  accessKey;
}
