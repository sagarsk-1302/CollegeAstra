package com.collegeastra.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Librarian implements Parcelable {

    private String username;
    private String email;
    private String password;
    private String libId;

    public Librarian(){

    }

    protected Librarian(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
        libId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(libId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Librarian> CREATOR = new Parcelable.Creator<Librarian>() {
        @Override
        public Librarian createFromParcel(Parcel in) {
            return new Librarian(in);
        }

        @Override
        public Librarian[] newArray(int size) {
            return new Librarian[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }
}
