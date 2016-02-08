package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkPhoto {
    @SerializedName ("id")
    @Expose
    private int id;
    @SerializedName ("album_id")
    @Expose
    private int albumId;
    @SerializedName ("owner_id")
    @Expose
    private int ownerId;
    @SerializedName ("photo_75")
    @Expose
    private String  photo75;
    @SerializedName ("photo_130")
    @Expose
    private String  photo130;
    @SerializedName ("photo_604")
    @Expose
    private String  photo604;
    @SerializedName ("photo_807")
    @Expose
    private String  photo807;
    @SerializedName ("photo_1280")
    @Expose
    private String  photo1280;
    @SerializedName ("width")
    @Expose
    private int width;
    @SerializedName ("height")
    @Expose
    private int height;
    @SerializedName ("text")
    @Expose
    private String  text;
    @SerializedName ("date")
    @Expose
    private int date;
    @SerializedName ("access_key")
    @Expose
    private String  accessKey;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The albumId
     */
    public int getAlbumId() {
        return albumId;
    }

    /**
     * @param albumId The album_id
     */
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    /**
     * @return The ownerId
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId The owner_id
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return The photo75
     */
    public String getPhoto75() {
        return photo75;
    }

    /**
     * @param photo75 The photo_75
     */
    public void setPhoto75(String photo75) {
        this.photo75 = photo75;
    }

    /**
     * @return The photo130
     */
    public String getPhoto130() {
        return photo130;
    }

    /**
     * @param photo130 The photo_130
     */
    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    /**
     * @return The photo604
     */
    public String getPhoto604() {
        return photo604;
    }

    /**
     * @param photo604 The photo_604
     */
    public void setPhoto604(String photo604) {
        this.photo604 = photo604;
    }

    /**
     * @return The photo807
     */
    public String getPhoto807() {
        return photo807;
    }

    /**
     * @param photo807 The photo_807
     */
    public void setPhoto807(String photo807) {
        this.photo807 = photo807;
    }

    /**
     * @return The photo1280
     */
    public String getPhoto1280() {
        return photo1280;
    }

    /**
     * @param photo1280 The photo_1280
     */
    public void setPhoto1280(String photo1280) {
        this.photo1280 = photo1280;
    }

    /**
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The date
     */
    public int getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(int date) {
        this.date = date;
    }

    /**
     * @return The accessKey
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * @param accessKey The access_key
     */
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
