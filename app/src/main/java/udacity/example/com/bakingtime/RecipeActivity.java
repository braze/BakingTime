package udacity.example.com.bakingtime;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import udacity.example.com.bakingtime.ui.IngredientsListFragment;
import udacity.example.com.bakingtime.ui.RecipeListFragment;

public class RecipeActivity extends AppCompatActivity implements RecipeListFragment.OnSelectedListener{

    private static String TAG = RecipeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);



        RecipeListFragment fragment = new RecipeListFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_list_frame, fragment)
                .commit();


    }


    @Override
    public void onListItemSelected(int position) {
        if (position == 0) {

            IngredientsListFragment fragment = new IngredientsListFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_list_frame, fragment)
                    .addToBackStack("")
                    .commit();
        }
    }
}
