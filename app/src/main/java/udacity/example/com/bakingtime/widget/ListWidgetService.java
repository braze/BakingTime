package udacity.example.com.bakingtime.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import udacity.example.com.bakingtime.R;
import udacity.example.com.bakingtime.RecipeActivity;
import udacity.example.com.bakingtime.model.Bake;


public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private ArrayList<Bake> mIngredients;
    private Context mContext;


    public ListRemoteViewsFactory(Context applicationContext) {
        this.mContext = applicationContext;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        //fetch data
        if (RecipeActivity.sIngredients != null){
            mIngredients = RecipeActivity.sIngredients;
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredients == null) {
            return 0;
        }
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients == null || mIngredients.size() == 0) return null;

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_widget);

        String ingredient = mIngredients.get(position).getIngredient();
        String measure = mIngredients.get(position).getMeasure();
        String quantity = mIngredients.get(position).getQuantity();
        views.setTextViewText(R.id.widget_ingredient_name_tv, String.valueOf(ingredient));
        views.setTextViewText(R.id.widget_quantity_measure_tv, String.valueOf(quantity + " (" + measure + ")"));

        Bundle extras = new Bundle();
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_ingredient_name_tv, fillInIntent);
        views.setOnClickFillInIntent(R.id.widget_quantity_measure_tv, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
