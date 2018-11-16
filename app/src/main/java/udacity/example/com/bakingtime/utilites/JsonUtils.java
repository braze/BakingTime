package udacity.example.com.bakingtime.utilites;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import udacity.example.com.bakingtime.model.Bake;

public class JsonUtils {

    private static String TAG = JsonUtils.class.getSimpleName();

    private JsonUtils() {
    }

    /**
     * Get main movie information
     *
     * @param jsonString JSON string
     * @return ArrayList of Bake objects.
     */
    public static ArrayList<Bake> getRecipesNamesList (String jsonString) {
        Log.d(TAG, "getRecipesNamesList: START BAKING>>>>");

        ArrayList<Bake> list = new ArrayList<>();
        JSONArray array = null;
        try {
            array = new JSONArray(jsonString);

            for (int i = 0; i < array.length(); i++) {

                JSONObject objectInArray = array.getJSONObject(i);
                String id = objectInArray.getString("id");
                String name = objectInArray.getString("name");
                Log.d(TAG, "getRecipesNamesList: " + name);
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
     * @return  BakeDetail object.
     */
    public static void getRecipeDetails (String jsonString, String recipeId) {
        Log.d(TAG, "getRecipeDetails: START BAKING DETAILS >>>>");

        ArrayList<Bake> ingredients = new ArrayList<>();
        ArrayList<Bake> steps = new ArrayList<>();
        //add first row for starting Ingredients fragment
        steps.add(new Bake("Ingredients", "", "", ""));

        try {
            JSONArray array = new JSONArray(jsonString);

            for (int i = 0; i < array.length(); i++) {
                JSONObject objectInArray = array.getJSONObject(i);
                String id = objectInArray.getString("id");
                Log.d(TAG, "getRecipeDetails: String id " + id );


                if (id.equals(recipeId)) {
                    Log.d(TAG, "getRecipeDetails: IF STATEMENT" + id.equals(recipeId));

                    JSONArray arrayIngredients = objectInArray.getJSONArray("ingredients");
                    for (int j = 0; j < arrayIngredients.length(); j++) {

                        JSONObject objInArrayIngredients = arrayIngredients.getJSONObject(j);

                        String ingredient = objInArrayIngredients.getString("ingredient");
                        Log.d(TAG, "getRecipeDetails: ingredient " + ingredient );

                        String measure = objInArrayIngredients.getString("measure");
                        Log.d(TAG, "getRecipeDetails: measure " + measure );

                        String quantity = objInArrayIngredients.getString("quantity");
                        Log.d(TAG, "getRecipeDetails: quantity " + quantity );

                        ingredients.add(new Bake(ingredient, measure, quantity));
                    }

                    JSONArray arraySteps = objectInArray.getJSONArray("steps");
                    for (int j = 0; j < arraySteps.length(); j++) {
                        JSONObject objInArraySteps = arraySteps.getJSONObject(j);
                        String shortDescription = objInArraySteps.getString("shortDescription");
                        Log.d(TAG, "shortDescription: quantity " + shortDescription );

                        String description = objInArraySteps.getString("description");
                        Log.d(TAG, "getRecipeDetails: description " + description );

                        String videoURL = objInArraySteps.getString("videoURL");
                        Log.d(TAG, "getRecipeDetails: videoURL " + videoURL );

                        String thumbnailURL = objInArraySteps.getString("thumbnailURL");
                        steps.add(new Bake(shortDescription, description, videoURL, thumbnailURL));
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        return new BakeDetails(ingredients, steps);
        Bake.setSteps(steps);
        Bake.setIngredients(ingredients);
    }

}
