package com.dmbaryshev.vkschool.model.view_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.dmbaryshev.vkschool.model.dto.LastSeen;

public class UserVM implements IViewModel, Parcelable {
    public int      id;
    public String   firstName;
    public String   lastName;
    public String   photo100;
    public int      online;
    public LastSeen lastSeen;

    public UserVM(int id,
                  String firstName,
                  String lastName,
                  String photo100,
                  int online,
                  LastSeen lastSeen) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo100 = photo100;
        this.online = online;
        this.lastSeen = lastSeen;
    }

    protected UserVM(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        photo100 = in.readString();
        online = in.readInt();
        lastSeen = new LastSeen();
        lastSeen.platform = in.readInt();
        lastSeen.time = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(firstName);
        out.writeString(lastName);
        out.writeString(photo100);
        out.writeInt(online);
        out.writeInt(lastSeen.platform);
        out.writeInt(lastSeen.time);
    }

    public static final Creator<UserVM> CREATOR = new Creator<UserVM>() {
        @Override
        public UserVM createFromParcel(Parcel in) {
            return new UserVM(in);
        }

        @Override
        public UserVM[] newArray(int size) {
            return new UserVM[size];
        }
    };

    @Override
    public String toString() {
        return String.format("%d %s", id, lastName);
    }
}
