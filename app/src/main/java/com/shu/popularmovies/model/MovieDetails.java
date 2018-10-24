package com.shu.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

import java.util.List;

@Parcel
public class MovieDetails {

    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("belongs_to_collection")
    @Expose
    private MovieCollection belongsToCollection;
    @SerializedName("budget")
    @Expose
    private Integer budget;
    @SerializedName("genres")
    @Expose
    private List<MovieGenre> genres = null;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Float popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("production_companies")
    @Expose
    private List<MovieProductionCompany> productionCompanies = null;
    @SerializedName("production_countries")
    @Expose
    private List<MovieProductionCountry> productionCountries = null;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("revenue")
    @Expose
    private Integer revenue;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("spoken_languages")
    @Expose
    private List<MovieSpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    @ParcelProperty("adult")
    public Boolean getAdult() {
        return adult;
    }

    @ParcelProperty("adult")
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    @ParcelProperty("backdropPath")
    public String getBackdropPath() {
        return backdropPath;
    }

    @ParcelProperty("backdropPath")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @ParcelProperty("belongsToCollection")
    public MovieCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    @ParcelProperty("belongsToCollection")
    public void setBelongsToCollection(MovieCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    @ParcelProperty("budget")
    public Integer getBudget() {
        return budget;
    }

    @ParcelProperty("budget")
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    @ParcelProperty("genres")
    public List<MovieGenre> getGenres() {
        return genres;
    }

    @ParcelProperty("genres")
    public void setGenres(List<MovieGenre> genres) {
        this.genres = genres;
    }

    @ParcelProperty("homepage")
    public String getHomepage() {
        return homepage;
    }

    @ParcelProperty("homepage")
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @ParcelProperty("id")
    public Integer getId() {
        return id;
    }

    @ParcelProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @ParcelProperty("imdbId")
    public String getImdbId() {
        return imdbId;
    }

    @ParcelProperty("imdbId")
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
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

    @ParcelProperty("overview")
    public String getOverview() {
        return overview;
    }

    @ParcelProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
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

    @ParcelProperty("productionCompanies")
    public List<MovieProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    @ParcelProperty("productionCompanies")
    public void setProductionCompanies(List<MovieProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    @ParcelProperty("productionCountries")
    public List<MovieProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    @ParcelProperty("productionCountries")
    public void setProductionCountries(List<MovieProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    @ParcelProperty("releaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    @ParcelProperty("releaseDate")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @ParcelProperty("revenue")
    public Integer getRevenue() {
        return revenue;
    }

    @ParcelProperty("revenue")
    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    @ParcelProperty("runtime")
    public Integer getRuntime() {
        return runtime;
    }

    @ParcelProperty("runtime")
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    @ParcelProperty("spokenLanguages")
    public List<MovieSpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    @ParcelProperty("spokenLanguages")
    public void setSpokenLanguages(List<MovieSpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    @ParcelProperty("status")
    public String getStatus() {
        return status;
    }

    @ParcelProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @ParcelProperty("tagline")
    public String getTagline() {
        return tagline;
    }

    @ParcelProperty("tagline")
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @ParcelProperty("title")
    public String getTitle() {
        return title;
    }

    @ParcelProperty("title")
    public void setTitle(String title) {
        this.title = title;
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
    public Double getVoteAverage() {
        return voteAverage;
    }

    @ParcelProperty("voteAverage")
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @ParcelProperty("voteCount")
    public Integer getVoteCount() {
        return voteCount;
    }

    @ParcelProperty("voteCount")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
