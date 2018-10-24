package com.shu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

@Parcel
public class MovieProductionCompany {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("origin_country")
    @Expose
    private String originCountry;

    @ParcelProperty("id")
    public Integer getId() {
        return id;
    }

    @ParcelProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @ParcelProperty("logoPath")
    public String getLogoPath() {
        return logoPath;
    }

    @ParcelProperty("logoPath")
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @ParcelProperty("name")
    public String getName() {
        return name;
    }

    @ParcelProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @ParcelProperty("originCountry")
    public String getOriginCountry() {
        return originCountry;
    }

    @ParcelProperty("originCountry")
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
}
