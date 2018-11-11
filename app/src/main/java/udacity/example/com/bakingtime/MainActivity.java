package udacity.example.com.bakingtime;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.example.com.bakingtime.model.Bake;
import udacity.example.com.bakingtime.utilites.NetworkUtils;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted, MainActivityAdapterOnClickHandler{
    
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

        mGridLayoutManager = new GridLayoutManager(this,columns);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MainActivityAdapter(this);
        mAdapter.setRecipesNameListList(mAdapter.getRecipesNameList());
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreate: START ASYNC TASK");
        makeRecipesQuery(this);
    }

    @Override
    public void onTaskCompleted(ArrayList<Bake> list) {
        Log.d(TAG, "onTaskCompleted: SET JSON LIST");
        mAdapter.setRecipesNameListList(list);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(int position) {
        
    }

    /**
     * Make query for recipes
     *
     */
    private void makeRecipesQuery(Context context) {

        Log.d(TAG, "makeRecipesQuery: STARTING >>>>>>>>>>>");
//        if (NetworkUtils.hasInternetConnection(context)) {
//            URL bakeUrl = NetworkUtils.buildBaseUrl();
//            mProgressBar.setVisibility(View.VISIBLE);
//            new MainActivityQueryAsyncTask(MainActivity.this).execute(bakeUrl);
//        }

        ///TMP decision
        URL bakeUrl = NetworkUtils.buildBaseUrl();
        mProgressBar.setVisibility(View.VISIBLE);
        new MainActivityQueryAsyncTask(MainActivity.this).execute(bakeUrl);

    }

}
