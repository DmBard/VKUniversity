package com.dmbaryshev.vkschool.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VkUserResponse {
    @SerializedName ("response")
    @Expose
    private List<VkUser> mVkUsers = new ArrayList<>();

    /**
     * @return The mVkUsers
     */
    public List<VkUser> getVkUsers() {
        return mVkUsers;
    }

    /**
     * @param vkUsers The mVkUsers
     */
    public void setVkUsers(List<VkUser> vkUsers) {
        this.mVkUsers = vkUsers;
    }
}
