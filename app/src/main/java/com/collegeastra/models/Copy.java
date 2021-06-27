package com.collegeastra.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Copy implements Parcelable {
    private String bookId;
    private String copyId;
    private Boolean isAvailable = true;
    private String issuedId;

    public Copy(){

    }

    public String getIssuedId() {
        return issuedId;
    }

    public void setIssuedId(String issuedId) {
        this.issuedId = issuedId;
    }



    protected Copy(Parcel in) {
        bookId = in.readString();
        copyId = in.readString();
        byte isAvailableVal = in.readByte();
        isAvailable = isAvailableVal == 0x02 ? null : isAvailableVal != 0x00;
        issuedId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookId);
        dest.writeString(copyId);
        if (isAvailable == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isAvailable ? 0x01 : 0x00));
        }
        dest.writeString(issuedId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Copy> CREATOR = new Parcelable.Creator<Copy>() {
        @Override
        public Copy createFromParcel(Parcel in) {
            return new Copy(in);
        }

        @Override
        public Copy[] newArray(int size) {
            return new Copy[size];
        }
    };
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public static Creator<Copy> getCREATOR() {
        return CREATOR;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }
}
