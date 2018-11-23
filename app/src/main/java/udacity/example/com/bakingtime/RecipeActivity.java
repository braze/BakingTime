package udacity.example.com.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import udacity.example.com.bakingtime.model.Bake;
import udacity.example.com.bakingtime.ui.IngredientsListFragment;
import udacity.example.com.bakingtime.ui.RecipeListFragment;
import udacity.example.com.bakingtime.ui.RecipeStepSinglePageFragment;

import static udacity.example.com.bakingtime.MainActivity.EXTRA_INGREDIENTS_LIST;
import static udacity.example.com.bakingtime.MainActivity.EXTRA_STEPS_LIST;

public class RecipeActivity extends AppCompatActivity implements RecipeListFragment.OnSelectedListener {

    private static String TAG = RecipeActivity.class.getSimpleName();

    public static final String STEPS_LIST = "steps_list";
    public static final String INGREDIENTS_LIST = "ingredient_list";

    IngredientsListFragment mIngredientFragment;
    RecipeListFragment mRecipeListFragment;
    RecipeStepSinglePageFragment mRecipeStepSinglePageFragment;

    private int currentPosition;
    public static ArrayList<Bake> ingredients;
    private ArrayList<Bake> steps;
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        Log.d(TAG, "onCreate: intent != NULL -> " + (intent != null));
        if (intent != null) {
            String cakeName = intent.getStringExtra(Intent.EXTRA_TEXT);
            setTitle(cakeName);
            steps = intent.getExtras().getParcelableArrayList(EXTRA_STEPS_LIST);
            Log.d(TAG, "onCreate: steps is NULL -> " + (steps == null));
            ingredients = intent.getExtras().getParcelableArrayList(EXTRA_INGREDIENTS_LIST);
            Log.d(TAG, "onCreate: ingredients is NULL -> " + (ingredients == null));
        } else {
            Log.d(TAG, "onCreate: WARNING!!! INTENT == NULL");
        }

        mTwoPane = getResources().getBoolean(R.bool.two_pane);

        Log.d(TAG, "onCreate: mTwoPane = " + mTwoPane);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate: lists reincarnation");
//            mIngredientFragment = (IngredientsListFragment) fragmentManager.getFragment(savedInstanceState, "mIngredientFragment");
//            mRecipeListFragment = (RecipeListFragment) fragmentManager.getFragment(savedInstanceState, "mRecipeListFragment");
//            mRecipeStepSinglePageFragment = (RecipeStepSinglePageFragment) fragmentManager.getFragment(savedInstanceState, "mRecipeStepSinglePageFragment");
            ingredients = savedInstanceState.getParcelableArrayList("ingredientsList");
            Log.d(TAG, "onCreate: ingredients != NULL ? "+(ingredients!=null));
            steps = savedInstanceState.getParcelableArrayList("stepsList");
            Log.d(TAG, "onCreate: steps != NULL ? "+(steps!=null));

        }

        if (mTwoPane) {
            mRecipeListFragment = RecipeListFragment.newInstance(steps);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_list_frame, mRecipeListFragment)
                    .commit();

            mIngredientFragment = IngredientsListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_action_frame, mIngredientFragment)
                    .commit();
        } else {
            mRecipeListFragment = RecipeListFragment.newInstance(steps);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_list_frame, mRecipeListFragment)
                    .addToBackStack("")
                    .commit();
        }

    }


    @Override
    public void onListItemSelected(int position) {
        currentPosition = position;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (position == 0) {
            if (mTwoPane) {
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_action_frame, mIngredientFragment)
//                        .addToBackStack("")
                        .commit();
            } else {
                mIngredientFragment = IngredientsListFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_list_frame, mIngredientFragment)
                        .addToBackStack("")
                        .commit();
            }
        } else {
            Toast.makeText(this, "item#" + position, Toast.LENGTH_SHORT).show();
            getStepDetailsFragment(position);
        }
    }

    private void getStepDetailsFragment(int position) {
        Bake step = steps.get(position);
        String stepId = step.getStepId();
        String description = step.getDescription();
        String videoURL = step.getVideoURL();
        String thumbnailURL = step.getThumbnailURL();

        mRecipeStepSinglePageFragment = RecipeStepSinglePageFragment.newInstance(stepId,
                videoURL, description, thumbnailURL, steps, mTwoPane);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (mTwoPane) {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_action_frame, mRecipeStepSinglePageFragment)
//                .addToBackStack("")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_list_frame, mRecipeStepSinglePageFragment)
                    .addToBackStack("")
                    .commit();
        }

    }

    public void next(View view) {
        currentPosition = currentPosition + 1;
        getStepDetailsFragment(currentPosition);
    }

    public void previous(View view) {
        currentPosition = currentPosition - 1;
        getStepDetailsFragment(currentPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
//        getSupportFragmentManager().putFragment(outState, "recipeStepSinglePageFragment", mRecipeStepSinglePageFragment);
//        getSupportFragmentManager().putFragment(outState, "mRecipeListFragment", mRecipeListFragment);
//        getSupportFragmentManager().putFragment(outState, "mIngredientFragment", mIngredientFragment);
        Log.d(TAG, "onSaveInstanceState: save lists");
        outState.putParcelableArrayList("ingredientsList", ingredients);
        outState.putParcelableArrayList("stepsList", steps);

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(RecipeActivity.this, MainActivity.class));
//    }
}
