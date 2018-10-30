package com.shu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

@Parcel
class MovieProductionCountry {

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("name")
    @Expose
    private String name;

    @ParcelProperty("iso31661")
    public String getIso31661() {
        return iso31661;
    }

    @ParcelProperty("iso31661")
    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
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
