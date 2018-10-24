package com.shu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

@Parcel
public class Trailer {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("type")
    @Expose
    private String type;

    @ParcelProperty("id")
    public String getId() {
        return id;
    }

    @ParcelProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @ParcelProperty("iso6391")
    public String getIso6391() {
        return iso6391;
    }

    @ParcelProperty("iso6391")
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    @ParcelProperty("iso31661")
    public String getIso31661() {
        return iso31661;
    }

    @ParcelProperty("iso31661")
    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    @ParcelProperty("key")
    public String getKey() {
        return key;
    }

    @ParcelProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @ParcelProperty("name")
    public String getName() {
        return name;
    }

    @ParcelProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @ParcelProperty("site")
    public String getSite() {
        return site;
    }

    @ParcelProperty("site")
    public void setSite(String site) {
        this.site = site;
    }

    @ParcelProperty("size")
    public Integer getSize() {
        return size;
    }

    @ParcelProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @ParcelProperty("type")
    public String getType() {
        return type;
    }

    @ParcelProperty("type")
    public void setType(String type) {
        this.type = type;
    }
}
