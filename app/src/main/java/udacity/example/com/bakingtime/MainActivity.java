package udacity.example.com.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.example.com.bakingtime.adapters.MainActivityAdapter;
import udacity.example.com.bakingtime.async.tasks.MainActivityQueryAsyncTask;
import udacity.example.com.bakingtime.interfaces.OnAdapterClickHandler;
import udacity.example.com.bakingtime.interfaces.OnTaskCompleted;
import udacity.example.com.bakingtime.model.Bake;
import udacity.example.com.bakingtime.utilites.JsonUtils;
import udacity.example.com.bakingtime.utilites.NetworkUtils;

import static udacity.example.com.bakingtime.utilites.NetworkUtils.THE_JSON;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted, OnAdapterClickHandler,
        LoaderManager.LoaderCallbacks<Bake> {

    private static String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_STEPS_LIST = "steps_list";
    public static final String EXTRA_INGREDIENTS_LIST = "ingredients_list";
    private static final int RECIPE_LOADER_ID = 1;

    private String cakeName;
    private String cakeId;

    private MainActivityAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private SharedPreferences preferences;


    private ArrayList<Bake> ingredients;
    private ArrayList<Bake> steps;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // bind the view using butterknife
        ButterKnife.bind(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recipes);
        final int columns = getResources().getInteger(R.integer.recipe_columns);

        mGridLayoutManager = new GridLayoutManager(this, columns);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MainActivityAdapter(this);
        mAdapter.setRecipesNameList(mAdapter.getRecipesNameList());
        mRecyclerView.setAdapter(mAdapter);

        makeRecipesQuery(this);
    }

    @Override
    public void onTaskCompleted(ArrayList<Bake> list) {
        mAdapter.setRecipesNameList(list);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(int position) {
        cakeId = mAdapter.getRecipesNameList().get(position).getId();
        cakeName = mAdapter.getRecipesNameList().get(position).getName();

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Bake> loader = loaderManager.getLoader(RECIPE_LOADER_ID);

        if(loader==null){
            loaderManager.initLoader(RECIPE_LOADER_ID, null, this);
        }else{
            loaderManager.restartLoader(RECIPE_LOADER_ID, null, this);
        }
    }

    /**
     * Make query for recipes
     */
    private void makeRecipesQuery(Context context) {
        if (NetworkUtils.hasInternetConnection(context)) {
            mProgressBar.setVisibility(View.VISIBLE);
            new MainActivityQueryAsyncTask(MainActivity.this, preferences).execute();
        }
    }

    @NonNull
    @Override
    public Loader<Bake> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<Bake>(this) {
            @Nullable
            @Override
            public Bake loadInBackground() {
                String jsonString = NetworkUtils.getSharedPreferences().getString(THE_JSON,"");
                return JsonUtils.getRecipeDetails(jsonString, cakeId);
            }
            @Override
            protected void onStartLoading() {
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bake> loader, Bake bake) {
        ingredients = bake.getIngredientsList();
        steps = bake.getStepsList();
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, cakeName);
        intent.putParcelableArrayListExtra(EXTRA_STEPS_LIST, steps);
        intent.putParcelableArrayListExtra(EXTRA_INGREDIENTS_LIST, ingredients);
        startActivity(intent);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bake> loader) {

    }

}
