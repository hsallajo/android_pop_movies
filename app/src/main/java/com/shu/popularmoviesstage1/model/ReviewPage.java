package com.shu.popularmoviesstage1.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public List<Review> getReviews() {
            return results;
        }

        public void setReviews(List<Review> results) {
            this.results = results;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getTotalReviews() {
            return totalResults;
        }

        public void setTotalReviews(Integer totalResults) {
            this.totalResults = totalResults;
        }

    }