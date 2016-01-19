package com.dmbaryshev.vkschool.network.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VkResponse<T> {
    @SerializedName ("count")
    @Expose
    private Integer count;
    @SerializedName ("items")
    @Expose
    private List<T> items = new ArrayList<>();

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return The items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<T> items) {
        this.items = items;
    }
}