package com.dmbaryshev.vkschool.model.view_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class AudioVM implements IViewModel, Parcelable {
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

    protected AudioVM(Parcel in) {
        id = in.readInt();
        ownerId = in.readInt();
        artist = in.readString();
        title = in.readString();
        duration = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(ownerId);
        out.writeString(artist);
        out.writeString(title);
        out.writeInt(duration);
    }

    public static final Creator<AudioVM> CREATOR = new Creator<AudioVM>() {
        @Override
        public AudioVM createFromParcel(Parcel in) {
            return new AudioVM(in);
        }

        @Override
        public AudioVM[] newArray(int size) {
            return new AudioVM[size];
        }
    };

    @Override
    public String toString() {
        return String.format("%d %s", id, title);
    }
}
