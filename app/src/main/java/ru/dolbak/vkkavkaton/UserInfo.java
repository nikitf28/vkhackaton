package ru.dolbak.vkkavkaton;

import android.graphics.Bitmap;

public class UserInfo {
    String status;
    String ERROR = "NONE";
    String firstName;
    String lastName;
    String age;
    String bio;
    Bitmap[] pictures;
    String[] tags;

    public UserInfo(String status, String firstName, String lastName, String age, String bio, Bitmap[] pictures, String[] tags) {
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.bio = bio;
        this.pictures = pictures;
        this.tags = tags;
    }

    public UserInfo(String status, String ERROR) {
        this.status = status;
        this.ERROR = ERROR;
    }
}
