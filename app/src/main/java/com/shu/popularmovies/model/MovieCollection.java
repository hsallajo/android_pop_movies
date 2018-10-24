package com.shu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

@Parcel
public class MovieCollection {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @ParcelProperty("id")
    public Integer getId() {
        return id;
    }

    @ParcelProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @ParcelProperty("name")
    public String getName() {
        return name;
    }

    @ParcelProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @ParcelProperty("posterPath")
    public String getPosterPath() {
        return posterPath;
    }

    @ParcelProperty("posterPath")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @ParcelProperty("backdropPath")
    public String getBackdropPath() {
        return backdropPath;
    }

    @ParcelProperty("backdropPath")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

}
