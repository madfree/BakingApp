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
public class Step {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    private int recipeId;

    public Step(int stepId, String shortDescription, String description, String videoURL,
                String thumbnailURL, int recipeId) {
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipeId = recipeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getStepId() {
        return stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
