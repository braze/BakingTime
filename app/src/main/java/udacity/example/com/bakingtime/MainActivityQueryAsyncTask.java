package udacity.example.com.bakingtime;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;

import udacity.example.com.bakingtime.model.Bake;
import udacity.example.com.bakingtime.utilites.JsonUtils;
import udacity.example.com.bakingtime.utilites.NetworkUtils;

public class MainActivityQueryAsyncTask extends AsyncTask<URL, Void, ArrayList<Bake>> {
    
    private final String TAG = MainActivityQueryAsyncTask.class.getSimpleName();
    private OnTaskCompleted taskCompleted;

    public MainActivityQueryAsyncTask(OnTaskCompleted activityContext) {
        this.taskCompleted = activityContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Bake> doInBackground(URL... urls) {

        Log.d(TAG, "doInBackground: ");
        URL searchUrl = urls[0];

        String jsonString = null;
        //create list of Recipes names
        ArrayList<Bake> list = new ArrayList<>();
//        try {

//            JsonString = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            jsonString = NetworkUtils.getFakeResponseFromHttpUrl(searchUrl);
            list = JsonUtils.getRecipesNamesList(jsonString);


//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<Bake> recipesNamesList) {
        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(recipesNamesList);
        taskCompleted.onTaskCompleted(recipesNamesList);
    }
}
