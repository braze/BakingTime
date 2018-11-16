package udacity.example.com.bakingtime.model;

import java.util.ArrayList;

public class Bake {

    private String id;
    private String name;
    private String quantity;
    private String measure;
    private String ingredient;

    private String stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    private static ArrayList<Bake> ingredients;
    private static ArrayList<Bake> steps;

    public Bake(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Bake(String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Bake(String ingredient, String measure, String quantity) {
        this.ingredient = ingredient;
        this.measure = measure;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public static ArrayList<Bake> getIngredients() {
        return ingredients;
    }

    public static void setIngredients(ArrayList<Bake> ingredients) {
        Bake.ingredients = ingredients;
    }

    public static ArrayList<Bake> getSteps() {
        return steps;
    }

    public static void setSteps(ArrayList<Bake> steps) {
        Bake.steps = steps;
    }
}
