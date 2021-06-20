package com.collegeastra.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Book implements Parcelable {
    private String bookId;
    private String title;
    private String author;
    private String edition;
    private String pubYear;
    private List<String> dept;
    private String noofcopies;
    private String description;

    public Book(){

    }

    public Book(String bookId, String title, String author, String edition, String pubYear, List<String>  dept, String noofcopies,String description){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.pubYear = pubYear;
        this.dept = dept;
        this.noofcopies = noofcopies;
        this.description = description;
    }
    protected Book(Parcel in) {
        bookId = in.readString();
        title = in.readString();
        author = in.readString();
        edition = in.readString();
        pubYear = in.readString();
        if (in.readByte() == 0x01) {
            dept = new ArrayList<String>();
            in.readList(dept, String.class.getClassLoader());
        } else {
            dept = null;
        }
        noofcopies = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookId);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(edition);
        dest.writeString(pubYear);
        dest.writeList(dept);
        dest.writeString(noofcopies);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public List<String> getDept() {
        return dept;
    }

    public void setDept(List<String> dept) {
        this.dept = dept;
    }

    public String getNoofcopies() {
        return noofcopies;
    }

    public void setNoofcopies(String noofcopies) {
        this.noofcopies = noofcopies;
    }

    public static Creator<Book> getCREATOR() {
        return CREATOR;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

}