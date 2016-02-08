package com.dmbaryshev.vkschool.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VkMessage {
    public static final int OUT = 0;
    public static final int IN = 1;
    
    @SerializedName ("id")
    @Expose
    private int id;
    @SerializedName ("body")
    @Expose
    private String  body;
    @SerializedName ("user_id")
    @Expose
    private int userId;
    @SerializedName ("from_id")
    @Expose
    private int fromId;
    @SerializedName ("date")
    @Expose
    private int date;
    @SerializedName ("read_state")
    @Expose
    private int readState;
    @SerializedName ("out")
    @Expose
    private int out;
    @SerializedName ("attachments")
    @Expose
    private List<VkAttachment> attachments = new ArrayList<>();

    private String photo;

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
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return The fromId
     */
    public int getFromId() {
        return fromId;
    }

    /**
     * @param fromId The from_id
     */
    public void setFromId(int fromId) {
        this.fromId = fromId;
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
     * @return The readState
     */
    public int getReadState() {
        return readState;
    }

    /**
     * @param readState The read_state
     */
    public void setReadState(int readState) {
        this.readState = readState;
    }

    /**
     * @return The out
     */
    public int getOut() {
        return out;
    }

    /**
     * @param out The out
     */
    public void setOut(int out) {
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
