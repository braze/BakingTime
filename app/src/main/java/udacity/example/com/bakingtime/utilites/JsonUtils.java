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
     * @param string JSON string
     * @return ArrayList of Movie objects.
     */
    public static ArrayList<Bake> getRecipesNamesList (String string) {
        Log.d(TAG, "getRecipesNamesList: START BAKING>>>>");

        ArrayList<Bake> list = new ArrayList<>();
        JSONArray array = null;
        try {
            array = new JSONArray(string);

            for (int i = 0; i < array.length(); i++) {

                JSONObject objectInArray = array.getJSONObject(i);
                String name = objectInArray.getString("name");
                Log.d(TAG, "getRecipesNamesList: " + name);
                list.add(new Bake(name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
