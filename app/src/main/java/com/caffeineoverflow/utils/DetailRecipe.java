package com.caffeineoverflow.utils;

import com.google.gson.annotations.SerializedName;

public class DetailRecipe {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("extendedIngredients")
    private String[] extendedIngredients;
    @SerializedName("instructions")
    private String instructions;

    public DetailRecipe(String id, String title, String[] extendedIngredients, String instructions) {
        this.id = id;
        this.title = title;
        this.extendedIngredients = extendedIngredients;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getExtendedIngredients() {
        return extendedIngredients;
    }

    public String getInstructions() {
        return instructions;
    }
}
