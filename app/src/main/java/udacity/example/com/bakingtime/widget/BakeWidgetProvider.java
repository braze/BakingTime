package udacity.example.com.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import udacity.example.com.bakingtime.MainActivity;
import udacity.example.com.bakingtime.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakeWidgetProvider extends AppWidgetProvider {
    
    private static String TAG = BakeWidgetProvider.class.getSimpleName();
    private static String mCakeName = "Cake";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
                                String cakeName) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        if (cakeName != null){
            mCakeName = cakeName;
        }
        RemoteViews remoteViews = null;
        if (width < 300) {
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.bake_widget_provider);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_default_image, pendingIntent);
        } else {
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
            Intent intent = new Intent(context, ListWidgetService.class);

            Log.d(TAG, "updateAppWidget: cakeName = " + cakeName);
            remoteViews.setTextViewText(R.id.cake_name, mCakeName);
            remoteViews.setRemoteAdapter(R.id.widget_list_view, intent);

            // Set the PlantDetailActivity intent to launch when clicked
            Intent appIntent = new Intent(context, MainActivity.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.widget_list_view, appPendingIntent);

            // Handle empty gardens
            remoteViews.setEmptyView(R.id.widget_list_view, R.id.empty_view);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    /**
     * Updates all widget instances given the widget Ids and display information
     *
     * @param context          The calling context
     * @param appWidgetManager The widget manager
     * @param appWidgetIds     Array of widget Ids to be updated
     * @param cakeName         Cake name
     */
    public static void updateBakeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                                         String cakeName) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, cakeName);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingTimeService.startActionUpdateBakeWidgets(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        BakingTimeService.startActionUpdateBakeWidgets(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

