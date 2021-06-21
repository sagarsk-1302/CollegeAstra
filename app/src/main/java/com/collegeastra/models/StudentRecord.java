package com.collegeastra.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;


public class StudentRecord implements Parcelable {
    private String copyid;
    private Timestamp borrowedOn;
    private Timestamp returnedOn;
    private Boolean returned = false;

    public StudentRecord( String copyid, Timestamp borrowedOn, Timestamp returnedOn){
        this.copyid = copyid;
        this.borrowedOn = borrowedOn;
        this.returnedOn = returnedOn;
    }

    protected StudentRecord(Parcel in) {
        copyid = in.readString();
        borrowedOn = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        returnedOn = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        byte returnedVal = in.readByte();
        returned = returnedVal == 0x02 ? null : returnedVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(copyid);
        dest.writeValue(borrowedOn);
        dest.writeValue(returnedOn);
        if (returned == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (returned ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StudentRecord> CREATOR = new Parcelable.Creator<StudentRecord>() {
        @Override
        public StudentRecord createFromParcel(Parcel in) {
            return new StudentRecord(in);
        }

        @Override
        public StudentRecord[] newArray(int size) {
            return new StudentRecord[size];
        }
    };

    public String getCopyid() {
        return copyid;
    }

    public void setCopyid(String copyid) {
        this.copyid = copyid;
    }

    public Timestamp getBorrowedOn() {
        return borrowedOn;
    }

    public void setBorrowedOn(Timestamp borrowedOn) {
        this.borrowedOn = borrowedOn;
    }

    public Timestamp getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(Timestamp returnedOn) {
        this.returnedOn = returnedOn;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public static Creator<StudentRecord> getCREATOR() {
        return CREATOR;
    }
}
