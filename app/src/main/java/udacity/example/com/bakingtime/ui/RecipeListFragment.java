package udacity.example.com.bakingtime.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import udacity.example.com.bakingtime.OnAdapterClickHandler;
import udacity.example.com.bakingtime.R;
import udacity.example.com.bakingtime.RecipeListAdapter;
import udacity.example.com.bakingtime.model.Bake;

public class RecipeListFragment extends Fragment implements OnAdapterClickHandler {

    private static String TAG = RecipeListFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecipeListAdapter mAdapter;
    OnSelectedListener mCallback;


    public RecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        final View rootView = inflater.inflate(R.layout.master_recipe_list, container, false);
        final View rootView = inflater.inflate(R.layout.master_recipe_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_recyclerView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecipeListAdapter(this);
        mAdapter.setRecipeStepsList(Bake.getSteps());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    // click on recyclerView item
    @Override
    public void onClick(int position) {
        mCallback.onListItemSelected(position);
    }

    public interface OnSelectedListener {
        public void onListItemSelected(int position);
    }


    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSelectedListener");
        }
    }

}
