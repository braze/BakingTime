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
import udacity.example.com.bakingtime.R;
import udacity.example.com.bakingtime.model.Bake;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsAdapterViewHolder> {

    private ArrayList<Bake> mRecipeIngredientsList;

    public IngredientsListAdapter() {
    }

    @NonNull
    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        IngredientsAdapterViewHolder viewHolder = new IngredientsAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapterViewHolder holder, int i) {
        String ingredient = mRecipeIngredientsList.get(i).getIngredient();
        String measure = mRecipeIngredientsList.get(i).getMeasure();
        String quantity = mRecipeIngredientsList.get(i).getQuantity();
        holder.ingredient.setText(ingredient);
        StringBuffer buffer = new StringBuffer();
        buffer.append(quantity).append(" (").append(measure).append(")");
        holder.measureAndQuantity.setText(buffer.toString());
    }

    @Override
    public int getItemCount() {
        if (mRecipeIngredientsList == null) {
            return 0;
        }
        return mRecipeIngredientsList.size();
    }

    public void setRecipeIngredientsList(ArrayList<Bake> recipesIngredientsList) {
        mRecipeIngredientsList = recipesIngredientsList;
        notifyDataSetChanged();
    }

    public ArrayList<Bake> getRecipeIngredientsList() {
        return mRecipeIngredientsList;
    }

    class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name_tv)
        TextView ingredient;

        @BindView(R.id.quantity_measure_tv)
        TextView measureAndQuantity;

        public IngredientsAdapterViewHolder(View view) {
            super(view);

            // binding view
            ButterKnife.bind(this, view);
        }
    }
}
