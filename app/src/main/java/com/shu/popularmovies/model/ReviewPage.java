package com.shu.popularmovies.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

@Parcel
public class ReviewPage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<Review> results = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @ParcelProperty("id")
    public Integer getId() {
        return id;
    }

    @ParcelProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @ParcelProperty("page")
    public Integer getPage() {
        return page;
    }

    @ParcelProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @ParcelProperty("results")
    public List<Review> getReviews() {
        return results;
    }

    @ParcelProperty("results")
    public void setReviews(List<Review> results) {
        this.results = results;
    }

    @ParcelProperty("totalPages")
    public Integer getTotalPages() {
        return totalPages;
    }

    @ParcelProperty("totalPages")
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @ParcelProperty("totalResults")
    public Integer getTotalReviews() {
        return totalResults;
    }

    @ParcelProperty("totalResults")
    public void setTotalReviews(Integer totalResults) {
        this.totalResults = totalResults;
    }

}