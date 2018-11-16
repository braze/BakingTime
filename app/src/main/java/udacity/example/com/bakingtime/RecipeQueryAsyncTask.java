package udacity.example.com.bakingtime;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import udacity.example.com.bakingtime.utilites.JsonUtils;
import udacity.example.com.bakingtime.utilites.NetworkUtils;

public class RecipeQueryAsyncTask extends AsyncTask<String, Void, Void> {

    private final String TAG = RecipeQueryAsyncTask.class.getSimpleName();


    public RecipeQueryAsyncTask() {
    }

    @Override
    protected Void doInBackground(String... strings) {

        Log.d(TAG, "RecipeQueryAsyncTask doInBackground: >>>>>>>>>>>>");
        String recipeId = strings[0];

        String jsonString = null;
        //create list of Recipes names
//        try {

        URL bakeUrl = NetworkUtils.buildBaseUrl();
//            JsonString = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        jsonString = NetworkUtils.getFakeResponseFromHttpUrl(bakeUrl);
        JsonUtils.getRecipeDetails(jsonString, recipeId);


//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Log.d(TAG, "doInBackground: READY FOR RETURN NULL <<<<<<<<<<<");
        return null;
    }

}
