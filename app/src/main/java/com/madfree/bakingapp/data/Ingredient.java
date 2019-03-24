package com.madfree.bakingapp.data;

public class Ingredient {

    private Double quantity;
    private String measure;
    private String ingredient;

    public Double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public Ingredient(Double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}
