package udacity.example.com.bakingtime.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import udacity.example.com.bakingtime.R;
import udacity.example.com.bakingtime.RecipeActivity;
import udacity.example.com.bakingtime.adapters.IngredientsListAdapter;
import udacity.example.com.bakingtime.model.Bake;

public class IngredientsListFragment extends Fragment {

    private static final String BUNDLE_RECYCLER_LAYOUT = "ingredientsListFragment_recycler_layout";
    private static final String ADAPTER_LIST = "ingredientsListFragment_adapter_list";

    private RecyclerView mRecyclerView;
    private Parcelable mSavedRecyclerLayoutState;
    private IngredientsListAdapter mAdapter;


    public IngredientsListFragment() {
    }

    public static IngredientsListFragment newInstance () {
        Bundle arguments = new Bundle();
        IngredientsListFragment fragment = new IngredientsListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.master_ingredients_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredients_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new IngredientsListAdapter();

        if (savedInstanceState != null) {
            mSavedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);

            mRecyclerView.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
            ArrayList<Bake> ingredientsList = savedInstanceState.getParcelableArrayList(ADAPTER_LIST);
            mAdapter.setRecipeIngredientsList(ingredientsList);
        } else {
            mAdapter.setRecipeIngredientsList(RecipeActivity.sIngredients);
        }

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null){
            outState.putParcelableArrayList(ADAPTER_LIST, mAdapter.getRecipeIngredientsList());
            outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
        }

    }
}


