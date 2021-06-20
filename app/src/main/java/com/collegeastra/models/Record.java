package com.collegeastra.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class Record implements Parcelable {
    private String usn;
    private Timestamp issueDate;
    private Timestamp returnDate;
    private String copyId;

    public Record(){

    }

    public Record(String usn,Timestamp issueDate,Timestamp returnDate, String copyId){
        this.usn = usn;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.copyId = copyId;
    }

    protected Record(Parcel in) {
        usn = in.readString();
        issueDate = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        returnDate = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        copyId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(usn);
        dest.writeValue(issueDate);
        dest.writeValue(returnDate);
        dest.writeString(copyId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public static Creator<Record> getCREATOR() {
        return CREATOR;
    }
}
