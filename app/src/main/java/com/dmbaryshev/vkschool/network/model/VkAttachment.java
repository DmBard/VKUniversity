package com.dmbaryshev.vkschool.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkAttachment {
    @SerializedName ("type")
    @Expose
    private String  type;
    @SerializedName ("photo")
    @Expose
    private VkPhoto photo;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The photo
     */
    public VkPhoto getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    public void setPhoto(VkPhoto photo) {
        this.photo = photo;
    }
}
