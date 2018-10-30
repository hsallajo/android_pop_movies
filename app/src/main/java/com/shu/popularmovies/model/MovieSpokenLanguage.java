package com.shu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

@Parcel
class MovieSpokenLanguage {

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("name")
    @Expose
    private String name;

    @ParcelProperty("iso6391")
    public String getIso6391() {
        return iso6391;
    }

    @ParcelProperty("iso6391")
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    @ParcelProperty("name")
    public String getName() {
        return name;
    }

    @ParcelProperty("name")
    public void setName(String name) {
        this.name = name;
    }

}
