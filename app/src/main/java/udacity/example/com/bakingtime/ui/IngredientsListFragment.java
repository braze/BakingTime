package udacity.example.com.bakingtime.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import udacity.example.com.bakingtime.IngredientsListAdapter;
import udacity.example.com.bakingtime.R;
import udacity.example.com.bakingtime.model.Bake;

public class IngredientsListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private IngredientsListAdapter mAdapter;

    public IngredientsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.master_ingredients_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredients_recyclerView);

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new IngredientsListAdapter();
        mAdapter.setRecipeIngredientsList(Bake.getIngredients());
        mRecyclerView.setAdapter(mAdapter);

    }
}


