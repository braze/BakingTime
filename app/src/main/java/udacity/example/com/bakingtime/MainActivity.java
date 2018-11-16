package udacity.example.com.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.example.com.bakingtime.model.Bake;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted, OnAdapterClickHandler {
    
    private static String TAG = MainActivity.class.getSimpleName();

    private MainActivityAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Log.d(TAG, "onCreate: START ASYNC TASK");
        makeRecipesQuery(this);
    }

    @Override
    public void onTaskCompleted(ArrayList<Bake> list) {
        Log.d(TAG, "onTaskCompleted: SET JSON LIST");
        mAdapter.setRecipesNameList(list);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(int position) {
        String id = mAdapter.getRecipesNameList().get(position).getId();

        Log.d(TAG, "RecipeActivity onCreate: start RecipeQueryAsyncTask1");
        new RecipeQueryAsyncTask().execute(id);

        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
        
    }

    /**
     * Make query for recipes
     *
     */
    private void makeRecipesQuery(Context context) {

        Log.d(TAG, "makeRecipesQuery: STARTING >>>>>>>>>>>");
//        if (NetworkUtils.hasInternetConnection(context)) {
//            mProgressBar.setVisibility(View.VISIBLE);
//            new MainActivityQueryAsyncTask(MainActivity.this).execute(bakeUrl);
//        }

        ///TMP decision
        mProgressBar.setVisibility(View.VISIBLE);
        new MainActivityQueryAsyncTask(MainActivity.this).execute();

    }

}
