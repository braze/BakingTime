package udacity.example.com.bakingtime.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.example.com.bakingtime.interfaces.OnAdapterClickHandler;
import udacity.example.com.bakingtime.R;
import udacity.example.com.bakingtime.model.Bake;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeAdapterViewHolder> {

    private ArrayList<Bake> mRecipeStepsList;

    private final OnAdapterClickHandler mClickHandler;

    public RecipeListAdapter(OnAdapterClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeAdapterViewHolder viewHolder = new RecipeAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int i) {
        String stepName = mRecipeStepsList.get(i).getShortDescription();
        holder.itemView.setText(stepName);
    }

    @Override
    public int getItemCount() {
        if (mRecipeStepsList == null) {
            return 0;
        }
        return mRecipeStepsList.size();
    }

    public void setRecipeStepsList(ArrayList<Bake> recipesNameList) {
        mRecipeStepsList = recipesNameList;
        notifyDataSetChanged();
    }

    public ArrayList<Bake> getRecipeStepsList() {
        return mRecipeStepsList;
    }

    class RecipeAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        @BindView(R.id.recipe_step_tv)
        TextView itemView;

        public RecipeAdapterViewHolder(View view) {
            super(view);

            // binding view
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}



