package com.shu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

@Parcel
public class Review {

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;

    @ParcelProperty("author")
    public String getAuthor() {
        return author;
    }

    @ParcelProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @ParcelProperty("content")
    public String getContent() {
        return content;
    }

    @ParcelProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    @ParcelProperty("id")
    public String getId() {
        return id;
    }

    @ParcelProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @ParcelProperty("url")
    public String getUrl() {
        return url;
    }

    @ParcelProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }
}
