package udacity.example.com.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
        if (intent != null) {
            String cakeName = intent.getStringExtra(Intent.EXTRA_TEXT);
            setTitle(cakeName);
            steps = intent.getExtras().getParcelableArrayList(EXTRA_STEPS_LIST);
            ingredients = intent.getExtras().getParcelableArrayList(EXTRA_INGREDIENTS_LIST);
        } else {
            closeOnError();
        }

        mTwoPane = getResources().getBoolean(R.bool.two_pane);
        mIngredientFragment = IngredientsListFragment.newInstance();
        mRecipeListFragment = RecipeListFragment.newInstance(steps);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            ingredients = savedInstanceState.getParcelableArrayList("ingredientsList");
            steps = savedInstanceState.getParcelableArrayList("stepsList");

            if (savedInstanceState.containsKey("mIngredientFragment")) {
                mIngredientFragment = (IngredientsListFragment) fragmentManager.getFragment(savedInstanceState, "mIngredientFragment");
                assert mIngredientFragment != null;
                replaceFragment(mIngredientFragment, R.id.recipe_detail_list_frame);
            } else if (savedInstanceState.containsKey("mRecipeListFragment")) {
                mRecipeListFragment = (RecipeListFragment) fragmentManager.getFragment(savedInstanceState, "mRecipeListFragment");
                assert mRecipeListFragment != null;
                replaceFragment(mRecipeListFragment, R.id.recipe_detail_list_frame);
            }
        } else {
            if (mTwoPane) {
                //start recipe list fragment
                replaceFragment(mRecipeListFragment, R.id.recipe_detail_list_frame);

                //start ingredient fragment
                replaceFragment(mIngredientFragment, R.id.recipe_detail_action_frame);
            } else {
                //start recipe list fragment
                replaceFragment(mRecipeListFragment, R.id.recipe_detail_list_frame);
            }
        }
    }

    @Override
    public void onListItemSelected(int position) {
        currentPosition = position;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (position == 0) {
            //start ingredient fragment
            if (mTwoPane) {
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_action_frame, mIngredientFragment)
                        .commit();
            } else {
                replaceFragment(mIngredientFragment, R.id.recipe_detail_list_frame);
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
        int stepsListSize = steps.size();

        mRecipeStepSinglePageFragment = RecipeStepSinglePageFragment.newInstance(stepId,
                videoURL, description, thumbnailURL, stepsListSize);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (mTwoPane) {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_action_frame, mRecipeStepSinglePageFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_list_frame, mRecipeStepSinglePageFragment)
                    .commit();
        }
    }

    private void replaceFragment(Fragment fragment, int content_frame) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(content_frame, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
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

        Log.d(TAG, "onSaveInstanceState: contains(mRecipeListFragment) " + (getSupportFragmentManager().getFragments().contains(mRecipeListFragment)));
        Log.d(TAG, "onSaveInstanceState: contains(mIngredientFragment) " + (getSupportFragmentManager().getFragments().contains(mIngredientFragment)));

        if (mRecipeListFragment != null && getSupportFragmentManager().getFragments().contains(mRecipeListFragment)) {
            getSupportFragmentManager().putFragment(outState, "mRecipeListFragment", mRecipeListFragment);
        }
        if (mIngredientFragment != null && getSupportFragmentManager().getFragments().contains(mIngredientFragment)) {
            getSupportFragmentManager().putFragment(outState, "mIngredientFragment", mIngredientFragment);
        }
        outState.putParcelableArrayList("ingredientsList", ingredients);
        outState.putParcelableArrayList("stepsList", steps);
    }

    @Override
    public void onBackPressed() {
        if (mTwoPane) {
            startActivity(new Intent(RecipeActivity.this, MainActivity.class));
        }
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.err_message, Toast.LENGTH_SHORT).show();
    }

}
