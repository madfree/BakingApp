package com.madfree.bakingapp.data;

import com.google.gson.annotations.Expose;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (indices = {@Index(value = "id", unique = true)})
public class Recipe {

    @PrimaryKey
    private int id;
    private String name;
    private int servings;
    private String image;

    @Ignore
    @Expose
    private List<Ingredient> ingredients = null;

    @Ignore
    @Expose
    private List<Step> steps = null;

    public Recipe(int id, String name, int servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

}