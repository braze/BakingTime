package udacity.example.com.bakingtime.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import udacity.example.com.bakingtime.R;

import static android.content.Intent.EXTRA_TEXT;

public class BakingTimeService extends IntentService {

    public static final String ACTION_UPDATE_BAKE_WIDGETS = "udacity.example.com.bakingtime.action.update_bake_widgets";


    public BakingTimeService() {
        super("BakingTimeService");
    }


    public static void startActionSetWidgetIngredients(Context context, String cakeName) {
        Intent intent = new Intent(context, BakingTimeService.class);
        intent.setAction(ACTION_UPDATE_BAKE_WIDGETS);
        intent.putExtra(EXTRA_TEXT, cakeName);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_BAKE_WIDGETS.equals(action)) {
                handleActionUpdateBakeWidgets(intent);
            }
        }
    }

    /**
     * Handle action UpdateBakeWidgets in the provided background thread
     */
    private void handleActionUpdateBakeWidgets(Intent intent) {
        String cakeName = intent.getStringExtra(EXTRA_TEXT);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        BakeWidgetProvider.updateBakeWidgets(this, appWidgetManager, appWidgetIds ,cakeName);

    }

    /**
     * Starts this service to perform UpdateBakeWidgets action. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateBakeWidgets(Context context) {
        Intent intent = new Intent(context, BakingTimeService.class);
        intent.setAction(ACTION_UPDATE_BAKE_WIDGETS);
        context.startService(intent);
    }
}
