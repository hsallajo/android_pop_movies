package com.shu.popularmoviesstage1.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import org.parceler.ParcelProperty;

import java.util.List;

@Parcel
public class Movie {

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private Float popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @ParcelProperty("voteCount")
    public Integer getVoteCount() {
        return voteCount;
    }

    @ParcelProperty("voteCount")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @ParcelProperty("id")
    public Integer getId() {
        return id;
    }

    @ParcelProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @ParcelProperty("video")
    public Boolean getVideo() {
        return video;
    }

    @ParcelProperty("video")
    public void setVideo(Boolean video) {
        this.video = video;
    }

    @ParcelProperty("voteAverage")
    public Float getVoteAverage() {
        return voteAverage;
    }

    @ParcelProperty("voteAverage")
    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    @ParcelProperty("title")
    public String getTitle() {
        return title;
    }

    @ParcelProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @ParcelProperty("popularity")
    public Float getPopularity() {
        return popularity;
    }

    @ParcelProperty("popularity")
    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    @ParcelProperty("posterPath")
    public String getPosterPath() {
        return posterPath;
    }

    @ParcelProperty("posterPath")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @ParcelProperty("originalLanguage")
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    @ParcelProperty("originalLanguage")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @ParcelProperty("originalTitle")
    public String getOriginalTitle() {
        return originalTitle;
    }

    @ParcelProperty("originalTitle")
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @ParcelProperty("genreIds")
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    @ParcelProperty("genreIds")
    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    @ParcelProperty("backdropPath")
    public String getBackdropPath() {
        return backdropPath;
    }

    @ParcelProperty("backdropPath")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @ParcelProperty("adult")
    public Boolean getAdult() {
        return adult;
    }

    @ParcelProperty("adult")
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    @ParcelProperty("overview")
    public String getOverview() {
        return overview;
    }

    @ParcelProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @ParcelProperty("releaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    @ParcelProperty("releaseDate")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
