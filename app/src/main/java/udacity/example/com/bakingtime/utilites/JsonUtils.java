package udacity.example.com.bakingtime.utilites;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import udacity.example.com.bakingtime.model.Bake;

public class JsonUtils {

    private JsonUtils() {
    }

    /**
     * Get main movie information
     *
     * @param jsonString JSON string
     * @return ArrayList of Bake objects.
     */
    public static ArrayList<Bake> getRecipesNamesList (String jsonString) {

        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }

        ArrayList<Bake> list = new ArrayList<>();
        JSONArray array = null;
        try {
            array = new JSONArray(jsonString);

            for (int i = 0; i < array.length(); i++) {
                JSONObject objectInArray = array.getJSONObject(i);
                String id = objectInArray.getString("id");
                String name = objectInArray.getString("name");
                list.add(new Bake(id, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get recipe detail information
     *
     * @param jsonString JSON string
     */
    public static Bake getRecipeDetails (String jsonString, String recipeId) {

        ArrayList<Bake> ingredients = new ArrayList<>();
        ArrayList<Bake> steps = new ArrayList<>();
        //add first row for starting Ingredients fragment
        steps.add(new Bake("0","Ingredients", "", "", ""));

        try {
            JSONArray array = new JSONArray(jsonString);

            for (int i = 0; i < array.length(); i++) {
                JSONObject objectInArray = array.getJSONObject(i);
                String id = objectInArray.getString("id");

                if (id.equals(recipeId)) {

                    JSONArray arrayIngredients = objectInArray.getJSONArray("ingredients");
                    for (int j = 0; j < arrayIngredients.length(); j++) {
                        JSONObject objInArrayIngredients = arrayIngredients.getJSONObject(j);
                        String ingredient = objInArrayIngredients.getString("ingredient");
                        String measure = objInArrayIngredients.getString("measure");
                        String quantity = objInArrayIngredients.getString("quantity");
                        ingredients.add(new Bake(ingredient, measure, quantity));
                    }

                    JSONArray arraySteps = objectInArray.getJSONArray("steps");
                    for (int j = 0; j < arraySteps.length(); j++) {
                        JSONObject objInArraySteps = arraySteps.getJSONObject(j);
                        String stepId = objInArraySteps.getString("id");
                        String shortDescription = objInArraySteps.getString("shortDescription");
                        String description = objInArraySteps.getString("description");
                        String videoURL = objInArraySteps.getString("videoURL");
                        String thumbnailURL = objInArraySteps.getString("thumbnailURL");
                        steps.add(new Bake(stepId, shortDescription, description, videoURL, thumbnailURL));
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Bake(ingredients, steps);
    }

}
