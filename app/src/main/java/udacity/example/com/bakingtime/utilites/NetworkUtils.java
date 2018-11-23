package udacity.example.com.bakingtime.utilites;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String BAKING_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static final String THE_JSON = "the_Json";
    private static SharedPreferences mPreferences;

    private NetworkUtils() {
    }

    /**
     * Build the base baking URL
     *
     * @return The Url to use for query.
     */
    public static URL buildBaseUrl() {
        URL url = null;
        try {
            url = new URL(BAKING_BASE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJsonString(SharedPreferences preferences){
        mPreferences = preferences;

        URL bakeUrl = buildBaseUrl();
        String jsonString = null;
        try {
            jsonString = getResponseFromHttpUrl(bakeUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPreferences.edit().putString(THE_JSON, jsonString).apply();
        return jsonString;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    /**
     * Check for internet connection
     */
    public static boolean hasInternetConnection(Context context) {
        //check for internet connection
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static SharedPreferences getSharedPreferences() {
        return mPreferences;
    }
}
