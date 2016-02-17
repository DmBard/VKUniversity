package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkAudio {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("owner_id")
    @Expose
    public int ownerId;
    @SerializedName("artist")
    @Expose
    public String artist;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("duration")
    @Expose
    public int duration;
    @SerializedName("date")
    @Expose
    public int date;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("lyrics_id")
    @Expose
    public int lyricsId;
    @SerializedName ("genre_id")
    @Expose
    public int genreId;
}
