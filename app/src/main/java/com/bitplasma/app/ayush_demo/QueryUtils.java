package com.bitplasma.app.ayush_demo;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slash on 24-03-2018.
 */

public class QueryUtils {
    public static final String LOG_TAG = BarcodeScan.class.getName();

    /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }
    /**
     * Query the USGS dataset and return a list of {@link Ayurveda} objects.
     *
     */
    public static Ayurveda fetchAyurvedaData(String requestUrl) {
        Log.i(LOG_TAG,"TEST: fetchAyurvedaData called");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
           // Log.e(LOG_TAG, requestUrl);
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        Ayurveda ayurvedas = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return ayurvedas;
    }
    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    /**
     * Return a list of {@link Ayurveda} objects that has been built up from
     * parsing the given JSON response.
     */
    private static Ayurveda extractFeatureFromJson(String AyurvedaJSON) {
        Log.i(LOG_TAG,"TEST: extractFeatuere called");
        Ayurveda resAyur=new Ayurveda("","","");
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(AyurvedaJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            Log.i(LOG_TAG,"I m here");
            JSONObject baseJsonResponse = new JSONObject(AyurvedaJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
           // JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object

                // Extract the value for the key called "mag"


                // Extract the value for the key called "place"
                String name = baseJsonResponse.getString("name");

                // Extract the value for the key called "time"
                String comapany = baseJsonResponse.getString("company");

                // Extract the value for the key called "url"
                String ingredients = baseJsonResponse.getString("ingredients");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
               Ayurveda ayurvedas = new Ayurveda(name,comapany,ingredients);
                resAyur=ayurvedas;
            //Log.i(LOG_TAG,resAyur.getmTitle());
            } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return resAyur;
    }
    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
