package udacity.example.com.bakingtime;

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
import udacity.example.com.bakingtime.model.Bake;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.BakeAdapterViewHolder> {

    private ArrayList<Bake> mRecipesNameList;

    private final MainActivityAdapterOnClickHandler mClickHandler;

    public MainActivityAdapter(MainActivityAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public BakeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        BakeAdapterViewHolder viewHolder = new BakeAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BakeAdapterViewHolder holder, int i) {
        String recipe = mRecipesNameList.get(i).getName();
        holder.itemView.setText(recipe);
    }

    @Override
    public int getItemCount() {
        if (mRecipesNameList == null) {
            return 0;
        }
        return mRecipesNameList.size();
    }

    public void setRecipesNameListList(ArrayList<Bake> recipesNameList) {
        mRecipesNameList = recipesNameList;
        notifyDataSetChanged();
    }

    public ArrayList<Bake> getRecipesNameList() {
        return mRecipesNameList;
    }

    class BakeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_name_tv)
        TextView itemView;

        public BakeAdapterViewHolder(View view) {
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
