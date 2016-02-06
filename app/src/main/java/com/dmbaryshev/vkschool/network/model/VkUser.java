package com.dmbaryshev.vkschool.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkUser {
    @SerializedName ("id")
    @Expose
    private int id;
    @SerializedName ("first_name")
    @Expose
    private String  firstName;
    @SerializedName ("last_name")
    @Expose
    private String  lastName;
    @SerializedName ("photo_100")
    @Expose
    private String  photo100;
    @SerializedName ("online")
    @Expose
    private int online;

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
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The photo100
     */
    public String getPhoto100() {
        return photo100;
    }

    /**
     * @param photo100 The photo_100
     */
    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
    }

    /**
     * @return The online
     */
    public int getOnline() {
        return online;
    }

    /**
     * @param online The online
     */
    public void setOnline(int online) {
        this.online = online;
    }
}
