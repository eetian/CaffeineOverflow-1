package com.caffeineoverflow.models;

import com.caffeineoverflow.models.Result;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopResults {

    @SerializedName("results")
    private ArrayList<Result> results;

    public TopResults(ArrayList<Result> results) {
        this.results = results;

    }

    public ArrayList<Result> getResults() {
        return this.results;
    }

}
