package com.madfree.bakingapp.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(indices = {@Index(value = "recipeId")},
        foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = CASCADE))
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Double quantity;
    private String measure;
    private String ingredient;
    private int recipeId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public Ingredient(Double quantity, String measure, String ingredient, int recipeId) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipeId = recipeId;
    }
}
