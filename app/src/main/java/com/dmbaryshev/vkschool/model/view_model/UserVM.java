package com.dmbaryshev.vkschool.model.view_model;

import com.dmbaryshev.vkschool.model.dto.LastSeen;

public class UserVM implements IViewModel {
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

    @Override
    public String toString() {
        return String.format("%d %s", id, lastName);
    }
}
