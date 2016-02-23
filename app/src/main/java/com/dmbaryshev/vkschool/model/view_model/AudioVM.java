package com.dmbaryshev.vkschool.model.view_model;

public class AudioVM implements IViewModel {
    public int id;
    public int ownerId;
    public String artist;
    public String title;
    public int duration;

    public AudioVM(int id, int ownerId, String artist, String title, int duration) {
        this.id = id;
        this.ownerId = ownerId;
        this.artist = artist;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("%d %s", id, title);
    }
}
