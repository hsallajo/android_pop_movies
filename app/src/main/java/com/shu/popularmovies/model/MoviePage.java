package com.shu.popularmovies.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

@Parcel
public class MoviePage {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<Movie> results = null;

    @ParcelProperty("page")
    public Integer getPage() {
        return page;
    }

    @ParcelProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @ParcelProperty("totalResults")
    public Integer getTotalResults() {
        return totalResults;
    }

    @ParcelProperty("totalResults")
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @ParcelProperty("totalPages")
    public Integer getTotalPages() {
        return totalPages;
    }

    @ParcelProperty("totalPages")
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @ParcelProperty("results")
    public List<Movie> getMovies() {
        return results;
    }

    @ParcelProperty("results")
    public void setResults(List<Movie> results) {
        this.results = results;
    }

}


