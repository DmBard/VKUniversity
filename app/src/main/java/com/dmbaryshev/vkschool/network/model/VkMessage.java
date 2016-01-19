package com.dmbaryshev.vkschool.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VkMessage {
    @SerializedName ("id")
    @Expose
    private Integer id;
    @SerializedName ("body")
    @Expose
    private String  body;
    @SerializedName ("user_id")
    @Expose
    private Integer userId;
    @SerializedName ("from_id")
    @Expose
    private Integer fromId;
    @SerializedName ("date")
    @Expose
    private Integer date;
    @SerializedName ("read_state")
    @Expose
    private Integer readState;
    @SerializedName ("out")
    @Expose
    private Integer out;
    @SerializedName ("attachments")
    @Expose
    private List<VkAttachment> attachments = new ArrayList<>();

    private String photo;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return The fromId
     */
    public Integer getFromId() {
        return fromId;
    }

    /**
     * @param fromId The from_id
     */
    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    /**
     * @return The date
     */
    public Integer getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(Integer date) {
        this.date = date;
    }

    /**
     * @return The readState
     */
    public Integer getReadState() {
        return readState;
    }

    /**
     * @param readState The read_state
     */
    public void setReadState(Integer readState) {
        this.readState = readState;
    }

    /**
     * @return The out
     */
    public Integer getOut() {
        return out;
    }

    /**
     * @param out The out
     */
    public void setOut(Integer out) {
        this.out = out;
    }

    /**
     * @return The attachments
     */
    public List<VkAttachment> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments The attachments
     */
    public void setAttachments(List<VkAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getPhoto() {
        if (photo != null) {return photo;}
        String photoUrl = null;
        if (attachments == null || attachments.size() == 0) {return null;}
        for (VkAttachment vkAttachment : attachments) {
            if (!vkAttachment.getType().equals("photo")) continue;
            photoUrl = vkAttachment.getPhoto().getPhoto130();
        }
        photo = photoUrl;
        return photoUrl;
    }
}
