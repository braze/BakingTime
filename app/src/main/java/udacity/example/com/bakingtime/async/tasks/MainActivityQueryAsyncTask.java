package udacity.example.com.bakingtime.async.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.ArrayList;

import udacity.example.com.bakingtime.interfaces.OnTaskCompleted;
import udacity.example.com.bakingtime.model.Bake;
import udacity.example.com.bakingtime.utilites.JsonUtils;
import udacity.example.com.bakingtime.utilites.NetworkUtils;

public class MainActivityQueryAsyncTask extends AsyncTask<Void, Void, ArrayList<Bake>> {
    
    private OnTaskCompleted taskCompleted;
    private SharedPreferences preferences;


    public MainActivityQueryAsyncTask(OnTaskCompleted activityContext, SharedPreferences preferences) {
        this.taskCompleted = activityContext;
        this.preferences = preferences;
    }

    @Override
    protected ArrayList<Bake> doInBackground(Void... voids) {

//        URL bakeUrl = NetworkUtils.buildBaseUrl();
//
//        String jsonString = null;
        //create list of Recipes names
//        ArrayList<Bake> list = new ArrayList<>();
//        try {
//            jsonString = NetworkUtils.getResponseFromHttpUrl(bakeUrl);
//            list = JsonUtils.getRecipesNamesList(jsonString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ArrayList<Bake> list = new ArrayList<>();
        String jsonString = NetworkUtils.getJsonString(preferences);
        list = JsonUtils.getRecipesNamesList(jsonString);
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<Bake> recipesNamesList) {
        super.onPostExecute(recipesNamesList);
        taskCompleted.onTaskCompleted(recipesNamesList);
    }
}
