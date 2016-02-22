package com.dmbaryshev.vkschool.model.view_model;

import com.dmbaryshev.vkschool.model.dto.VkAttachment;

import java.util.List;

public class MessageVM implements IViewModel {
    public static final int OUT = 1;
    public static final int IN  = 0;

    public  int                id;
    public  String             body;
    public  int                out;
    public  List<VkAttachment> attachments;
    private String             photo;

    public MessageVM(int id, String body, int out, List<VkAttachment> attachments) {
        this.id = id;
        this.body = body;
        this.out = out;
        this.attachments = attachments;
    }

    public String getPhoto() {
        if (photo != null) {return photo;}
        String photoUrl = null;
        if (attachments == null || attachments.size() == 0) {return null;}
        for (VkAttachment vkAttachment : attachments) {
            if (!vkAttachment.type.equals("photo")) { continue; }
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

