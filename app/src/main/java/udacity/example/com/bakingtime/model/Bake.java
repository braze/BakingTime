package udacity.example.com.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Bake implements Parcelable {

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
    private ArrayList<Bake> ingredientsList;
    private ArrayList<Bake> stepsList;

    public Bake() {
    }

    public Bake(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Bake(String stepId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.stepId = stepId;
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

    public Bake(ArrayList<Bake> ingredientsList, ArrayList<Bake> stepsList) {
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
    }

    protected Bake(Parcel in) {
        id = in.readString();
        name = in.readString();
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
        stepId = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
        ingredientsList = in.createTypedArrayList(Bake.CREATOR);
        stepsList = in.createTypedArrayList(Bake.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
        dest.writeString(stepId);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
        dest.writeTypedList(ingredientsList);
        dest.writeTypedList(stepsList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bake> CREATOR = new Creator<Bake>() {
        @Override
        public Bake createFromParcel(Parcel in) {
            return new Bake(in);
        }

        @Override
        public Bake[] newArray(int size) {
            return new Bake[size];
        }
    };



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

    public ArrayList<Bake> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(ArrayList<Bake> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public ArrayList<Bake> getStepsList() {
        return stepsList;
    }

    public void setStepsList(ArrayList<Bake> stepsList) {
        this.stepsList = stepsList;
    }
}
